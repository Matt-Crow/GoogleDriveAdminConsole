package drive.commands.implementations;

import drive.commands.utils.AbstractDriveCommand;
import fileUtils.FileList;
import fileUtils.UserList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import start.GoogleDriveService;
import structs.GoogleSheetProperties;
import structs.UserToFileMapping;
import sysUtils.Logger;

/**
 * 
 * @author Matt
 */
public class ParseCertificationForm extends AbstractDriveCommand<List<UserToFileMapping>>{
    private final GoogleSheetProperties certFormInfo;
    private final GoogleSheetProperties fileListInfo;
    private final boolean isTest;
    
    public ParseCertificationForm(GoogleDriveService service, GoogleSheetProperties source, GoogleSheetProperties fileList, boolean thisIsATest) {
        super(service);
        certFormInfo = source;
        fileListInfo = fileList;
        isTest = thisIsATest;
    }

    @Override
    public List<UserToFileMapping> doExecute() throws IOException {
        StringBuilder msg = new StringBuilder();
        
        // first, extract the campers from the form responses
        UserList newCampers = new ReadUserList(getServiceAccess(), certFormInfo).doExecute();
        
        msg.append("Contents of certification form:");
        newCampers.forEach((camper)->msg.append("\n").append(camper.toString()));
        
        
        // next, get the list of files campers will get access to
        FileList files = new ReadFileList(getServiceAccess(), fileListInfo).doExecute();
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
                cmd.doExecute();
            } catch (IOException ex) {
                Logger.logError(ex);
            }
        }
        return whoGetsWhat;
    }
}
