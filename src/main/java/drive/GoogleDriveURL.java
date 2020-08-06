package drive;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Matt
 */
public class GoogleDriveURL {
    private final String fileId;
    // https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
    /*
                                              .*: any number of any character
                                                                        (?<id>REGEX): create a new group named "id", it contains whatever matches REGEX
                                                                              any number of non-'/' or '?' characters
    */
    private static final String FILE_REGEX = ".*drive.google.com/file/d/(?<id>[^/?]*).*";
    private static final String FORM_REGEX = ".*docs.google.com/forms/d/(e/)?+(?<id>[^/?]*).*"; // "e/" once or not at all
    private static final String NON_FORM_DOC_REGEX = ".*docs.google.com/(?<type>.*)/d/(?<id>.*)/.*"; // matches spreadsheets, documents, and presentationws at least. 
    // '.' in regex means any character, '*' means any number of. (?<NAME>REGEX) means "make a new group called NAME out of anything that matches REGEX"
    private static final String FOLDER_REGEX = ".*drive.google.com/drive/folders/(?<id>[^/?]*)[/?]*.*"; 
    //                                                                                 any character, but not '/' or '?' 
    private static final String ID_REGEX = ".*id=(?<id>[^/?]*).*";
    
    public GoogleDriveURL(String url){
        String id = "ID not found";
        if(Pattern.matches(FILE_REGEX, url)){
            Pattern p = Pattern.compile(FILE_REGEX);
            Matcher m = p.matcher(url);
            m.find();
            id = m.group("id");
        } else if(Pattern.matches(FORM_REGEX, url)){
            Pattern p = Pattern.compile(FORM_REGEX);
            Matcher m = p.matcher(url);
            m.find();
            id = m.group("id");
        } else if(Pattern.matches(NON_FORM_DOC_REGEX, url)){
            // need to check for spreadsheets and documents after forms, as forms urls are formatted oddly
            Pattern regexPattern = Pattern.compile(NON_FORM_DOC_REGEX);
            Matcher match = regexPattern.matcher(url);
            match.find();
            //String type = match.group("type");
            String idGroup = match.group("id");
            //System.out.println("Type is " + type);
            //System.out.println("ID is " + idGroup);
            id = idGroup;
        } else if(Pattern.matches(FOLDER_REGEX, url)){
            Pattern p = Pattern.compile(FOLDER_REGEX);
            Matcher folderMatcher = p.matcher(url);
            folderMatcher.find();
            String idGroup = folderMatcher.group("id");
            id = idGroup;
        } else if(Pattern.matches(ID_REGEX, url)){
            // easiest case
            Pattern p = Pattern.compile(ID_REGEX);
            Matcher m = p.matcher(url);
            m.find();
            id = m.group("id");
        } else {
            id = url;
        }
        
        fileId = id;
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
            System.out.println(txt + ": " + new GoogleDriveURL(txt).toString());
        }
    }
}
