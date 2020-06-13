package drive.commands.camp;

import structs.CamperFile;
import com.google.api.services.sheets.v4.model.ValueRange;
import services.ServiceAccess;
import drive.commands.AbstractDriveCommand;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sheets.CsvParser;
import structs.FileListInfo;

/**
 * currently reads the admin console, but we may change that in the future
 * @author Matt
 */
public class ReadFileList extends AbstractDriveCommand<ArrayList<CamperFile>>{
    private final FileListInfo sourceInfo;
    
    public ReadFileList(ServiceAccess service, FileListInfo source) {
        super(service);
        sourceInfo = source;
    }
    
    private ArrayList<CamperFile> getFilesFromSheet(String sheetName) throws IOException{
        ArrayList<CamperFile> files = new ArrayList<>();
        ValueRange range = getSheets().spreadsheets().values().get(sourceInfo.getFileId(), sheetName).execute();
        List<List<Object>> data = range.getValues();
        String[] ids = CsvParser.getColumn(data, sourceInfo.getFileIdHeader());
        String[] descs = CsvParser.getColumn(data, sourceInfo.getDescHeader());
        String[] urls = CsvParser.getColumn(data, sourceInfo.getUrlHeader());
        for(int i = 0; i < ids.length && i < descs.length && i < urls.length; i++){
            if(!(ids[i].isEmpty() || descs[i].isEmpty() || urls[i].isEmpty())){
                files.add(new CamperFile(ids[i], descs[i], urls[i]));
            }
        }
        return files;
    }
    
    @Override
    public ArrayList<CamperFile> execute() throws IOException {
        ArrayList<CamperFile> ret = new ArrayList<>();
        ret.addAll(getFilesFromSheet(sourceInfo.getViewSheetName()));
        ret.addAll(getFilesFromSheet(sourceInfo.getCopySheetName()));
        return ret;
    }

}
