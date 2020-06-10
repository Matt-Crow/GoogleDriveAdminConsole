package structs;

/**
 * We will likely change the structure of how the file list works later
 * @author Matt
 */
public class FileListInfo {
    private final String spreadsheetId;
    private final String viewSheetName;
    private final String copySheetName;
    private final String idHeader;
    private final String descHeader;
    private final String urlHeader;
    private final String levelHeader;
    
    public FileListInfo(String spreadsheetFileId, String viewFilesSheetName, String copyFileSheetName, String idCol, String descCol, String urlCol, String levelCol){
        spreadsheetId = spreadsheetFileId;
        viewSheetName = viewFilesSheetName;
        copySheetName = copyFileSheetName;
        idHeader = idCol;
        descHeader = descCol;
        urlHeader = urlCol;
        levelHeader = levelCol;
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
}
