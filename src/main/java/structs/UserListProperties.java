package structs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * UserListProperties are used to direct the application
 * to Google Sheets containing information on registered users
 * who should be given access to files. Sheets located
 * by these properties must conform to the requirement shown
 * in FileList.
 * 
 * @see fileUtils.FileList
 * @author Matt Crow
 */
public final class UserListProperties extends Properties{
    private static final String CERT_FORM_ID_KEY = "spreadsheetId";
    private static final String RESPONSE_SHEET_NAME_KEY = "responseSheetName";
    
    public UserListProperties(String spreadsheetId, String responseSheetName){
        super();
        setProperty(CERT_FORM_ID_KEY, spreadsheetId);
        setProperty(RESPONSE_SHEET_NAME_KEY, responseSheetName);
    }
    
    public UserListProperties(){
        this("sheetIdHere", "responseSheetNameHere");
    }
    
    public String getFileId(){
        return getProperty(CERT_FORM_ID_KEY);
    }
    
    public String getSheetName(){
        return getProperty(RESPONSE_SHEET_NAME_KEY);
    }
    
    public void save(File f) throws FileNotFoundException, IOException{
        store(new FileOutputStream(f), "This file contains information about which Google Sheet contains user information");
    }
    
    @Override
    public String toString(){
        StringBuilder bob = new StringBuilder();
        bob.append("USER LIST INFO:\n");
        forEach((k, v)->bob.append(String.format("* %s : %s \n", k, v)));
        return bob.toString();
    }
}
