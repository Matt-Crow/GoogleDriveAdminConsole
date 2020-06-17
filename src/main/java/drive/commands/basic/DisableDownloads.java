package drive.commands.basic;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import drive.commands.AbstractDriveCommand;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import services.ServiceAccess;
import structs.SimpleFileInfo;

/**
 * should also re-enable downloads for some
 * @author Matt
 */
public class DisableDownloads extends AbstractDriveCommand<String[]>{
    private final List<? extends SimpleFileInfo> files;
    public DisableDownloads(ServiceAccess serv, List<? extends SimpleFileInfo> dontLetThemDownloadUs) {
        super(serv);
        files = dontLetThemDownloadUs;
    }
    
    private List<String> gatherSubFiles(String rootId){
        ArrayList<String> ids = new ArrayList<>();
        Drive drive = getDrive();
        try{
            List<File> fileList = (drive.files().list().setQ(String.format("'%s' in parents and trashed = false", rootId)).execute().getFiles());
            for(File fileOrDir : fileList){
                if("application/vnd.google-apps.folder".equals(fileOrDir.getMimeType())){
                    // can't prevent download of folders, so get all files under it
                    ids.addAll(gatherSubFiles(fileOrDir.getId()));
                } else {
                    // if it isn't a folder, we can just add its ID
                    ids.add(fileOrDir.getId());
                }
            }
        } catch (IOException ex) { 
            ex.printStackTrace();
        }
        
        return ids;
    }
    
    @Override // still need to batch
    public String[] execute() throws IOException {
        List<String> preventedDownloadsFor = new ArrayList<>();
        files.forEach((root) -> {
            preventedDownloadsFor.addAll(gatherSubFiles(root.getFileId()));
        });
        preventedDownloadsFor.forEach((id) -> {
            System.out.println(id); // still need to prevent downloads
        });
        return null;
    }

}
