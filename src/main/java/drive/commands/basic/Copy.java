package drive.commands.basic;

import structs.AccessType;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import drive.commands.AbstractDriveCommand;
import java.io.IOException;
import java.util.ArrayList;
import services.ServiceAccess;
import structs.CamperFile;
import structs.UserToFileMapping;

/**
 *
 * @author Matt
 */
public class Copy extends AbstractDriveCommand<File>{
    private final String origId;
    private final String toDirId;
    private final String userName;
    private final String userEmail;
    
    //Idea: make this get the output dir from the camper info
    public Copy(ServiceAccess service, String fileId, String outputDirId, String camperName, String email) {
        super(service);
        origId = fileId;
        toDirId = outputDirId;
        userName = camperName;
        userEmail = email;
    }

    @Override
    public File execute() throws IOException {
        File orig = getDrive().files().get(origId).execute();
        File copy = null;
        
        // cannot directly copy folders
        if(orig.getMimeType().equals("application/vnd.google-apps.folder")){
            copy = new CreateFolder(getServiceAccess(), userName + " 's " + orig.getName(), toDirId).execute();
            
            FileList children = getDrive().files().list()
                .setSpaces("drive")
                .setQ("'" + orig.getId() + "' in parents and trashed = false").execute();
            for(File child : children.getFiles()){
                new Copy(getServiceAccess(), child.getId(), copy.getId(), userName, userEmail).execute();
            };
        } else {
            File changes = new File();
        
            ArrayList<String> parents = new ArrayList<>();
            parents.add(toDirId);
            changes.setParents(parents);
            changes.setName(userName + "'s " + orig.getName());
            
            // can't change permissions with copy, so I need to use Permissions afterward
            copy = getDrive().files().copy(origId, changes).execute();
        }
        
        new GiveAccess(
            getServiceAccess(), 
            new UserToFileMapping(
                null, //camper info
                new CamperFile(copy.getId(), copy.getDescription(), "url here", AccessType.EDIT)
            )
        ).execute();
        
        return copy;
    }

}
