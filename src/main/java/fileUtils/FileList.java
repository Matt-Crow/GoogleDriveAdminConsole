package fileUtils;

import java.util.AbstractCollection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import structs.AccessType;
import structs.DetailedFileInfo;
import structs.FileListInfo;
import structs.SimpleFileInfo;

/**
 * probably want to make this extends something
 * @author Matt
 */
public class FileList {
    private final LinkedList<SimpleFileInfo> fileInfo;
    
    public FileList(){
        fileInfo = new LinkedList<>();
    }
    public FileList(AbstractCollection<? extends SimpleFileInfo> files){
        this();
        files.iterator().forEachRemaining((file)->fileInfo.add(file));
    }
    public FileList(List<? extends SimpleFileInfo> files){
        this();
        files.iterator().forEachRemaining((file)->fileInfo.add(file));
    }
    public FileList(CsvFile csvFile){
        this();
        int idCol = csvFile.getColumnIdx(FileListInfo.ID_HEADER);
        int descCol = csvFile.getColumnIdx(FileListInfo.DESC_HEADER);
        int urlCol = csvFile.getColumnIdx(FileListInfo.URL_HEADER);
        int accTypeCol = csvFile.getColumnIdx(FileListInfo.ACC_TYPE_HEADER);
        
        csvFile.forEachBodyRow((List<String> row)->{
            try{
                if(row.get(idCol).trim().isEmpty()){
                    throw new RuntimeException(String.format("Row does not contain a file ID, it has the following data: %s", String.join(", ", row)));
                }
                boolean ableToDownload = AccessType.fromString(row.get(accTypeCol)).shouldAllowDownload();
                fileInfo.add(new DetailedFileInfo(row.get(idCol), row.get(descCol), row.get(urlCol), ableToDownload));
            } catch (RuntimeException ex){
                ex.printStackTrace();
            }
        });
    }   
    
    public final FileList forEach(Consumer<SimpleFileInfo> f){
        fileInfo.forEach(f);
        return this;
    }
}
