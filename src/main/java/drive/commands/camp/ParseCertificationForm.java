package drive.commands.camp;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import drive.commands.AbstractDriveCommand;
import drive.commands.basic.CreateFolder;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import structs.CamperFile;
import structs.CertificationFormInfo;
import structs.FileListInfo;
import structs.Level;
import structs.UserData;

/**
 * make this add multiple people to multiple files using threads
 * @author Matt
 */
public class ParseCertificationForm extends AbstractDriveCommand<File[]>{
    private final CertificationFormInfo certFormInfo;
    private final FileListInfo fileListInfo;
    private final String campRootId;
    
    public ParseCertificationForm(Drive d, CertificationFormInfo source, FileListInfo fileList, String campFolderRootId) {
        super(d);
        certFormInfo = source;
        fileListInfo = fileList;
        campRootId = campFolderRootId;
    }

    @Override
    public File[] execute() throws IOException {
        Level[] levels = Level.values();
        File[] createdFolders = new File[levels.length];
        
        // first, create the camp folders. TODO: make this check if folders for the week already exist
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String timeStr = LocalDateTime.now().format(formatter);
        
        // uncomment this when I'm done testing
        /*
        for(int i = 0; i < levels.length; i++){
            createdFolders[i] = new CreateFolder(
                getDrive(), 
                String.format("%s %s (test)", levels[i].toString(), timeStr),
                campRootId
            ).execute();
        }*/
        
        // second, extract the campers from the form responses
        ArrayList<UserData> newCampers = new ReadCertificationForm(getDrive(), certFormInfo).execute();
        //newCampers.forEach(System.out::println);
        
        // TODO: sort them each into camps
        
        // next, get the list of files campers will get access to
        ArrayList<CamperFile> files = new ReadFileList(getDrive(), fileListInfo).execute();
        files.forEach(System.out::println);
        
        return createdFolders;
    }
}
