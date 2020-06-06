package drive.commands;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matt
 */
public class Copy extends AbstractDriveCommand<File>{
    private final String origId;
    private final String toDirId;
    private final String userName;
    private final String userEmail;
    
    public Copy(Drive d, String fileId, String outputDirId, String camperName, String email) {
        super(d);
        origId = fileId;
        toDirId = outputDirId;
        userName = camperName;
        userEmail = email;
    }

    @Override
    public File execute() throws IOException {
        File newFile = null;
        File orig = getDrive().files().get(origId).execute();
        
        // cannot directly copy folders
        if(orig.getMimeType().equals("application/vnd.google-apps.folder")){
            throw new RuntimeException("doesn't support copying folders yet");
        }
        
        File changes = new File();
        
        ArrayList<String> parents = new ArrayList<>();
        parents.add(toDirId);
        changes.setParents(parents);
        changes.setName(userName + "'s " + orig.getName());
        
        // can't change permissions with copy, so I need to use Permissions afterward
        newFile = getDrive().files().copy(origId, changes).execute();
        
        Permission p = new Permission();
        p.setEmailAddress(userEmail);
        p.setType("user");
        p.setRole("writer");
        System.out.println(newFile.getId());
        getDrive().permissions().create(newFile.getId(), p).execute();
        
        return newFile;
    }

}
