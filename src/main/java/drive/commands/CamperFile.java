package drive.commands;

/**
 *
 * @author Matt
 */
public class CamperFile {
    private final String fileId;
    private final String fileDesc;
    private final String fileUrl;
    private final AccessType whatAccess;
    
    public CamperFile(String id, String desc, String url, AccessType accessType){
        fileId = id;
        fileDesc = desc;
        fileUrl = url;
        whatAccess = accessType;
    }
    
    public String getFileId(){
        return fileId;
    }
    
    public AccessType getAccessType(){
        return whatAccess;
    }
    
    @Override
    public String toString(){
        return String.format(
            "Campers should be given %s access to %s (%s),"
            + "which you can find at %s", 
            whatAccess.getDriveRole(),
            fileId,
            fileDesc,
            fileUrl
        );
    }
}
