package fileUtils;

import java.util.AbstractCollection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import structs.AccessType;
import structs.DetailedFileInfo;
import structs.FileListProperties;
import structs.SimpleFileInfo;

/**
 * probably want to make this extends something
 * @author Matt
 */
public class FileList {

    public static final String URL_HEADER = "URL";
    public static final String ACC_TYPE_HEADER = "access type";
    /*
    Attributes belonging to all file lists
     */
    public static final String ID_HEADER = "ID";
    public static final String DESC_HEADER = "desc";
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
        int idCol = csvFile.getColumnIdx(ID_HEADER);
        int descCol = csvFile.getColumnIdx(DESC_HEADER);
        int urlCol = csvFile.getColumnIdx(URL_HEADER);
        int accTypeCol = csvFile.getColumnIdx(ACC_TYPE_HEADER);
        
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
