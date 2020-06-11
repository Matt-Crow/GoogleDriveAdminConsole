package drive.commands.basic;

import com.google.api.services.drive.model.File;
import drive.commands.AbstractDriveCommand;
import java.io.IOException;
import java.util.ArrayList;
import services.ServiceAccess;

/**
 *
 * @author Matt
 */
public class CreateFolder extends AbstractDriveCommand<File>{
    private final String name;
    private final String parentId;
    
    public CreateFolder(ServiceAccess service, String newFolderName, String parentFolderId) {
        super(service);
        name = newFolderName;
        parentId = parentFolderId;
    }

    @Override
    public File execute() throws IOException {
        File metadata = new File();
        metadata.setName(name);
        metadata.setMimeType("application/vnd.google-apps.folder");
        
        ArrayList<String> parents = new ArrayList<>();
        parents.add(parentId);
        metadata.setParents(parents);
        
        File newFile = getDrive().files().create(metadata).execute();
        return newFile;
    }

}
