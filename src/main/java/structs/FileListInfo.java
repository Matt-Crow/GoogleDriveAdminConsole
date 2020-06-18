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
    private static final String SHEET_ID_KEY = "spreadsheetId";
    private static final String VIEW_SHEET_NAME_KEY = "viewSheetName";
    private static final String COPY_SHEET_NAME_KEY = "copySheetName";
    private static final String ID_HEADER_KEY = "idHeader";
    private static final String DESC_HEADER_KEY = "descHeader";
    private static final String URL_HEADER_KEY = "urlHeader";
    private static final String LEVEL_HEADER_KEY = "levelHeader";
    
    public FileListInfo(String spreadsheetFileId, String viewFilesSheetName, String copyFileSheetName, String idCol, String descCol, String urlCol, String levelCol){
        super();
        setProperty(SHEET_ID_KEY, spreadsheetFileId);
        setProperty(VIEW_SHEET_NAME_KEY, viewFilesSheetName);
        setProperty(COPY_SHEET_NAME_KEY, copyFileSheetName);
        setProperty(ID_HEADER_KEY, idCol);
        setProperty(DESC_HEADER_KEY, descCol);
        setProperty(URL_HEADER_KEY, urlCol);
        setProperty(LEVEL_HEADER_KEY, levelCol);
    }
    
    public FileListInfo(){
        this("spreadsheetIdHere", "viewFileSheetNameHere", "copyFileSheetNameHere", "idColumnHeaderHere", "descColumnHeaderHere", "urlColumnHeaderHere", "levelColumnHeaderHere");
    }
    
    public String getFileId(){
        return getProperty(SHEET_ID_KEY);
    }
    
    public String getViewSheetName(){
        return getProperty(VIEW_SHEET_NAME_KEY);
    }
    
    public String getCopySheetName(){
        return getProperty(COPY_SHEET_NAME_KEY);
    }
    
    public String getFileIdHeader(){
        return getProperty(ID_HEADER_KEY);
    }
    
    public String getDescHeader(){
        return getProperty(DESC_HEADER_KEY);
    }
    
    public String getUrlHeader(){
        return getProperty(URL_HEADER_KEY);
    }
    
    public String getParticipationLevelHeader(){
        return getProperty(LEVEL_HEADER_KEY);
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
