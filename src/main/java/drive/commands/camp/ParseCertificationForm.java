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
        
        // batch view requests
        List<List<UserToFileMapping>> viewBatches = new ArrayList<>();
        for(int i = 0; i < viewReqs.size(); i++){
            if(i % 100 == 0){ // need to do in batches of 100
                viewBatches.add(new ArrayList<>());
            }
            viewBatches.get(i / 100).add(viewReqs.get(i));
        }
        
        // construct the commands
        List<AbstractDriveCommand> commands = new ArrayList<>();
        viewBatches.forEach((mappingList)->{
            commands.add(new GiveAccess(getServiceAccess(), mappingList));
        });
        copyUs.forEach((userToFile)->{
            commands.add(new Copy(getServiceAccess(), userToFile.getFile().getFileId(), createdFolder.getId(), userToFile.getUser().getName(), userToFile.getUser().getEmail()));
        });
        
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
