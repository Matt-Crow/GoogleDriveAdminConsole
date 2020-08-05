package drive;

/**
 * I need an exact definition of how Drive URLs are formatted so this can work
 * @author Matt
 */
public class GoogleDriveURL {
    private final String fileId;
    
    public GoogleDriveURL(String url){
        String id = "ID not found";
        // split on '/', '?', or '&'
        String[] urlParts = url.split("/|\\?|&");
        if(url.contains("id=")){
            // easiest case
            int startIdx = url.indexOf("id=") + "id=".length(); // incorrectly matches "gid=..."
            int endIdx = url.indexOf('/', startIdx);
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
            "https://drive.google.com/file/d/1pyrbJFYVKFk1RkaSANXrkS8BIZd3gQxU/view",
            "https://docs.google.com/forms/d/e/1FAIpQLSe7BFxmwXyu0H-oaneRAsnP5yB_5JG7lE1VcNHrarNBAyl6xA/viewform",
            "https://drive.google.com/open?id=1WJhqZOlRWo0b5ylBx4n8G2LvuH6JnM3W0epKebu8THA",
            "https://docs.google.com/spreadsheets/d/1aCW3dxF-B-s_NWrBq88xvCNjy6bO8wCNJlGDFdH7R1g/edit#gid=836243040",
            "https://drive.google.com/drive/folders/1E2y2l330vqYHLGSfU7NZ-4OHDmRO6qEK?usp=sharing",
            "https://docs.google.com/spreadsheets/d/1dtWFKcLKM8WyNVRV8G9Fmb-MANzvPqQwsiJOEFxCYOA/edit?usp=sharing"
        };
        for(String txt : text){
            System.out.println(txt + ": " + new GoogleDriveURL(txt).toString());
        }
    }
}
