package structs;

/**
 *
 * @author Matt
 */
public final class CertificationFormInfo {
    private final String fileId;
    private final String sheetName;
    private final String nameHeader;
    private final String emailHeader;
    private final String mcUserHeader;
    private final String levelHeader;
    
    public CertificationFormInfo(String spreadsheetId, String responseSheetName, String nameCol, String emailCol, String mcUserCol, String levelCol){
        fileId = spreadsheetId;
        sheetName = responseSheetName;
        nameHeader = nameCol;
        emailHeader = emailCol;
        mcUserHeader = mcUserCol;
        levelHeader = levelCol;
    }
    
    public String getFileId(){
        return fileId;
    }
    
    public String getSheetName(){
        return sheetName;
    }
    
    public String getNameHeader(){
        return nameHeader;
    }
    
    public String getEmailHeader(){
        return emailHeader;
    }
    
    public String getMinecraftUsernameHeader(){
        return mcUserHeader;
    }
    
    public String getParticipationLevelHeader(){
        return levelHeader;
    }
}
