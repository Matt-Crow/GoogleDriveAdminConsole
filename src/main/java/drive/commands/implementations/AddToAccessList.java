package drive.commands.implementations;

import drive.commands.utils.AbstractDriveCommand;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import start.GoogleDriveService;

/**
 *
 * @author Matt
 */
public class AddToAccessList extends AbstractDriveCommand<String[]>{
    private final String accessListId;
    private final String[] addedUsers;
    
    public AddToAccessList(GoogleDriveService service, String fileId, String[] newUsers) {
        super(service);
        accessListId = fileId;
        addedUsers = Arrays.stream(newUsers).map((name)->name.trim()).toArray((count)->new String[count]);
    }
    
    public AddToAccessList(GoogleDriveService service, String fileId, String newUser){
        this(service, fileId, new String[]{newUser});
    }

    @Override
    public String[] doExecute() throws IOException {
        ArrayList<String> totalUsers = new ArrayList<>();
        String[] previousUsers = new GetAccessList(getServiceAccess(), accessListId).doExecute();
        Arrays.stream(previousUsers).forEach(totalUsers::add);
        Arrays.stream(addedUsers).forEach(totalUsers::add);
        
        String[] updatedUserList = totalUsers.toArray(new String[]{});
        SetAccessListContent cmd = new SetAccessListContent(getServiceAccess(), accessListId, updatedUserList);
        cmd.doExecute();
        return cmd.getNewContent();
    }
}
