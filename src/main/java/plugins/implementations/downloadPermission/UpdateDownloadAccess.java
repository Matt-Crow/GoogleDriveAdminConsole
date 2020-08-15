package plugins.implementations.downloadPermission;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Permissions;
import com.google.api.services.drive.DriveRequest;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import plugins.implementations.fileListReader.ReadFileList;
import drive.AbstractDriveCommand;
import drive.CommandBatch;
import drive.GoogleDriveFileId;
import fileUtils.FileList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import structs.FileInfo;
import structs.GoogleSheetProperties;
import sysUtils.Logger;

/**
 * This command is used to set sharing permissions on files.
 * It sets whether or not viewers can download or copy files
 * based on the FileInfo it used to find that file. It also removes all
 * global permissions from the files it updates.
 * Note that the changes this makes cascade: given a folder in FileInfo,
 * it will apply changes to both that folder and all of its children.
 * 
 * @author Matt Crow
 */
public class UpdateDownloadAccess extends AbstractDriveCommand<String[]>{
    private final GoogleSheetProperties fileList;
    private static final Predicate<File> IS_FOLDER = (File f)->"application/vnd.google-apps.folder".equals(f.getMimeType());
    private static final Predicate<Permission> IS_GLOBAL_PERMISSION = (Permission p)->p.getId().equals("anyoneWithLink"); // there may be a better way of detecting if this permission is the "anyone with the link can do X"
    
    public UpdateDownloadAccess(GoogleSheetProperties driveFileList) {
        super();
        fileList = driveFileList;
    }

    /**
     * Returns all Google Drive files for whom
     * the given parameter is a parent.
     * 
     * @param parent the info of the file to get children for.
     * @return a list of child files for the given
     * parent. This list may be empty if the parent
     * is a leaf node.
     */
    private List<FileInfo> getChildren(FileInfo parent){
        List<FileInfo> children = new ArrayList<>();
        try {
            children = getServiceAccess()
                .getDrive()
                .files()
                .list()
                .setQ(String.format("'%s' in parents and trashed = false", parent.getFileId().toString()))
                .execute()
                .getFiles()
                .stream()
                .map(
                    (File file)->{
                        return new FileInfo(
                            new GoogleDriveFileId(file.getId()),
                            file.getName(),
                            parent.getGroups().copy(), // inherit group from parent
                            parent.shouldCopyBeEnabled() // inherit downloadability from parent
                        );
                })
                .collect(Collectors.toList());
        } catch (IOException ex) {
            Logger.logError(ex);
        }
        return children;
    }
    
    /**
     * 
     * @param root the information on a Google Drive file or folder to get leaf nodes for
     * @return all non-folder files underneath root.
     */
    private List<FileInfo> getLeaves(FileInfo root){
        List<FileInfo> leaves = new ArrayList<>();
        try {
            File rootFile = getServiceAccess().getDrive().files().get(root.getFileId().toString()).execute();
            if(IS_FOLDER.test(rootFile)){
                // if it is a folder, add all of its non-folder descendants to the list
                getChildren(root).stream().forEach((FileInfo newRootInfo)->{
                    leaves.addAll(getLeaves(newRootInfo));
                });
            } else {
                // if root is a file, just return it
                leaves.add(root);
            }
        } catch (IOException ex) {
            Logger.logError(ex);
        }
        
        return leaves;
    }
    
    /**
     * 
     * @param fileInfo
     * @return all global permissions associated with the Google Drive
     * file referenced by fileInfo.
     */
    private List<Permission> getGlobalPermissions(FileInfo fileInfo){
        List<Permission> permissions = new ArrayList<>();
        try {
            permissions = getServiceAccess()
                .getDrive()
                .permissions()
                .list(fileInfo.getFileId().toString())
                .execute()
                .getPermissions()
                .stream()
                .filter(IS_GLOBAL_PERMISSION)
                .collect(Collectors.toList());
        } catch (IOException ex) {
            Logger.logError(ex);
        }
        return permissions;
    }
    /**
     * 
     * @param fileInfo
     * @return the requests to remove all "anyone with the link can view" permissions from the given file and its subfiles
     */
    private List<Drive.Permissions.Delete> createDelPermReqs(FileInfo fileInfo){
        List<Permissions.Delete> reqs = new ArrayList<>();
        Drive.Permissions perms = getServiceAccess().getDrive().permissions();
        
        // get all the global permissions for the file
        getGlobalPermissions(fileInfo).stream().map((Permission withLinkPerm)->{
            // try to construct a request to delete the permission
            Permissions.Delete del = null;
            try {
                del = perms.delete(fileInfo.getFileId().toString(), withLinkPerm.getId());
            } catch (IOException ex) {
                Logger.logError(ex);
            }
            return del;
        }).filter((req)->req != null).forEach((Permissions.Delete del)->{
            reqs.add(del);
        });
        
        // gather requests for all child files and folders
        getChildren(fileInfo).forEach((FileInfo child)->{
            reqs.addAll(createDelPermReqs(child));
        });
        
        return reqs;
    }
    
    /**
     * 
     * @param root
     * @return the file update commands to make on the given file
     */
    private List<Files.Update> createPermsForSubFiles(FileInfo root){
        List<Files.Update> reqs = new ArrayList<>();
        Drive.Files files = getServiceAccess().getDrive().files();
        
        getLeaves(root).stream().map((FileInfo info)->{
            Files.Update req = null;
            File changes = new File();
            changes.setViewersCanCopyContent((info.shouldCopyBeEnabled()) ? Boolean.TRUE: Boolean.FALSE);

            try {
                req = files.update(info.getFileId().toString(), changes);
            } catch (IOException ex) {
                Logger.logError(ex);
            }
            
            return req;
        }).filter((req)->req != null).forEach((req)->{
            reqs.add(req);
        });
        
        return reqs;
    }
    
    /**
     * 
     * @param allFiles
     * @return the requests this command should batch and make.
     */
    private List<DriveRequest<?>> createRequests(FileList allFiles){
        List<DriveRequest<?>> allReqs = new ArrayList<>();
        
        // first, create the requests to remove all "anyone with the link can view" permissions.
        allFiles.forEach((fileInfo)->allReqs.addAll(createDelPermReqs(fileInfo)));
        
        // next, create all the requests to set download / copy abilities
        allFiles.forEach((fileInfo)->allReqs.addAll(createPermsForSubFiles(fileInfo)));
        return allReqs;
    }
    
    @Override
    public String[] execute() throws IOException {
        FileList allFiles = new ReadFileList(fileList).execute();
        
        Logger.log("Will attempt updating download access for the following files:");
        Logger.log(allFiles.toString());
        
        List<DriveRequest<?>> allReqs = createRequests(allFiles);
        allReqs.forEach(System.out::println);
        
        CommandBatch batch = new CommandBatch(allReqs); // needs diamond operator, but I can't figure out the capture types now
        List updated = batch.execute();
        
        Logger.log("Successfully updated download access for the following files:");
        updated.stream().forEach((f)->Logger.log(String.format("- %s", f)));
        String[] updatedIds = new String[0];//updated.stream().map((f)->f.getId()).toArray((size)->new String[size]);
        
        return updatedIds;
    }

}
