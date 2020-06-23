package drive.commands.implementations;

import structs.DetailedFileInfo;
import com.google.api.services.sheets.v4.model.ValueRange;
import start.ServiceAccess;
import drive.commands.utils.AbstractDriveCommand;
import fileUtils.CsvFile;
import fileUtils.FileList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import structs.FileListInfo;

/**
 * currently reads the admin console, but we may change that in the future
 * @author Matt
 */
public class ReadFileList extends AbstractDriveCommand<FileList>{
    private final FileListInfo sourceInfo;
    
    public ReadFileList(ServiceAccess service, FileListInfo source) {
        super(service);
        sourceInfo = source;
    }
    
    @Override
    public FileList execute() throws IOException {
        ValueRange range = getSheets().spreadsheets().values().get(sourceInfo.getFileId(), sourceInfo.getSheetName()).execute();
        List<List<Object>> data = range.getValues();
        CsvFile file = CsvFile.from(data);
        return new FileList(file);
    }

}
