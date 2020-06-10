package drive.commands.basic;

import structs.AccessType;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import drive.commands.AbstractDriveCommand;
import java.io.IOException;

/**
 *
 * @author Matt
 */
public class GiveAccess extends AbstractDriveCommand<Boolean>{
    private final String fileId;
    private final String email;
    private final AccessType type;
    public GiveAccess(Drive d, String fileOrFolderId, String emailAddr, AccessType accessType) {
        super(d);
        fileId = fileOrFolderId;
        email = emailAddr;
        type = accessType;
    }

    @Override
    public Boolean execute() throws IOException {
        // need some way of batching this?
        Drive.Permissions perms = getDrive().permissions();
        Permission p = new Permission();
        p.setEmailAddress(email);
        // from the documentation: "Valid values are: - user - group - domain - anyone"
        p.setType("user");
        p.setRole(type.getDriveRole());
        Drive.Permissions.Create create = perms.create(fileId, p);
        create.setSendNotificationEmail((email.endsWith("@gmail.com")) ? Boolean.FALSE : Boolean.TRUE); 
        // non-gmail accounts need notification emails to get access to the file
        
        create.execute();
        return true;
    }

}
