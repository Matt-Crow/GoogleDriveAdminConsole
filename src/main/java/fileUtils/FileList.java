package fileUtils;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import structs.AccessType;
import structs.DetailedFileInfo;
import structs.FileListInfo;
import structs.SimpleFileInfo;

/**
 *
 * @author Matt
 */
public class FileList {
    private final LinkedList<SimpleFileInfo> fileInfo;
    
    public FileList(){
        fileInfo = new LinkedList<>();
    }
    public FileList(AbstractCollection<? extends SimpleFileInfo> files){
        this();
        files.iterator().forEachRemaining((file)->fileInfo.addLast(file));
    }
    public FileList(CsvFile csvFile){
        this();
        int idCol = csvFile.getColumnIdx(FileListInfo.ID_HEADER);
        int descCol = csvFile.getColumnIdx(FileListInfo.DESC_HEADER);
        int urlCol = csvFile.getColumnIdx(FileListInfo.URL_HEADER);
        int accTypeCol = csvFile.getColumnIdx(FileListInfo.ACC_TYPE_HEADER);
        
        csvFile.forEachBodyRow((List<String> row)->{
            try{
                boolean ableToDownload = AccessType.fromString(row.get(accTypeCol)).shouldAllowDownload();
                fileInfo.add(new DetailedFileInfo(row.get(idCol), row.get(descCol), row.get(urlCol), ableToDownload));
            } catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }
        });
    }
    
}
