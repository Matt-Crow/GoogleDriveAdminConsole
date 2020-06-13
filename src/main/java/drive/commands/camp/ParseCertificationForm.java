package drive.commands.camp;

import drive.commands.AbstractDriveCommand;
import drive.commands.accessList.AddToAccessList;
import drive.commands.basic.GiveViewAccess;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import services.ServiceAccess;
import structs.CamperFile;
import structs.CertificationFormInfo;
import structs.FileListInfo;
import structs.UserData;
import structs.UserToFileMapping;

/**
 * make this add multiple people to multiple files using threads
 * @author Matt
 */
public class ParseCertificationForm extends AbstractDriveCommand<List<UserToFileMapping>>{
    private final CertificationFormInfo certFormInfo;
    private final FileListInfo fileListInfo;
    private final String accessListId;
    
    public ParseCertificationForm(ServiceAccess service, CertificationFormInfo source, FileListInfo fileList, String accessListFileId) {
        super(service);
        certFormInfo = source;
        fileListInfo = fileList;
        accessListId = accessListFileId;
    }

    @Override
    public List<UserToFileMapping> execute() throws IOException {
        // first, extract the campers from the form responses
        ArrayList<UserData> newCampers = new ReadCertificationForm(getServiceAccess(), certFormInfo).execute();
        System.out.println("Contents of certification form:");
        newCampers.forEach(System.out::println);
        
        
        // next, get the list of files campers will get access to
        ArrayList<CamperFile> files = new ReadFileList(getServiceAccess(), fileListInfo).execute();
        System.out.println("Files they will get:");
        files.forEach(System.out::println);
        
        // construct the list of requests to make
        ArrayList<UserToFileMapping> whoGetsWhat = UserToFileMapping.constructUserFileList(newCampers, files);
        
        // construct the command
        AbstractDriveCommand cmd = new GiveViewAccess(getServiceAccess(), whoGetsWhat);
        System.out.println(cmd);
        try{
            cmd.execute();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
        // add people to the Minecraft user list
        String[] newMcUsers = newCampers.stream().map((userData)->{
            return userData.getMinecraftUsername();
        }).toArray((size)->new String[size]);
        
        new AddToAccessList(getServiceAccess(), accessListId, newMcUsers).execute();
        
        return whoGetsWhat;
    }
}
