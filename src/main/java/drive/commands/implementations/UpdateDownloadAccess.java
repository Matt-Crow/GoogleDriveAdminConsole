package drive.commands.implementations;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import drive.commands.utils.AbstractDriveCommand;
import drive.commands.utils.CommandBatch;
import fileUtils.FileList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import start.GoogleDriveService;
import structs.DetailedFileInfo;
import structs.FileListProperties;
import sysUtils.Logger;

/**
 *
 * @author Matt
 */
public class UpdateDownloadAccess extends AbstractDriveCommand<String[]>{
    private final FileListProperties fileList;
    public UpdateDownloadAccess(GoogleDriveService serv, FileListProperties driveFileList) {
        super(serv);
        fileList = driveFileList;
    }

    private List<DetailedFileInfo> getLeaves(DetailedFileInfo root){
        List<DetailedFileInfo> childFiles = new ArrayList<>();
        try {
            File rootFile = getDrive().files().get(root.getFileId()).execute();
            if("application/vnd.google-apps.folder".equals(rootFile.getMimeType())){
                // if it is a folder, add all of its non-folder descendants to the list
                List<File> childDirsAndFiles = getDrive().files().list()
                    .setQ(String.format("'%s' in parents and trashed = false", root.getFileId())).execute().getFiles();
                childDirsAndFiles.stream().map((File newRootFile)->{
                    return new DetailedFileInfo(
                        newRootFile.getId(),
                        newRootFile.getName(),
                        newRootFile.getThumbnailLink(),
                        root.shouldCopyBeEnabled() // inherit downloadability from parent
                    );
                }).forEach((DetailedFileInfo newRootInfo)->{
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
        FileList allCampFiles = new ReadFileList(getServiceAccess(), fileList).execute();
        StringBuilder msg = new StringBuilder();
        msg.append("All files:");
        allCampFiles.forEach((file)->msg.append("\n").append(file.toString()));
        
        List<DetailedFileInfo> allLeafNodes = new ArrayList<>();
        allCampFiles.forEach((file)->{
            if(file instanceof DetailedFileInfo){
                allLeafNodes.addAll(getLeaves((DetailedFileInfo)file));
            } else {
                Logger.logError("Not a detailed file info in UpdateDownloadAccess: " + file.toString());
            }
        });
        
        msg.append("\nAll children:");
        allLeafNodes.forEach((leaf)->msg.append("\n").append(leaf.toString()));
        Logger.log(msg.toString());
        
        Drive.Files files = getDrive().files();
        List<Drive.Files.Update> updates = allLeafNodes
            .stream()
            .map((DetailedFileInfo info)->{
                Drive.Files.Update update = null;
                File newChanges = new File();
                try {
                    newChanges.setViewersCanCopyContent((info.shouldCopyBeEnabled()) ? Boolean.TRUE: Boolean.FALSE);
                    update = files.update(info.getFileId(), newChanges);
                } catch (IOException ex) {
                    Logger.logError(ex);
                }
                return update;
            }).filter((req)->req != null).collect(Collectors.toList());
        
        // batch requests to add or remove download access for viewers
        
        CommandBatch<File> batch = new CommandBatch<>(getServiceAccess(), updates);
        List<File> updated = batch.execute();
        String[] updatedIds = updated.stream().map((f)->f.getId()).toArray((size)->new String[size]);
        return updatedIds;
    }

}
