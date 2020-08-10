package drive;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A GoogleDriveFileId is used to
 extract a Google drive file ID
 from a String.
 * 
 * @author Matt Crow
 */
public class GoogleDriveFileId {
    private final String fileId;
    // I'm not sure the exact format of Google drive file IDs, so I can't throw that in the regex
    // https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
    //                                               .*: any number of any character
    //                                                                          (?<id>REGEX): create a new group named "id", it contains whatever matches REGEX
    //                                                                                any number of non-'/' or '?' characters
    private static final String FILE_REGEX =         ".*drive.google.com/file/d/(?<id>[^/?]*).*";
    private static final String FORM_REGEX =         ".*docs.google.com/forms/d/(e/)?+(?<id>[^/?]*).*"; // "e/" once or not at all
    private static final String NON_FORM_DOC_REGEX = ".*docs.google.com/(?<type>.*)/d/(?<id>.*)/.*"; // matches spreadsheets, documents, and presentationws at least. 
    // '.' in regex means any character, '*' means any number of. (?<NAME>REGEX) means "make a new group called NAME out of anything that matches REGEX"
    private static final String FOLDER_REGEX = ".*drive.google.com/drive/folders/(?<id>[^/?]*)[/?]*.*"; 
    //                                                                                 any character, but not '/' or '?' 
    private static final String ID_REGEX = ".*id=(?<id>[^/?]*).*";
    
    private static final String[] PATTERNS = {
        FILE_REGEX,
        FORM_REGEX,
        NON_FORM_DOC_REGEX,
        FOLDER_REGEX,
        ID_REGEX
    };
    
    public GoogleDriveFileId(String url){
        String id = null;
        for(int patternNum = 0; patternNum < PATTERNS.length && id == null; patternNum++){
            if(Pattern.matches(PATTERNS[patternNum], url)){
                id = extractIdGroup(PATTERNS[patternNum], url);
            }
        }
        if(id == null){
            id = url;
        }
        fileId = id;
    }
    
    /**
     * 
     * @param regexPattern
     * @param url
     * @return the capture group named "id" matched by regexPattern in url 
     */
    private String extractIdGroup(String regexPattern, String url){
        Pattern p = Pattern.compile(regexPattern);
        Matcher m = p.matcher(url);
        m.find();
        return m.group("id");
    }
    
    @Override
    public String toString(){
        return fileId;
    }
    
    public static void main(String[] args){
        String[] text = new String[]{
            "https://drive.google.com/file/d/1pyrbJFYVKFk1RkaSANXrkS8BIZd3gQxU/view",
            "https://docs.google.com/forms/d/e/1FAIpQLSe7BFxmwXyu0H-oaneRAsnP5yB_5JG7lE1VcNHrarNBAyl6xA/viewform",
            "https://docs.google.com/forms/d/1XtY2THaxhAY_9nSlx_jtEKDOe5gBRdF-Fdpuocf7PkM/edit?usp=sharing",
            "https://drive.google.com/open?id=1WJhqZOlRWo0b5ylBx4n8G2LvuH6JnM3W0epKebu8THA",
            "https://docs.google.com/spreadsheets/d/1aCW3dxF-B-s_NWrBq88xvCNjy6bO8wCNJlGDFdH7R1g/edit#gid=836243040",
            "https://docs.google.com/document/d/1enalt5bwo21Ja5dr7RF0aHtzXdq5ExYlpkOHQlISsG4/edit?usp=sharing",
            "https://drive.google.com/drive/folders/1E2y2l330vqYHLGSfU7NZ-4OHDmRO6qEK?usp=sharing",
            "https://drive.google.com/drive/folders/1E2y2l330vqYHLGSfU7NZ-4OHDmRO6qEK",
            "https://docs.google.com/spreadsheets/d/1dtWFKcLKM8WyNVRV8G9Fmb-MANzvPqQwsiJOEFxCYOA/edit?usp=sharing",
            "https://docs.google.com/presentation/d/1ZqKtQSQIlm-p0V_Y47yjadWeBKypdScCvZShSMb2hDg/edit?usp=sharing",
            "1dtWFKcLKM8WyNVRV8G9Fmb-MANzvPqQwsiJOEFxCYOA"
        };
        
        for(String txt : text){
            System.out.println(txt + ": " + new GoogleDriveFileId(txt).toString());
        }
    }
}
