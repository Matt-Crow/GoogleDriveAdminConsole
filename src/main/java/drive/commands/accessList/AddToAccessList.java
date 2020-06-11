package drive.commands.accessList;

import drive.commands.AbstractDriveCommand;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import services.ServiceAccess;

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
        addedUsers = newUsers;
    }

    @Override
    public String[] execute() throws IOException {
        ArrayList<String> totalUsers = new ArrayList<>();
        String[] previousUsers = new GetAccessList(getServiceAccess(), accessListId).execute();
        Arrays.stream(previousUsers).forEach(totalUsers::add);
        Arrays.stream(addedUsers).forEach(totalUsers::add);
        
        String[] updatedUserList = totalUsers.toArray(new String[]{});
        
        new SetAccessListContent(getServiceAccess(), accessListId, updatedUserList).execute();
        return updatedUserList;
    }
}
