package plugins.implementations.downloadPermission;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
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
        StringBuilder msg = new StringBuilder();
        msg.append("All files:");
        allFiles.forEach((file)->msg.append("\n").append(file.toString()));
        
        List<FileInfo> allLeafNodes = new ArrayList<>();
        allFiles.forEach((file)->{
            if(file instanceof FileInfo){
                allLeafNodes.addAll(getLeaves((FileInfo)file));
            } else {
                Logger.logError("Not a detailed file info in UpdateDownloadAccess: " + file.toString());
            }
        });
        
        msg.append("\nAll children:");
        allLeafNodes.forEach((leaf)->msg.append("\n").append(leaf.toString()));
        Logger.log(msg.toString());
        
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
        String[] updatedIds = updated.stream().map((f)->f.getId()).toArray((size)->new String[size]);
        return updatedIds;
    }

}
