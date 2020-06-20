package drive.commands;

import drive.commands.AbstractDriveCommand;
import drive.commands.AddToAccessList;
import drive.commands.GiveViewAccess;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import start.ServiceAccess;
import structs.DetailedFileInfo;
import structs.CertificationFormInfo;
import structs.FileListInfo;
import structs.DetailedUserInfo;
import structs.UserToFileMapping;

/**
 * 
 * @author Matt
 */
public class ParseCertificationForm extends AbstractDriveCommand<List<UserToFileMapping>>{
    private final CertificationFormInfo certFormInfo;
    private final FileListInfo fileListInfo;
    private final String accessListId;
    private final boolean isTest;
    
    public ParseCertificationForm(ServiceAccess service, CertificationFormInfo source, FileListInfo fileList, String accessListFileId, boolean thisIsATest) {
        super(service);
        certFormInfo = source;
        fileListInfo = fileList;
        accessListId = accessListFileId;
        isTest = thisIsATest;
    }

    @Override
    public List<UserToFileMapping> execute() throws IOException {
        // first, extract the campers from the form responses
        ArrayList<DetailedUserInfo> newCampers = new ReadCertificationForm(getServiceAccess(), certFormInfo).execute();
        System.out.println("Contents of certification form:");
        newCampers.forEach(System.out::println);
        
        
        // next, get the list of files campers will get access to
        ArrayList<DetailedFileInfo> files = new ReadFileList(getServiceAccess(), fileListInfo).execute();
        System.out.println("Files they will get:");
        files.forEach(System.out::println);
        
        // construct the list of requests to make
        ArrayList<UserToFileMapping> whoGetsWhat = UserToFileMapping.constructUserFileList(newCampers, files);
        
        // construct the command
        AbstractDriveCommand cmd = new GiveViewAccess(getServiceAccess(), whoGetsWhat);
        
        System.out.println(cmd);
        
        if(!isTest){
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
        }
        return whoGetsWhat;
    }
}
