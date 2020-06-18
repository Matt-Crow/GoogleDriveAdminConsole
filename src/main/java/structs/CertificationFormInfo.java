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
public final class CertificationFormInfo extends Properties{
    private static final String CERT_FORM_ID_KEY = "spreadsheetId";
    private static final String RESPONSE_SHEET_NAME_KEY = "responseSheetName";
    private static final String NAME_HEADER_KEY = "nameHeader";
    private static final String EMAIL_HEADER_KEY = "emailHeader";
    private static final String MC_USER_HEADER_KEY = "minecraftUsernameHeader";
    private static final String LEVEL_HEADER_KEY = "levelHeader";
    
    public CertificationFormInfo(String spreadsheetId, String responseSheetName, String nameCol, String emailCol, String mcUserCol, String levelCol){
        super();
        setProperty(CERT_FORM_ID_KEY, spreadsheetId);
        setProperty(RESPONSE_SHEET_NAME_KEY, responseSheetName);
        setProperty(NAME_HEADER_KEY, nameCol);
        setProperty(EMAIL_HEADER_KEY, emailCol);
        setProperty(MC_USER_HEADER_KEY, mcUserCol);
        setProperty(LEVEL_HEADER_KEY, levelCol);
    }
    
    public CertificationFormInfo(){
        this("sheetIdHere", "responseSheetNameHere", "nameHeaderHere", "emailHeaderHere", "mcHeaderHere", "levelHeaderHere");
    }
    
    public String getFileId(){
        return getProperty(CERT_FORM_ID_KEY);
    }
    
    public String getSheetName(){
        return getProperty(RESPONSE_SHEET_NAME_KEY);
    }
    
    public String getNameHeader(){
        return getProperty(NAME_HEADER_KEY);
    }
    
    public String getEmailHeader(){
        return getProperty(EMAIL_HEADER_KEY);
    }
    
    public String getMinecraftUsernameHeader(){
        return getProperty(MC_USER_HEADER_KEY);
    }
    
    public String getParticipationLevelHeader(){
        return getProperty(LEVEL_HEADER_KEY);
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
