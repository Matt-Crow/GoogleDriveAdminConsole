package structs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * We will likely change the structure of how the file list works later
 * @author Matt
 */
public class FileListInfo extends Properties{
    /*
    Attributes belonging to all file lists
    */
    public static final String ID_HEADER = "ID";
    public static final String DESC_HEADER = "desc";
    public static final String URL_HEADER = "URL";
    public static final String ACC_TYPE_HEADER = "access type";
    
    
    private static final String SHEET_ID_KEY = "spreadsheetId";
    private static final String SHEET_NAME_KEY = "sheetName";
    
    public FileListInfo(String spreadsheetFileId, String filesSheetName){
        super();
        setProperty(SHEET_ID_KEY, spreadsheetFileId);
        setProperty(SHEET_NAME_KEY, filesSheetName);
    }
    
    public FileListInfo(){
        this("spreadsheetIdHere", "fileSheetNameHere");
    }
    
    public String getFileId(){
        return getProperty(SHEET_ID_KEY);
    }
    
    public String getSheetName(){
        return getProperty(SHEET_NAME_KEY);
    }
    
    public void save(File f) throws FileNotFoundException, IOException{
        store(new FileOutputStream(f), "This file contains information about which Google Sheet to query for information on a file list");
    }
    
    @Override
    public String toString(){
        StringBuilder bob = new StringBuilder();
        bob.append("FILE LIST INFO:\n");
        forEach((k, v)->bob.append(String.format("* %s : %s \n", k, v)));
        return bob.toString();
    }
}
