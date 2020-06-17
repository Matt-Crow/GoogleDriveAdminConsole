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
    
    /**
     * 
     * @param id the ID of the Google Drive file or folder this represents
     * @param desc a brief textual description of the file
     * @param url a URL camp admins can use to view the file
     */
    public DetailedFileInfo(String id, String desc, String url){
        super(id);
        fileDesc = desc;
        fileUrl = url;
    }
    
    @Override
    public String toString(){
        return String.format(
            "Campers should be given view access to %s (%s),"
            + "which you can find at %s",
            getFileId(),
            fileDesc,
            fileUrl
        );
    }
}
