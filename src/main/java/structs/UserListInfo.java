package structs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Matt
 */
public final class UserListInfo extends Properties{
    /*
    Attributes belonging to all user lists
    */
    public static final String NAME_HEADER = "name";
    public static final String EMAIL_HEADER = "email";
    public static final String MC_USER_HEADER = "minecraft username";
    public static final String LEVEL_HEADER = "level";
    
    private static final String CERT_FORM_ID_KEY = "spreadsheetId";
    private static final String RESPONSE_SHEET_NAME_KEY = "responseSheetName";
    
    public UserListInfo(String spreadsheetId, String responseSheetName){
        super();
        setProperty(CERT_FORM_ID_KEY, spreadsheetId);
        setProperty(RESPONSE_SHEET_NAME_KEY, responseSheetName);
    }
    
    public UserListInfo(){
        this("sheetIdHere", "responseSheetNameHere");
    }
    
    public String getFileId(){
        return getProperty(CERT_FORM_ID_KEY);
    }
    
    public String getSheetName(){
        return getProperty(RESPONSE_SHEET_NAME_KEY);
    }
    
    public void save(File f) throws FileNotFoundException, IOException{
        store(new FileOutputStream(f), "This file contains information about which Google Sheet contains certification information");
    }
    
    @Override
    public String toString(){
        StringBuilder bob = new StringBuilder();
        bob.append("CERTIFICATION FORM INFO:\n");
        forEach((k, v)->bob.append(String.format("* %s : %s \n", k, v)));
        return bob.toString();
    }
}
