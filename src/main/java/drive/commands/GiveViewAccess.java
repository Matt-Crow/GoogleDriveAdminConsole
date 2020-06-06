package drive.commands;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import java.io.IOException;
import java.util.HashSet;

/**
 *
 * @author Matt
 */
public class GiveViewAccess extends AbstractDriveCommand<Boolean>{
    private final String viewFileId;
    private final String[] forEmails;
    
    public GiveViewAccess(Drive d, String fileId, String[] emails) {
        super(d);
        viewFileId = fileId;
        forEmails = emails;
    }

    @Override
    public Boolean execute() throws IOException {
        // need some way of batching this
        
        Permission p;
        Drive.Permissions perm = getDrive().permissions();
        // giving view access to a folder automatically grants view access to files under it
        for(String email : forEmails){
            p = new Permission();
            p.setEmailAddress(email);
            // from the documentation: "Valid values are: - user - group - domain - anyone"
            p.setType("user");
            // from the documentation: "the following are currently allowed: - owner - organizer - fileOrganizer - writer - commenter - reader"
            p.setRole("reader");
            perm.create(viewFileId, p).setSendNotificationEmail(Boolean.FALSE).execute();
        }
        
        return true;
    }
}
