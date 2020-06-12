package drive.commands.camp;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import drive.commands.AbstractDriveCommand;
import drive.commands.accessList.AddToAccessList;
import drive.commands.basic.Copy;
import drive.commands.basic.CreateFolder;
import drive.commands.basic.GiveAccess;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import services.ServiceAccess;
import structs.AccessType;
import structs.CamperFile;
import structs.CertificationFormInfo;
import structs.FileListInfo;
import structs.UserData;
import structs.UserToFileMapping;

/**
 * make this add multiple people to multiple files using threads
 * @author Matt
 */
public class ParseCertificationForm extends AbstractDriveCommand<File>{
    private final CertificationFormInfo certFormInfo;
    private final FileListInfo fileListInfo;
    private final String campRootId;
    private final String accessListId;
    
    public ParseCertificationForm(ServiceAccess service, CertificationFormInfo source, FileListInfo fileList, String campFolderRootId, String accessListFileId) {
        super(service);
        certFormInfo = source;
        fileListInfo = fileList;
        campRootId = campFolderRootId;
        accessListId = accessListFileId;
    }

    @Override
    public File execute() throws IOException {
        // first, create the camp folder. TODO: make this check if a folder for the week already exist
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String timeStr = LocalDateTime.now().format(formatter);
        
        File createdFolder = new CreateFolder(getServiceAccess(), String.format("%s (test)", timeStr), campRootId).execute();
        
        // second, extract the campers from the form responses
        ArrayList<UserData> newCampers = new ReadCertificationForm(getServiceAccess(), certFormInfo).execute();
        //newCampers.forEach(System.out::println);
        
        
        // next, get the list of files campers will get access to
        ArrayList<CamperFile> files = new ReadFileList(getServiceAccess(), fileListInfo).execute();
        //files.forEach(System.out::println);
        
        // construct the list of requests to make
        ArrayList<UserToFileMapping> whoGetsWhat = UserToFileMapping.constructUserFileList(newCampers, files);
        
        List<UserToFileMapping> viewReqs = whoGetsWhat
            .stream()
            .filter(mapping->mapping.getFile().getAccessType()==AccessType.VIEW)
            .collect(Collectors.toList());
        List<UserToFileMapping> copyUs = whoGetsWhat
            .stream()
            .filter(mapping->mapping.getFile().getAccessType()==AccessType.EDIT)
            .collect(Collectors.toList());
        
        
        // construct the commands
        List<AbstractDriveCommand> commands = new ArrayList<>();
        commands.add(new Copy(getServiceAccess(), copyUs, createdFolder.getId()));
        commands.add(new GiveAccess(getServiceAccess(), viewReqs)); // GiveAccess automatically batches
        commands.forEach(System.out::println);
        
        commands.parallelStream().forEach((cmd)->{
            try{
                cmd.execute();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        String[] newMcUsers = newCampers.stream().map((userData)->{
            return userData.getMinecraftUsername();
        }).toArray((size)->new String[size]);
        
        new AddToAccessList(getServiceAccess(), accessListId, newMcUsers).execute();
        return createdFolder;
    }
}
