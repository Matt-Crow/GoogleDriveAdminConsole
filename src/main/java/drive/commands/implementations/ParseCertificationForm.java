package drive.commands.implementations;

import drive.commands.utils.AbstractDriveCommand;
import fileUtils.FileList;
import fileUtils.UserList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import start.GoogleDriveService;
import structs.UserListProperties;
import structs.FileListProperties;
import structs.DetailedUserInfo;
import structs.UserToFileMapping;
import sysUtils.Logger;

/**
 * 
 * @author Matt
 */
public class ParseCertificationForm extends AbstractDriveCommand<List<UserToFileMapping>>{
    private final UserListProperties certFormInfo;
    private final FileListProperties fileListInfo;
    private final String accessListId;
    private final boolean isTest;
    
    public ParseCertificationForm(GoogleDriveService service, UserListProperties source, FileListProperties fileList, String accessListFileId, boolean thisIsATest) {
        super(service);
        certFormInfo = source;
        fileListInfo = fileList;
        accessListId = accessListFileId;
        isTest = thisIsATest;
    }

    @Override
    public List<UserToFileMapping> execute() throws IOException {
        StringBuilder msg = new StringBuilder();
        
        // first, extract the campers from the form responses
        UserList newCampers = new ReadUserList(getServiceAccess(), certFormInfo).execute();
        
        msg.append("Contents of certification form:");
        newCampers.forEach((camper)->msg.append("\n").append(camper.toString()));
        
        
        // next, get the list of files campers will get access to
        FileList files = new ReadFileList(getServiceAccess(), fileListInfo).execute();
        msg.append("\nFiles they will get:");
        files.forEach((file)->msg.append("\n").append(file.toString()));
        
        // construct the list of requests to make
        ArrayList<UserToFileMapping> whoGetsWhat = UserToFileMapping.constructUserFileList(newCampers, files);
        
        msg.append("\nCreating the following mappings:");
        whoGetsWhat.forEach((mapping)->msg.append("\n").append(mapping.toString()));
        Logger.log(msg.toString());
        
        // construct the command
        AbstractDriveCommand cmd = new GiveViewAccess(getServiceAccess(), whoGetsWhat);
        
        Logger.log(cmd.toString());
        
        if(!isTest){
            try{
                cmd.execute();
            } catch (IOException ex) {
                Logger.logError(ex);
            }

            // add people to the Minecraft user list
            String[] newMcUsers = newCampers
                .stream()
                .filter((userInfo)->{
                    return userInfo instanceof DetailedUserInfo;
                }).map((simpleUserInfo)->{
                    return (DetailedUserInfo)simpleUserInfo;
                }).map((detailedUserInfo)->{
                    return detailedUserInfo.getMinecraftUsername();
                })
                .toArray((size)->new String[size]);

            new AddToAccessList(getServiceAccess(), accessListId, newMcUsers).execute();
        }
        return whoGetsWhat;
    }
}
