package drive.commands.camp;

import drive.commands.AbstractDriveCommand;
import drive.commands.basic.DisableDownloads;
import java.io.IOException;
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

    @Override
    public String[] execute() throws IOException {
        List<DetailedFileInfo> allCampFiles = new ReadFileList(getServiceAccess(), fileList).execute();
        String[] updated = new DisableDownloads(getServiceAccess(), allCampFiles).execute();
        return updated;
    }

}
