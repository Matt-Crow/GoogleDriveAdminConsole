package plugins.implementations.fileListReader;

import com.google.api.services.sheets.v4.model.ValueRange;
import start.GoogleDriveService;
import drive.commands.utils.AbstractDriveCommand;
import fileUtils.CsvFile;
import fileUtils.FileList;
import java.io.IOException;
import java.util.List;
import structs.GoogleSheetProperties;

/**
 * currently reads the admin console, but we may change that in the future
 * @author Matt
 */
public class ReadFileList extends AbstractDriveCommand<FileList>{
    private final GoogleSheetProperties sourceInfo;
    
    public ReadFileList(GoogleSheetProperties source) {
        super();
        sourceInfo = source;
    }
    
    @Override
    public FileList doExecute() throws IOException {
        ValueRange range = getSheets().spreadsheets().values().get(sourceInfo.getFileId(), sourceInfo.getSheetName()).execute();
        List<List<Object>> data = range.getValues();
        CsvFile file = CsvFile.from(data);
        
        return new FileList(file);
    }

}
