package drive.commands;

import drive.commands.ReadFileList;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import drive.commands.AbstractDriveCommand;
import drive.commands.CommandBatch;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import start.ServiceAccess;
import structs.DetailedFileInfo;
import structs.FileListInfo;

/**
 *
 * @author Matt
 */
public class UpdateDownloadAccess extends AbstractDriveCommand<String[]>{
    private final FileListInfo fileList;
    public UpdateDownloadAccess(ServiceAccess serv, FileListInfo driveFileList) {
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
            ex.printStackTrace();
        }
        
        
        return childFiles;
    }
    @Override
    public String[] execute() throws IOException {
        List<DetailedFileInfo> allCampFiles = new ReadFileList(getServiceAccess(), fileList).execute();
        System.out.println("All files:");
        allCampFiles.forEach(System.out::println);
        
        List<DetailedFileInfo> allLeafNodes = new ArrayList<>();
        allCampFiles.forEach((file)->{
            allLeafNodes.addAll(getLeaves(file));
        });
        
        System.out.println("All children:");
        allLeafNodes.forEach(System.out::println);
        
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
                    ex.printStackTrace();
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
