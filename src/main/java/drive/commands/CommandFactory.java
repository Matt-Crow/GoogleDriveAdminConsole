package drive.commands;

import com.google.api.services.drive.Drive;

/**
 *
 * @author Matt
 */
public class CommandFactory {
    private final Drive drive;
    
    public CommandFactory(Drive forDrive){
        drive = forDrive;
    }
    
    public final CreateAccessList createAccessListCmd(String folderId){
        return new CreateAccessList(drive, folderId);
    }
    
    public final GetAccessList getAccessListCmd(String accessListId){
        return new GetAccessList(drive, accessListId);
    }
    
    public final SetAccessListContent setAccessListCmd(String accessListId, String[] userNames){
        return new SetAccessListContent(drive, accessListId, userNames);
    }
    
    public final void addToAccessListCmd(String[] newUsers){
        
    }
}
