package drive.commands.implementations;

import structs.DetailedFileInfo;
import com.google.api.services.sheets.v4.model.ValueRange;
import start.ServiceAccess;
import drive.commands.utils.AbstractDriveCommand;
import fileUtils.CsvFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import structs.AccessType;
import structs.FileListInfo;

/**
 * currently reads the admin console, but we may change that in the future
 * @author Matt
 */
public class ReadFileList extends AbstractDriveCommand<ArrayList<DetailedFileInfo>>{
    private final FileListInfo sourceInfo;
    
    public ReadFileList(ServiceAccess service, FileListInfo source) {
        super(service);
        sourceInfo = source;
    }
    
    @Override
    public ArrayList<DetailedFileInfo> execute() throws IOException {
        ArrayList<DetailedFileInfo> ret = new ArrayList<>();
        ValueRange range = getSheets().spreadsheets().values().get(sourceInfo.getFileId(), sourceInfo.getSheetName()).execute();
        List<List<Object>> data = range.getValues();
        CsvFile file = CsvFile.from(data);
        String[] ids = file.getColumn(FileListInfo.ID_HEADER).toArray(new String[0]);
        String[] descs = file.getColumn(FileListInfo.DESC_HEADER).toArray(new String[0]);
        String[] urls = file.getColumn(FileListInfo.URL_HEADER).toArray(new String[0]);
        String[] accType = file.getColumn(FileListInfo.ACC_TYPE_HEADER).toArray(new String[0]);
        boolean ableToDownload = false;
        for(int i = 0; i < ids.length && i < descs.length && i < urls.length; i++){
            if(!(ids[i].isEmpty() || descs[i].isEmpty() || urls[i].isEmpty())){
                try{
                    ableToDownload = AccessType.fromString(accType[i]).shouldAllowDownload();
                    ret.add(new DetailedFileInfo(ids[i], descs[i], urls[i], ableToDownload));
                } catch(IllegalArgumentException ex){
                    ex.printStackTrace();
                }
                
            }
        }
        return ret;
    }

}
