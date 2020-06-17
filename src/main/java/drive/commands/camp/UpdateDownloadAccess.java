package drive.commands.camp;

import com.google.api.services.drive.model.File;
import drive.commands.AbstractDriveCommand;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import services.ServiceAccess;
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

    private List<DetailedFileInfo> getFiles(DetailedFileInfo root){
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
                    childFiles.addAll(getFiles(newRootInfo));
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
        
        List<DetailedFileInfo> allChildNodes = new ArrayList<>();
        allCampFiles.forEach((file)->{
            allChildNodes.addAll(getFiles(file));
        });
        
        System.out.println("All children:");
        allChildNodes.forEach(System.out::println);
        
        // batch requests to add or remove download access for viewers
        
        //String[] updated = new DisableDownloads(getServiceAccess(), allCampFiles).execute();
        return null;
    }

}
