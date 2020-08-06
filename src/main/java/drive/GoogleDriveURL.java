package drive;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * I need an exact definition of how Drive URLs are formatted so this can work
 * @author Matt
 */
public class GoogleDriveURL {
    private final String fileId;
    
    private static final String FILE_BASE_URL = "drive.google.com/file/d/";
    private static final String FORM_BASE_URL = "docs.google.com/forms/d/";
    private static final String OTHER_DOC_URL = "docs.google.com/*/d/"; // matches spreadsheets and documents at least.
    
    public GoogleDriveURL(String url){
        String id = "ID not found";
        // split on '/', '?', or '&'
        String[] urlParts = url.split("/|\\?|&");
        int startIdx = -1;
        int endIdx = -1;
        if(url.contains(FILE_BASE_URL)){
            startIdx = url.indexOf(FILE_BASE_URL) + FILE_BASE_URL.length();
            endIdx = url.indexOf("/", startIdx);
            id = (endIdx == -1) ? url.substring(startIdx) : url.substring(startIdx, endIdx);
        } else if(url.contains(FORM_BASE_URL)){
            /*
            Google forms have two different sharing URLS:
            https://docs.google.com/forms/d/ID/edit
            and
            https://docs.google.com/forms/d/e/ID/viewform
            
            So if we detect the "forms" part of the URL, we need to check which specific format the URL is in
            */
            startIdx = url.indexOf(FORM_BASE_URL) + FORM_BASE_URL.length();
            endIdx = url.lastIndexOf("/");
            id = url.substring(startIdx, endIdx); // first, assume it is of the first form (without the "e/")
            if(id.startsWith("e/")){
                // now get rid of the e/ at the start, if it exists
                id = id.substring("e/".length());
            }
        } else if(url.matches(OTHER_DOC_URL)){
            // need to check for spreadsheets and documents after forms, as forms urls are formatted oddly
            Pattern regexPattern = Pattern.compile(OTHER_DOC_URL);
            Matcher match = regexPattern.matcher(id);
            match.find();
            String g = match.group();
            System.out.println();
        } else if(url.contains("id=")){
            // easiest case
            startIdx = url.indexOf("id=") + "id=".length(); // incorrectly matches "gid=..."
            endIdx = url.indexOf('/', startIdx);
            id = (endIdx == -1) ? url.substring(startIdx) : url.substring(startIdx, endIdx);
        } else {
            for(String part : urlParts){
                System.out.println(part);
            }
        }
        
        fileId = id;
    }
    
    @Override
    public String toString(){
        return fileId;
    }
    
    public static void main(String[] args){
        String[] text = new String[]{
            "https://drive.google.com/file/d/1pyrbJFYVKFk1RkaSANXrkS8BIZd3gQxU/view", // done
            "https://docs.google.com/forms/d/e/1FAIpQLSe7BFxmwXyu0H-oaneRAsnP5yB_5JG7lE1VcNHrarNBAyl6xA/viewform", //done
            "https://docs.google.com/forms/d/1XtY2THaxhAY_9nSlx_jtEKDOe5gBRdF-Fdpuocf7PkM/edit?usp=sharing", //done
            "https://drive.google.com/open?id=1WJhqZOlRWo0b5ylBx4n8G2LvuH6JnM3W0epKebu8THA", // done
            "https://docs.google.com/spreadsheets/d/1aCW3dxF-B-s_NWrBq88xvCNjy6bO8wCNJlGDFdH7R1g/edit#gid=836243040",
            "https://docs.google.com/document/d/1enalt5bwo21Ja5dr7RF0aHtzXdq5ExYlpkOHQlISsG4/edit?usp=sharing",
            "https://drive.google.com/drive/folders/1E2y2l330vqYHLGSfU7NZ-4OHDmRO6qEK?usp=sharing",
            "https://docs.google.com/spreadsheets/d/1dtWFKcLKM8WyNVRV8G9Fmb-MANzvPqQwsiJOEFxCYOA/edit?usp=sharing",
            "1dtWFKcLKM8WyNVRV8G9Fmb-MANzvPqQwsiJOEFxCYOA"
        };
        for(String txt : text){
            System.out.println(txt + ": " + new GoogleDriveURL(txt).toString());
        }
    }
}
