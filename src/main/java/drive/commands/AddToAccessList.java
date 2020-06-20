package drive.commands;

import drive.commands.AbstractDriveCommand;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import start.ServiceAccess;

/**
 *
 * @author Matt
 */
public class AddToAccessList extends AbstractDriveCommand<String[]>{
    private final String accessListId;
    private final String[] addedUsers;
    
    public AddToAccessList(ServiceAccess service, String fileId, String[] newUsers) {
        super(service);
        accessListId = fileId;
        addedUsers = Arrays.stream(newUsers).map((name)->name.trim()).toArray((count)->new String[count]);
    }
    
    public AddToAccessList(ServiceAccess service, String fileId, String newUser){
        this(service, fileId, new String[]{newUser});
    }

    @Override
    public String[] execute() throws IOException {
        ArrayList<String> totalUsers = new ArrayList<>();
        String[] previousUsers = new GetAccessList(getServiceAccess(), accessListId).execute();
        Arrays.stream(previousUsers).forEach(totalUsers::add);
        Arrays.stream(addedUsers).forEach(totalUsers::add);
        
        String[] updatedUserList = totalUsers.toArray(new String[]{});
        SetAccessListContent cmd = new SetAccessListContent(getServiceAccess(), accessListId, updatedUserList);
        cmd.execute();
        return cmd.getNewContent();
    }
}
