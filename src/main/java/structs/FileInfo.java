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
    private final String fileUrl;
    private final boolean isDownloadable;
    private final Groups groups;
    
    /**
     * 
     * @param id the ID of the Google Drive file or folder this represents
     * @param desc a brief textual description of the file
     * @param url a URL camp admins can use to view the file
     * @param forGroups the groups this file should be given to
     * @param viewersShouldDownload whether or not viewers should be able to download this file
     */
    public FileInfo(String id, String desc, String url, Groups forGroups, boolean viewersShouldDownload){
        fileId = id;
        fileDesc = desc;
        fileUrl = url;
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
            "People of group %s should be given %s access to %s (%s),"
            + "which you can find at %s",
            groups.toString(),
            (isDownloadable) ? "copy" : "view",
            fileId,
            fileDesc,
            fileUrl
        );
    }
}
