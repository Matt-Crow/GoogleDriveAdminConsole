package drive.commands.implementations;

import com.google.api.services.sheets.v4.model.ValueRange;
import start.GoogleDriveService;
import drive.commands.utils.AbstractDriveCommand;
import fileUtils.CsvFile;
import fileUtils.UserList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import structs.UserListProperties;
import structs.DetailedUserInfo;

/**
 *
 * @author Matt
 */
public class ReadUserList extends AbstractDriveCommand<UserList>{
    private final UserListProperties sourceInfo;
    
    public ReadUserList(GoogleDriveService service, UserListProperties formInfo) {
        super(service);
        sourceInfo = formInfo;
    }

    @Override
    public UserList doExecute() throws IOException {        
        ValueRange values = getSheets().spreadsheets().values().get(sourceInfo.getFileId(), sourceInfo.getSheetName()).execute();
        List<List<Object>> data = values.getValues();
        CsvFile content = CsvFile.from(data);
        return new UserList(content);
    }

}
