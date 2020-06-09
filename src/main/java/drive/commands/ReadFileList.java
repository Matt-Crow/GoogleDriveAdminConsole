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
 * currently reads the admin console, but we may change that in the future
 * @author Matt
 */
public class ReadFileList extends AbstractDriveCommand<ArrayList<CamperFile>>{
    private final String spreadsheetId;
    private final String viewSheetName;
    private final String copySheetName;
    
    public ReadFileList(Drive d, String workbookId, String viewSheetTitle, String copySheetTitle) {
        super(d);
        spreadsheetId = workbookId;
        viewSheetName = viewSheetTitle;
        copySheetName = copySheetTitle;
    }
    
    private ArrayList<CamperFile> getFilesFromSheet(String sheetName, AccessType accessType) throws IOException{
        ArrayList<CamperFile> files = new ArrayList<>();
        try {
            Sheets service = DriveAccess.getInstance().getSheets();
            
            ValueRange range = service.spreadsheets().values().get(spreadsheetId, sheetName).execute();
            List<List<Object>> data = range.getValues();
            
            String[] ids = CsvParser.getColumn(data, "ID");
            String[] descs = CsvParser.getColumn(data, "desc");
            String[] urls = CsvParser.getColumn(data, "URL");
            
            for(int i = 0; i < ids.length && i < descs.length && i < urls.length; i++){
                if(!(ids[i].isEmpty() || descs[i].isEmpty() || urls[i].isEmpty())){
                    files.add(new CamperFile(ids[i], descs[i], urls[i], accessType));
                }
            }
        } catch (GeneralSecurityException ex) {
            ex.printStackTrace();
            System.exit(ex.hashCode());
        }
        return files;
    }
    
    @Override
    public ArrayList<CamperFile> execute() throws IOException {
        ArrayList<CamperFile> ret = new ArrayList<>();
        ret.addAll(getFilesFromSheet(viewSheetName, AccessType.VIEW));
        ret.addAll(getFilesFromSheet(copySheetName, AccessType.EDIT));
        return ret;
    }

}
