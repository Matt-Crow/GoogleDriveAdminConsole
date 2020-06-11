package drive.commands.basic;

import structs.AccessType;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import drive.commands.AbstractDriveCommand;
import java.io.IOException;
import services.ServiceAccess;

/**
 *
 * @author Matt
 */
public class GiveAccess extends AbstractDriveCommand<Boolean>{
    private final String fileId;
    private final String email;
    private final AccessType type;
    public GiveAccess(ServiceAccess service, String fileOrFolderId, String emailAddr, AccessType accessType) {
        super(service);
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
        
        if(create.getSendNotificationEmail()){
            System.out.println("I will send an email to " + email + " telling them they have been given access");
        }
        // non-gmail accounts need notification emails to get access to the file
        
        create.execute();
        return true;
    }

    public static void main(String[] args) throws IOException{
        new GiveAccess(ServiceAccess.getInstance(), "176nV7YENvjUWgxoDIYFYHhXav3ojCn5jQyaV8Vt3tcc", "greengrappler12@gmail.com", AccessType.VIEW).execute();
    }
}
