package drive.commands.camp;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import drive.commands.AbstractDriveCommand;
import drive.commands.basic.CreateFolder;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import structs.Level;

/**
 * make this add multiple people to multiple files using threads
 * @author Matt
 */
public class ParseCertificationForm extends AbstractDriveCommand<File[]>{
    private final String certFormId;
    private final String fileListId;
    private final String campRootId;
    
    public ParseCertificationForm(Drive d, String certificationFormId, String fileListSpreadsheetId, String campFolderRootId) {
        super(d);
        certFormId = certificationFormId;
        fileListId = fileListSpreadsheetId;
        campRootId = campFolderRootId;
    }

    @Override
    public File[] execute() throws IOException {
        Level[] levels = Level.values();
        File[] createdFolders = new File[levels.length];
        
        // first, create the camp folders. TODO: make this check if folders for the week already exist
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String timeStr = LocalDateTime.now().format(formatter);
        
        for(int i = 0; i < levels.length; i++){
            createdFolders[i] = new CreateFolder(
                getDrive(), 
                String.format("%s %s (test)", levels[i].toString(), timeStr),
                campRootId
            ).execute();
        }
        
        return createdFolders;
    }
}
