package drive.commands.accessList;

import com.google.api.services.drive.Drive;
import drive.commands.AbstractDriveCommand;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Matt
 */
public class AddToAccessList extends AbstractDriveCommand<String[]>{
    private final String accessListId;
    private final String[] addedUsers;
    
    public AddToAccessList(Drive d, String fileId, String[] newUsers) {
        super(d);
        accessListId = fileId;
        addedUsers = newUsers;
    }

    @Override
    public String[] execute() throws IOException {
        ArrayList<String> totalUsers = new ArrayList<>();
        String[] previousUsers = new GetAccessList(getDrive(), accessListId).execute();
        Arrays.stream(previousUsers).forEach(totalUsers::add);
        Arrays.stream(addedUsers).forEach(totalUsers::add);
        
        String[] updatedUserList = totalUsers.toArray(new String[]{});
        
        new SetAccessListContent(getDrive(), accessListId, updatedUserList).execute();
        return updatedUserList;
    }
}
