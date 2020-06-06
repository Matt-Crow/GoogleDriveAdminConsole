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
    
    public final AddToAccessList addToAccessListCmd(String accessListId, String[] newUsers){
        return new AddToAccessList(drive, accessListId, newUsers);
    }
    
    
    
    public final GiveViewAccess giveViewAccess(String fileId, String[] emails){
        return new GiveViewAccess(drive, fileId, emails);
    }
    public final void makeCopyFor(String fileId, String email, String name){
        
    }
}
