package structs;

/**
 * This class is used as the 
 * minimum amount of information
 * required to get a file for sharing.
 * It is extended by DetailedFileInfo,
 * which is more descriptive.
 * 
 * @author Matt Crow
 */
public class SimpleFileInfo {
    private final String fileId;
    
    /**
     * 
     * @param fileOrDirId the ID of the Google 
     * Drive file or folder this represents
     */
    public SimpleFileInfo(String fileOrDirId){
        fileId = fileOrDirId;
    }
    
    /**
     * 
     * @return the ID of the Google Drive file or folder this represents
     */
    public final String getFileId(){
        return fileId;
    }
    
    @Override
    public String toString(){
        return String.format("Google Drive file#%s", fileId);
    }
}
