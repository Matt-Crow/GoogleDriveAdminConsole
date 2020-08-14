package plugins.implementations.downloadPermission;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Permissions;
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
import java.util.stream.Collectors;
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
    @Override
    public String[] execute() throws IOException {
        FileList allFiles = new ReadFileList(fileList).execute();
        
        FileList allLeafNodes = new FileList();
        allFiles.forEach((file)->{
            allLeafNodes.addAll(getLeaves((FileInfo)file));
        });
        
        Logger.log("Will attempt updating download access for the following files:");
        Logger.log(allLeafNodes.toString());
        
        Drive.Files files = getServiceAccess().getDrive().files();
        List<Drive.Files.Update> updates = allLeafNodes
            .stream()
            .map((FileInfo info)->{
    try {
        System.out.println("is shared? " + files.get(info.getFileId().toString()).execute().getShared());
    } catch (IOException ex) {
        ex.printStackTrace();
    }
                
                Drive.Files.Update update = null;
                File newChanges = new File();
                try {
                    newChanges.setViewersCanCopyContent((info.shouldCopyBeEnabled()) ? Boolean.TRUE: Boolean.FALSE);
                    newChanges.setShared(false);
                    update = files.update(info.getFileId().toString(), newChanges);
                } catch (IOException ex) {
                    Logger.logError(ex);
                }
                return update;
            }).filter((req)->req != null).collect(Collectors.toList());
        /*
        Drive.Permissions perms = getServiceAccess().getDrive().permissions();
        List<Drive.Permissions.Create> permissions = allLeafNodes
            .stream()
            .map((FileInfo info)->{
                Drive.Permissions.Create perm = null;
                try {
                    Permission p = new Permission();
                    p.setRole(null);
                    p.setType("anyone");
                    perm = perms.create(info.getFileId().toString(), p);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return perm;
            }).filter((req)->req != null).collect(Collectors.toList());
        */

        // batch requests to add or remove download access for viewers
        CommandBatch<File> batch = new CommandBatch<>(updates);
        List<File> updated = batch.execute();
        
        //CommandBatch<Permission> batch2 = new CommandBatch<>(permissions);
        //batch2.execute();
        
        Logger.log("Successfully updated download access for the following files:");
        updated.stream().forEach((f)->Logger.log(String.format("- %s", f.getId())));
        String[] updatedIds = updated.stream().map((f)->f.getId()).toArray((size)->new String[size]);
        
        return updatedIds;
    }

}
