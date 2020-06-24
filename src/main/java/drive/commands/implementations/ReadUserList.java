package drive.commands.implementations;

import com.google.api.services.sheets.v4.model.ValueRange;
import start.ServiceAccess;
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
public class ReadUserList extends AbstractDriveCommand<ArrayList<DetailedUserInfo>>{
    private final UserListProperties sourceInfo;
    
    public ReadUserList(ServiceAccess service, UserListProperties formInfo) {
        super(service);
        sourceInfo = formInfo;
    }

    @Override
    public ArrayList<DetailedUserInfo> execute() throws IOException {
        ArrayList<DetailedUserInfo> users = new ArrayList<>();
        
        ValueRange values = getSheets().spreadsheets().values().get(sourceInfo.getFileId(), sourceInfo.getSheetName()).execute();
        List<List<Object>> data = values.getValues();
        CsvFile content = CsvFile.from(data);
        
        String[] names = content.getColumn(UserList.NAME_HEADER).toArray(new String[0]);
        String[] emails = content.getColumn(UserList.EMAIL_HEADER).toArray(new String[0]);
        String[] mcUsers = content.getColumn(UserList.MC_USER_HEADER).toArray(new String[0]);
        String[] levels = content.getColumn(UserList.LEVEL_HEADER).toArray(new String[0]);

        for(int i = 0; i < names.length && i < emails.length && i < mcUsers.length && i < levels.length; i++){
            if(!(names[i].isEmpty() || emails[i].isEmpty() || mcUsers[i].isEmpty() || levels[i].isEmpty())){
                users.add(new DetailedUserInfo(names[i], emails[i], mcUsers[i], levels[i]));
            }
        }
        
        return users;
    }

}
