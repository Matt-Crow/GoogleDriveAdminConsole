package drive.commands;

import drive.commands.camp.ReadFileList;
import drive.commands.camp.ReadCertificationForm;
import drive.commands.accessList.SetAccessListContent;
import drive.commands.accessList.GetAccessList;
import drive.commands.accessList.CreateAccessList;
import drive.commands.accessList.AddToAccessList;
import drive.commands.basic.GiveAccess;
import drive.commands.basic.Copy;
import structs.AccessType;
import com.google.api.services.drive.Drive;
import drive.commands.camp.ParseCertificationForm;
import structs.CertificationFormInfo;
import structs.FileListInfo;

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
    
    public final ReadCertificationForm readCertForm(CertificationFormInfo info){
        return new ReadCertificationForm(drive, info);
    }
    public final ReadFileList readFileList(FileListInfo info){
        return new ReadFileList(drive, info);
    }
    
    public final ParseCertificationForm parseCertificationForm(CertificationFormInfo formInfo, FileListInfo fileInfo, String campRootId){
        return new ParseCertificationForm(drive, formInfo, fileInfo, campRootId);
    }
}
