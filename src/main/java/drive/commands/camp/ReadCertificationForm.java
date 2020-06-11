package drive.commands.camp;

import com.google.api.services.sheets.v4.model.ValueRange;
import services.ServiceAccess;
import drive.commands.AbstractDriveCommand;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sheets.CsvParser;
import structs.CertificationFormInfo;
import structs.UserData;

/**
 *
 * @author Matt
 */
public class ReadCertificationForm extends AbstractDriveCommand<ArrayList<UserData>>{
    private final CertificationFormInfo sourceInfo;
    
    public ReadCertificationForm(ServiceAccess service, CertificationFormInfo formInfo) {
        super(service);
        sourceInfo = formInfo;
    }

    @Override
    public ArrayList<UserData> execute() throws IOException {
        ArrayList<UserData> users = new ArrayList<>();
        
        ValueRange values = getSheets().spreadsheets().values().get(sourceInfo.getFileId(), sourceInfo.getSheetName()).execute();
        List<List<Object>> data = values.getValues();

        String[] names = CsvParser.getColumn(data, sourceInfo.getNameHeader());
        String[] emails = CsvParser.getColumn(data, sourceInfo.getEmailHeader());
        String[] mcUsers = CsvParser.getColumn(data, sourceInfo.getMinecraftUsernameHeader());
        String[] levels = CsvParser.getColumn(data, sourceInfo.getParticipationLevelHeader());

        for(int i = 0; i < names.length && i < emails.length && i < mcUsers.length && i < levels.length; i++){
            if(!(names[i].isEmpty() || emails[i].isEmpty() || mcUsers[i].isEmpty() || levels[i].isEmpty())){
                users.add(new UserData(names[i], emails[i], mcUsers[i], levels[i]));
            }
        }
        
        return users;
    }

}
