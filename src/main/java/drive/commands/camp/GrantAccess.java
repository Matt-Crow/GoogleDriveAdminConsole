package drive.commands.camp;

import drive.commands.basic.GiveAccess;
import drive.commands.basic.Copy;
import structs.AccessType;
import structs.CamperFile;
import com.google.api.services.drive.Drive;
import drive.commands.AbstractDriveCommand;
import java.io.IOException;
import java.util.ArrayList;
import structs.FileListInfo;
import structs.UserData;

/**
 * Use this only to add a single user to the drive
 * @author Matt
 */
public class GrantAccess extends AbstractDriveCommand<Boolean>{
    private final UserData camper;
    private final String folderId;
    private final FileListInfo fileListInfo;
    
    public GrantAccess(Drive d, UserData user, String campFolderId, FileListInfo fileList) {
        super(d);
        camper = user;
        folderId = campFolderId;
        fileListInfo = fileList;
    }

    @Override
    public Boolean execute() throws IOException {
        ArrayList<CamperFile> files = new ReadFileList(getDrive(), fileListInfo).execute();
        ArrayList<AbstractDriveCommand> commands = new ArrayList<>();
        for(CamperFile file : files){
            switch (file.getAccessType()) {
                case EDIT:
                    commands.add(new Copy(getDrive(), file.getFileId(), folderId, camper.getName(), camper.getEmail()));
                    break;
                case VIEW:
                    commands.add(new GiveAccess(getDrive(), file.getFileId(), camper.getEmail(), AccessType.VIEW));
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
        commands.forEach((cmd)->{
            try {
                cmd.execute();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return true;
    }

}
