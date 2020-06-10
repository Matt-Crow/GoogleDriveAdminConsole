package drive.commands;

import drive.commands.basic.GiveAccess;
import drive.commands.basic.Copy;
import structs.AccessType;
import structs.CamperFile;
import com.google.api.services.drive.Drive;
import java.io.IOException;
import java.util.ArrayList;
import structs.UserData;

/**
 * Use this only to add a single user to the drive
 * @author Matt
 */
public class GrantAccess extends AbstractDriveCommand<Boolean>{
    private final UserData camper;
    private final String folderId;
    private final String fileListId;
    
    public GrantAccess(Drive d, UserData user, String campFolderId, String fileSpreadsheetId) {
        super(d);
        camper = user;
        folderId = campFolderId;
        fileListId = fileSpreadsheetId;
    }

    @Override
    public Boolean execute() throws IOException {
        ArrayList<CamperFile> files = new ReadFileList(getDrive(), fileListId, "files campers can view", "campers get a copy of these").execute();
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
