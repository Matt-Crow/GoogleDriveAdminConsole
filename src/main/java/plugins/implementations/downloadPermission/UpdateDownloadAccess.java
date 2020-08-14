package plugins.implementations.downloadPermission;

import com.google.api.services.drive.Drive;
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
    
    private Stream<DriveRequest<?>> getChangesToMake(FileInfo info){
        List<DriveRequest<?>> changesToMake = new ArrayList<>();
        Drive drive = getServiceAccess().getDrive();
        Drive.Files files = drive.files();
        Drive.Permissions perms = drive.permissions();
        
        File changes = new File();
        changes.setViewersCanCopyContent((info.shouldCopyBeEnabled()) ? Boolean.TRUE: Boolean.FALSE);
        
        // first, try to create the request to change copy enabled
        try {
            Drive.Files.Update updateCopyPerms = files.update(info.getFileId().toString(), changes);
            changesToMake.add(updateCopyPerms);
        } catch (IOException ex) {
            Logger.logError(ex);
        }
        
        // next, try to create the request to remove "anyone with the link can view"
        try {
            PermissionList permList = perms.list(info.getFileId().toString()).execute();
            List<Permission> anyoneWithLink = permList.getPermissions().stream().filter((perm)->perm.getId().equals("anyoneWithLink")).collect(Collectors.toList());
            List<Permissions.Delete> deleteThose = anyoneWithLink.stream().map((perm)->{
                Permissions.Delete del = null;
                try {
                    del = perms.delete(info.getFileId().toString(), perm.getId());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return del;
            }).collect(Collectors.toList());
            changesToMake.addAll(deleteThose);
        } catch (IOException ex) {
            Logger.logError(ex);
        }
        
        return changesToMake.stream().filter((req)->req != null);
    }
    
    @Override
    public String[] execute() throws IOException {
        FileList allFiles = new ReadFileList(fileList).execute();
        
        FileList allLeafNodes = new FileList();
        allFiles.forEach((file)->{
            allLeafNodes.addAll(getLeaves((FileInfo)file));
        });
        
        Logger.log("Will attempt updating download access for the following files:");
        Logger.log(allLeafNodes.toString());
        
        List<DriveRequest> allReqs = allLeafNodes.stream().flatMap(this::getChangesToMake).filter((req)->req != null).collect(Collectors.toList());
        allReqs.forEach(System.out::println);
        /*
        Drive.Files files = getServiceAccess().getDrive().files();
        
        List<Drive.Files.Update> updates = allLeafNodes
            .stream()
            .map((FileInfo info)->{
                Drive.Files.Update update = null;
                File newChanges = new File();
                try {
                    newChanges.setViewersCanCopyContent((info.shouldCopyBeEnabled()) ? Boolean.TRUE: Boolean.FALSE);
                    update = files.update(info.getFileId().toString(), newChanges);
                } catch (IOException ex) {
                    Logger.logError(ex);
                }
                return update;
            }).filter((req)->req != null).collect(Collectors.toList());
        
        // batch requests to add or remove download access for viewers
        CommandBatch<File> batch = new CommandBatch<>(updates);
        List<File> updated = batch.execute();
        */
        CommandBatch batch = new CommandBatch(allReqs); // needs diamond operator, but I can't figure out the capture types now
        List updated = batch.execute();
        /*
        // remove "anyone with the link can view" options
        Drive.Permissions permissions = getServiceAccess().getDrive().permissions();
        
        List<Permission> anyoneWithLinkCanViewPerms = allLeafNodes
            .stream()
            .map((FileInfo info)->{
                PermissionList permList = null;
                try {
                    permList = permissions.list(info.getFileId().toString()).execute();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return permList;
            }).filter((permList)->permList != null).flatMap((PermissionList permList)->{
                return permList.getPermissions().stream();
            }).filter((perm)->{
                return perm.getId().equals("anyoneWithLink");
            }).collect(Collectors.toList());
        
        List<Permissions.Delete> removePerms = anyoneWithLinkCanViewPerms.stream().map((perm)->{
            try {
                // how do I get the file ID if it isn't stored in permissions?
                return permissions.delete("file id", perm.getId());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        */
        Logger.log("Successfully updated download access for the following files:");
        updated.stream().forEach((f)->Logger.log(String.format("- %s", f)));
        String[] updatedIds = new String[0];//updated.stream().map((f)->f.getId()).toArray((size)->new String[size]);
        
        return updatedIds;
    }

}
