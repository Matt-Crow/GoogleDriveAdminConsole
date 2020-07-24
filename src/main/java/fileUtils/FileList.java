package fileUtils;

import java.util.AbstractCollection;
import java.util.LinkedList;
import java.util.List;
import structs.AccessType;
import structs.DetailedFileInfo;
import structs.Groups;
import structs.SimpleFileInfo;
import sysUtils.Logger;

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
    public static final String GROUP_HEADER = "groups";
    
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
        addFromCsv(csvFile);
    }
    
    public final void addFromCsv(CsvFile csvFile) throws MissingHeaderException {
        // first, see if it has the mandatory columns.
        int idCol = csvFile.getColumnIdx(ID_HEADER);
        
        // next, optional ones
        // I really don't like this. Need better way
        if(!csvFile.hasHeader(URL_HEADER)){
            csvFile.addHeader(URL_HEADER);
        }
        if(!csvFile.hasHeader(ACC_TYPE_HEADER)){
            csvFile.addHeader(ACC_TYPE_HEADER);
        }
        if(!csvFile.hasHeader(GROUP_HEADER)){
            csvFile.addHeader(GROUP_HEADER);
        }
        int descCol = csvFile.getColumnIdx(DESC_HEADER);; 
        int urlCol = csvFile.getColumnIdx(URL_HEADER);
        int accTypeCol = csvFile.getColumnIdx(ACC_TYPE_HEADER);
        int groupCol = csvFile.getColumnIdx(GROUP_HEADER);
        
        
        csvFile.forEachBodyRow((List<String> row)->{
            try{
                if(row.get(idCol).trim().isEmpty()){
                    throw new CsvException(String.format("Row does not contain a file ID, it has the following data: %s", String.join(", ", row)));
                }
                
                String groupNames = (row.get(groupCol).trim().isEmpty()) ? Groups.ALL_GROUP : row.get(groupCol).trim();
                boolean ableToDownload = AccessType.fromString(row.get(accTypeCol)).shouldAllowDownload();
                add(new DetailedFileInfo(
                    row.get(idCol), 
                    row.get(descCol), 
                    row.get(urlCol), 
                    new Groups(groupNames), 
                    ableToDownload
                ));
            } catch (CsvException ex){
                Logger.logError(ex);
            }
        });
    }
}
