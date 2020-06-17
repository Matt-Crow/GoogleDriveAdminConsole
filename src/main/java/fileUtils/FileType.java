package fileUtils;

/**
 *
 * @author Matt
 */
public enum FileType {
    DIR("Folder / Directory", new String[]{"directory", "folder"}),
    TXT("Text file", new String[]{"txt"}),
    CSV("Comma Separated Values", new String[]{"csv"});
    
    private final String display;
    private final String[] extensions;
    
    private FileType(String displayText, String[] possibleExtensions){
        display = displayText;
        extensions = possibleExtensions.clone();
    }
    
    public String getDisplayText(){
        return display;
    }
    public String[] getExtensions(){
        return extensions;
    }
}
