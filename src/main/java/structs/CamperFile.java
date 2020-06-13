package structs;

/**
 *
 * @author Matt
 */
public class CamperFile {
    private final String fileId;
    private final String fileDesc;
    private final String fileUrl;
    
    public CamperFile(String id, String desc, String url){
        fileId = id;
        fileDesc = desc;
        fileUrl = url;
    }
    
    public String getFileId(){
        return fileId;
    }
    
    @Override
    public String toString(){
        return String.format(
            "Campers should be given view access to %s (%s),"
            + "which you can find at %s",
            fileId,
            fileDesc,
            fileUrl
        );
    }
}
