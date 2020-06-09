package drive.commands;

import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import drive.DriveAccess;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import sheets.CsvParser;
import users.UserData;

/**
 *
 * @author Matt
 */
public class ReadCertificationForm extends AbstractDriveCommand<ArrayList<UserData>>{
    private final String spreadsheetFileId;
    
    public ReadCertificationForm(Drive d, String fileId) {
        super(d);
        spreadsheetFileId = fileId;
    }

    @Override
    public ArrayList<UserData> execute() throws IOException {
        ArrayList<UserData> users = new ArrayList<>();
        try {
            Sheets service = DriveAccess.getInstance().getSheets();
            ValueRange values = service.spreadsheets().values().get(spreadsheetFileId, "Form Responses 1").execute();
            List<List<Object>> data = values.getValues();
            
            String[] names = CsvParser.getColumn(data, "Participants Name");
            String[] emails = CsvParser.getColumn(data, "Participant's email");
            String[] mcUsers = CsvParser.getColumn(data, "Participant's Minecraft username ... Add To Science Report");
            String[] levels = CsvParser.getColumn(data, "Participating At What Level?");
            
            for(int i = 0; i < names.length && i < emails.length && i < mcUsers.length && i < levels.length; i++){
                if(!(names[i].isEmpty() || emails[i].isEmpty() || mcUsers[i].isEmpty() || levels[i].isEmpty())){
                    users.add(new UserData(names[i], emails[i], mcUsers[i], levels[i]));
                }
            }
        } catch (GeneralSecurityException ex) {
            ex.printStackTrace();
            System.exit(ex.hashCode());
        }
        return users;
    }

}
