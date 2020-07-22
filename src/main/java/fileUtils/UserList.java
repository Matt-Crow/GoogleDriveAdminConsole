package fileUtils;

import java.util.AbstractCollection;
import java.util.LinkedList;
import java.util.List;
import structs.DetailedUserInfo;
import structs.SimpleUserInfo;
import sysUtils.Logger;

/**
 *
 * @author Matt
 */
public class UserList extends LinkedList<SimpleUserInfo>{
    /*
    Attributes belonging to all user lists
    */
    public static final String NAME_HEADER = "name";
    public static final String EMAIL_HEADER = "email";
    public static final String MC_USER_HEADER = "minecraft username";
    public static final String GROUP_HEADER = "group";
    
    public UserList(){
        super();
    }
    
    public UserList(AbstractCollection<? extends SimpleUserInfo> users){
        this();
        addAll(users);
    }
    
    public UserList(List<? extends SimpleUserInfo> users){
        this();
        addAll(users);
    }

    public UserList(CsvFile csvFile) throws CsvException{
        this();
        addFromCsv(csvFile);
    }
    
    public final void addFromCsv(CsvFile csvFile) throws CsvException{
        int nameCol = csvFile.getColumnIdx(NAME_HEADER);
        int emailCol = csvFile.getColumnIdx(EMAIL_HEADER);
        int mcUserCol = csvFile.getColumnIdx(MC_USER_HEADER);
        int groupCol = csvFile.getColumnIdx(GROUP_HEADER);
        
        csvFile.forEachBodyRow((List<String> row)->{
            try{
                if(row.get(emailCol).trim().isEmpty()){
                    throw new CsvException(String.format("Row does not contain an email address, it has the following data: %s", String.join(", ", row)));
                }
                add(new DetailedUserInfo(row.get(nameCol), row.get(emailCol), row.get(mcUserCol), row.get(groupCol)));
            } catch (CsvException ex){
                Logger.logError(ex);
            }
        });
    }
}
