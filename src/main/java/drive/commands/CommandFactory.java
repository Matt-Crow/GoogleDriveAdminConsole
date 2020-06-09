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
    
    
    
    public final GiveAccess giveViewAccess(String fileId, String email){
        return new GiveAccess(drive, fileId, email, AccessType.VIEW);
    }
    public final Copy makeCopyFor(String fileId, String toDir, String email, String name){
        return new Copy(drive, fileId, toDir, name, email);
    }
    
    public final ReadCertificationForm readCertForm(String fileId){
        return new ReadCertificationForm(drive, fileId);
    }
}
