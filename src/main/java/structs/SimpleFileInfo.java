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
public class SimpleFileInfo implements Groupable{
    private final String fileId;
    private final Groups groups;
    
    /**
     * 
     * @param fileOrDirId the ID of the Google 
     * Drive file or folder this represents
     * @param g the Groups this file belongs to
     */
    public SimpleFileInfo(String fileOrDirId, Groups g){
        fileId = fileOrDirId;
        groups = g;
    }
    
    public SimpleFileInfo(String fileOrDirId){
        this(fileOrDirId, new Groups(Groups.ALL_GROUP));
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
        return String.format("Google Drive file#%s for groups %s", fileId, groups.toString());
    }

    @Override
    public Groups getGroups() {
        return groups;
    }
}
