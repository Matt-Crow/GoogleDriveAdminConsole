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
    private final String spreadsheetId;
    private final String viewSheetName;
    private final String copySheetName;
    private final String idHeader;
    private final String descHeader;
    private final String urlHeader;
    private final String levelHeader;
    
    public FileListInfo(String spreadsheetFileId, String viewFilesSheetName, String copyFileSheetName, String idCol, String descCol, String urlCol, String levelCol){
        super();
        setProperty("spreadsheetId", spreadsheetFileId);
        spreadsheetId = spreadsheetFileId;
        setProperty("viewSheetName", viewFilesSheetName);
        viewSheetName = viewFilesSheetName;
        setProperty("copySheetName", copyFileSheetName);
        copySheetName = copyFileSheetName;
        setProperty("idHeader", idCol);
        idHeader = idCol;
        setProperty("descHeader", descCol);
        descHeader = descCol;
        setProperty("urlHeader", urlCol);
        urlHeader = urlCol;
        setProperty("levelHeader", levelCol);
        levelHeader = levelCol;
    }
    
    public FileListInfo(){
        this("spreadsheetIdHere", "viewFileSheetNameHere", "copyFileSheetNameHere", "idColumnHeaderHere", "descColumnHeaderHere", "urlColumnHeaderHere", "levelColumnHeaderHere");
    }
    
    public String getFileId(){
        return spreadsheetId;
    }
    
    public String getViewSheetName(){
        return viewSheetName;
    }
    
    public String getCopySheetName(){
        return copySheetName;
    }
    
    public String getFileIdHeader(){
        return idHeader;
    }
    
    public String getDescHeader(){
        return descHeader;
    }
    
    public String getUrlHeader(){
        return urlHeader;
    }
    
    public String getParticipationLevelHeader(){
        return levelHeader;
    }
    
    public void save(File f) throws FileNotFoundException, IOException{
        store(new FileOutputStream(f), "This file contains information about which Google Sheet to query for information on a file list");
    }
}
