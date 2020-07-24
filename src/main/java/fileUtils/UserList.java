package fileUtils;

import java.util.AbstractCollection;
import java.util.LinkedList;
import java.util.List;
import structs.DetailedUserInfo;
import structs.Groups;
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
    public static final String GROUP_HEADER = "groups";
    
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
        int emailCol = csvFile.getColumnIdx(EMAIL_HEADER);
        
        csvFile.forEachBodyRow((CsvRow row)->{
            try{
                if(row.get(emailCol).trim().isEmpty()){
                    throw new CsvException(String.format("Row does not contain an email address, it has the following data: %s", row.toString()));
                }
                
                String groupsString = row.getOrDefault(GROUP_HEADER, Groups.ALL_GROUP).trim();
                if(groupsString.isEmpty()){
                    groupsString = Groups.ALL_GROUP;
                }
                
                add(new DetailedUserInfo(
                    row.getOrDefault(NAME_HEADER, "name not set").trim(), 
                    row.get(emailCol).trim(), 
                    row.getOrDefault(MC_USER_HEADER, "Minecraft username not set").trim(), 
                    new Groups(groupsString)
                ));
            } catch (CsvException ex){
                Logger.logError(ex);
            }
        });
    }
}
