package drive.commands.implementations;

import drive.commands.utils.CommandBatch;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import drive.commands.utils.AbstractDriveCommand;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import start.ServiceAccess;
import structs.UserToFileMapping;
import sysUtils.Logger;

/**
 * GiveViewAccess is used to grant either view or edit access to files.
 * It automatically batches the requests it makes.
 * 
 * @author Matt Crow
 */
public class GiveViewAccess extends AbstractDriveCommand<Boolean>{
    private static final String VIEW_ROLE = "reader";
    /**
     * The list of details on the files and users it should grant access for
     */
    private final List<UserToFileMapping> mappings;
    
    /**
     * Constructs a request to give access to a given list of user-to-file mappings.
     * Note that, like all other DriveCommands, this DOES NOT automatically execute the request: 
     * MAKE SURE YOU CALL THE .execute() method AAAAHHHHHH
     * @param service the Google Services singleton... might just access this globally in the future.
     * @param mapping a List of the various UserToFileMappings this should satisfy.
     */
    public GiveViewAccess(ServiceAccess service, List<UserToFileMapping> mapping) {
        super(service);
        mappings = mapping;
    }
    public GiveViewAccess(ServiceAccess service, UserToFileMapping mapping){
        this(service, Arrays.asList(mapping));
    }
    
    /**
     * Batches all of the UserToFile mappings contained herein,
     * giving people the access to files detailed in each mapping.
     * 
     * @return true, as a placeholder
     * @throws IOException if anything fails. Note that this automatically catches failures in each batch
     */
    @Override
    public Boolean execute() throws IOException {
        Drive.Permissions perms = getDrive().permissions();
        
        List<Drive.Permissions.Create> reqs = mappings.stream().map((UserToFileMapping mapping)->{
            Permission p = new Permission();
            p.setEmailAddress(mapping.getUser().getEmail());
            // from the documentation: "Valid values are: - user - group - domain - anyone"
            p.setType("user");
            p.setRole(VIEW_ROLE);
            Drive.Permissions.Create create = null;
            try {
                create = perms.create(mapping.getFile().getFileId(), p);
                create.setSendNotificationEmail(Boolean.TRUE);
                // non-gmail accounts need notification emails to get access to the file
                // there is no way to check whether or not they are gmail, so we need to send notifications
            } catch (IOException ex) {
                Logger.logError(ex);
            }
            
            return create;
        }).filter((driveReq)->driveReq != null).collect(Collectors.toList());
        
        CommandBatch<Permission> batches = new CommandBatch<>(getServiceAccess(), reqs);
        batches.execute();
        
        return true;
    }
}
