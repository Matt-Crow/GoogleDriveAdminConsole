package structs;

/**
 * This class is used to hold data
 * downloaded from the file list via
 * the ReadFileList command. The
 * primary purpose of this class is
 * to provide better details when
 * outputting information about the
 * file this references.
 * 
 * @author Matt Crow
 */
public class FileInfo implements Groupable {
    private final String fileId;
    private final String fileDesc;
    private final boolean isDownloadable;
    private final Groups groups;
    
    /**
     * 
     * @param id the ID of the Google Drive file or folder this represents
     * @param desc a brief textual description of the file
     * @param forGroups the groups this file should be given to
     * @param viewersShouldDownload whether or not viewers should be able to download this file
     */
    public FileInfo(String id, String desc, Groups forGroups, boolean viewersShouldDownload){
        fileId = id;
        fileDesc = desc;
        isDownloadable = viewersShouldDownload;
        groups = forGroups;
    }
    
    /**
     * 
     * @return the ID of the Google Drive file or folder this represents
     */
    public final String getFileId(){
        return fileId;
    }
    
    public final boolean shouldCopyBeEnabled(){
        return isDownloadable;
    }
    
    @Override
    public Groups getGroups() {
        return groups;
    }
    
    @Override
    public String toString(){
        return String.format(
            "People of group %s should be given %s access to %s (%s)",
            groups.toString(),
            (isDownloadable) ? "copy" : "view",
            fileId,
            fileDesc
        );
    }
}
