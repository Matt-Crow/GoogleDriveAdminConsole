package fileUtils;

import drive.GoogleDriveFileId;
import java.util.AbstractCollection;
import java.util.LinkedList;
import java.util.List;
import structs.AccessType;
import structs.FileInfo;
import structs.Groups;
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
public class FileList extends LinkedList<FileInfo>{
    /*
    Attributes belonging to all file lists
     */
    public static final String ID_HEADER = "ID";
    public static final String DESC_HEADER = "desc";
    public static final String ACC_TYPE_HEADER = "access type";
    public static final String GROUP_HEADER = "groups";
    
    public FileList(){
        super();
    }
    public FileList(AbstractCollection<FileInfo> files){
        this();
        addAll(files);
    }
    public FileList(List<FileInfo> files){
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
        
        csvFile.forEachBodyRow((CsvRow row)->{
            try{
                if(row.get(idCol).trim().isEmpty()){
                    throw new CsvException(String.format("Row does not contain a file ID, it has the following data: %s", row.toString()));
                }
                
                String groupsString = row.getOrDefault(GROUP_HEADER, Groups.ALL_GROUP).trim();
                if(groupsString.isEmpty()){
                    groupsString = Groups.ALL_GROUP;
                }
                
                // how to handle optional AccessType?
                boolean ableToDownload = AccessType.fromString(row.get(ACC_TYPE_HEADER)).shouldAllowDownload();
                add(new FileInfo(
                    new GoogleDriveFileId(row.get(idCol).trim()), 
                    row.getOrDefault(DESC_HEADER, "no description").trim(), 
                    new Groups(groupsString), 
                    ableToDownload
                ));
            } catch (CsvException ex){
                Logger.logError(ex);
            }
        });
    }
}
