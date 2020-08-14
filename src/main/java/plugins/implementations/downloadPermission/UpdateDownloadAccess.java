package plugins.implementations.downloadPermission;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Permissions;
import com.google.api.services.drive.DriveRequest;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.drive.model.PermissionList;
import plugins.implementations.fileListReader.ReadFileList;
import drive.AbstractDriveCommand;
import drive.CommandBatch;
import drive.GoogleDriveFileId;
import drive.GoogleDriveService;
import fileUtils.FileList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import structs.FileInfo;
import structs.GoogleSheetProperties;
import sysUtils.Logger;

/**
 *
 * @author Matt
 */
public class UpdateDownloadAccess extends AbstractDriveCommand<String[]>{
    private final GoogleSheetProperties fileList;
    public UpdateDownloadAccess(GoogleSheetProperties driveFileList) {
        super();
        fileList = driveFileList;
    }

    /**
     * 
     * @param root the information on a Google Drive file or folder to get leaf nodes for
     * @return all files underneath root.
     */
    private List<FileInfo> getLeaves(FileInfo root){
        List<FileInfo> childFiles = new ArrayList<>();
        try {
            File rootFile = getServiceAccess().getDrive().files().get(root.getFileId().toString()).execute();
            if("application/vnd.google-apps.folder".equals(rootFile.getMimeType())){
                // if it is a folder, add all of its non-folder descendants to the list
                List<File> childDirsAndFiles = getServiceAccess().getDrive().files().list()
                    .setQ(String.format("'%s' in parents and trashed = false", root.getFileId())).execute().getFiles();
                childDirsAndFiles.stream().map((File newRootFile)->{
                    return new FileInfo(
                        new GoogleDriveFileId(newRootFile.getId()),
                        newRootFile.getName(),
                        root.getGroups().copy(), // inherit group from parent
                        root.shouldCopyBeEnabled() // inherit downloadability from parent
                    );
                }).forEach((FileInfo newRootInfo)->{
                    childFiles.addAll(getLeaves(newRootInfo));
                });
            } else {
                // if root is a file, just return it
                childFiles.add(root);
            }
        } catch (IOException ex) {
            Logger.logError(ex);
        }
        
        
        return childFiles;
    }
    
    /**
     * 
     * @param fileInfo
     * @return the requests to remove all "anyone with the link can view" permissions from the given file
     */
    private List<Drive.Permissions.Delete> createDelReqs(FileInfo fileInfo){
        List<Permissions.Delete> reqs = new ArrayList<>();
        Drive.Permissions perms = getServiceAccess().getDrive().permissions();
        try {
            // get all the permissions for the file
            PermissionList allPermissions = getServiceAccess().getDrive().permissions().list(fileInfo.getFileId().toString()).execute();
            allPermissions.getPermissions().stream().filter((Permission p)->{
                return p.getId().equals("anyoneWithLink"); // there may be a better way of detecting if this permission is the "anyone with the link can do X"
            }).map((Permission withLinkPerm)->{
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
        } catch (IOException ex) {
            Logger.logError(ex);
        }
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
        allFiles.forEach((fileInfo)->allReqs.addAll(createDelReqs(fileInfo)));
        
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
