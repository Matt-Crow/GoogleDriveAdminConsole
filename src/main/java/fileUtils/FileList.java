package fileUtils;

import java.util.AbstractCollection;
import java.util.LinkedList;
import java.util.List;
import structs.AccessType;
import structs.DetailedFileInfo;
import structs.SimpleFileInfo;

/**
 * The FileList class is used to convert multiple formats
 * into a list of file information objects. I'm not sure
 * if I'll just put in a parser that returns a LinkecList
 * if file infos, as that is essentially what this is, but
 * for now, I'll keep this for clarity's sake.
 * 
 * @author Matt Crow
 */
public class FileList extends LinkedList<SimpleFileInfo>{
    /*
    Attributes belonging to all file lists
     */
    public static final String ID_HEADER = "ID";
    public static final String DESC_HEADER = "desc";
    public static final String URL_HEADER = "URL";
    public static final String ACC_TYPE_HEADER = "access type";
    
    public FileList(){
        super();
    }
    public FileList(AbstractCollection<? extends SimpleFileInfo> files){
        this();
        addAll(files);
    }
    public FileList(List<? extends SimpleFileInfo> files){
        this();
        addAll(files);
    }
    
    /**
     * The given csvFile must contain the following headers:
     * ID, desc, URL, and access type
     * @param csvFile
     * @throws CsvException 
     */
    public FileList(CsvFile csvFile) throws CsvException{
        this();
        int idCol = csvFile.getColumnIdx(ID_HEADER);
        int descCol = csvFile.getColumnIdx(DESC_HEADER);
        int urlCol = csvFile.getColumnIdx(URL_HEADER);
        int accTypeCol = csvFile.getColumnIdx(ACC_TYPE_HEADER);
        
        csvFile.forEachBodyRow((List<String> row)->{
            try{
                if(row.get(idCol).trim().isEmpty()){
                    throw new CsvException(String.format("Row does not contain a file ID, it has the following data: %s", String.join(", ", row)));
                }
                boolean ableToDownload = AccessType.fromString(row.get(accTypeCol)).shouldAllowDownload();
                add(new DetailedFileInfo(row.get(idCol), row.get(descCol), row.get(urlCol), ableToDownload));
            } catch (CsvException ex){
                ex.printStackTrace();
            }
        });
    }
}
