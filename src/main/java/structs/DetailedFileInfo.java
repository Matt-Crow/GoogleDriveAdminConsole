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
public class DetailedFileInfo extends SimpleFileInfo{
    private final String fileDesc;
    private final String fileUrl;
    private final String group;
    private final boolean isDownloadable;
    
    /**
     * 
     * @param id the ID of the Google Drive file or folder this represents
     * @param desc a brief textual description of the file
     * @param url a URL camp admins can use to view the file
     * @param groupName the group this file should be given to
     * @param viewersShouldDownload whether or not viewers should be able to download this file
     */
    public DetailedFileInfo(String id, String desc, String url, String groupName, boolean viewersShouldDownload){
        super(id);
        fileDesc = desc;
        fileUrl = url;
        group = groupName;
        isDownloadable = viewersShouldDownload;
    }
    
    public final boolean shouldCopyBeEnabled(){
        return isDownloadable;
    }
    
    public final String getGroup(){
        return group;
    }
    
    @Override
    public String toString(){
        return String.format(
            "People of group %s should be given %s access to %s (%s),"
            + "which you can find at %s",
            group,
            (isDownloadable) ? "copy" : "view",
            getFileId(),
            fileDesc,
            fileUrl
        );
    }
}
