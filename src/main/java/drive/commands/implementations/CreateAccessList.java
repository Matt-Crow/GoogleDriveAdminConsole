package drive.commands.implementations;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.Permission;
import drive.commands.utils.AbstractDriveCommand;
import java.io.IOException;
import java.util.ArrayList;
import start.ServiceAccess;

/**
 *
 * @author Matt
 */
public class CreateAccessList extends AbstractDriveCommand<com.google.api.services.drive.model.File>{
    private final String folderId;
    public CreateAccessList(ServiceAccess service, String inFolder) {
        super(service);
        folderId = inFolder;
    }
    
    @Override
    public com.google.api.services.drive.model.File execute() throws IOException {
        com.google.api.services.drive.model.File googleFile = new com.google.api.services.drive.model.File();
        googleFile.setName("access list.txt");
        FileContent content = new FileContent("text/plain", java.io.File.createTempFile("temp", "txt"));
        ArrayList<String> parents = new ArrayList<>();
        parents.add(folderId);
        googleFile.setParents(parents);
        
        com.google.api.services.drive.model.File result = getDrive().files().create(googleFile, content).execute();
        // publish to the web
        Permission p = new Permission();
        p.setType("anyone");
        p.setAllowFileDiscovery(true);
        p.setRole("reader");
        getDrive().permissions().create(result.getId(), p).execute();
        return result;
    }

}
