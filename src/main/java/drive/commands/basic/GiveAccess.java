package drive.commands.basic;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import drive.commands.AbstractDriveCommand;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import services.ServiceAccess;
import structs.UserToFileMapping;

/**
 *
 * @author Matt
 */
public class GiveAccess extends AbstractDriveCommand<Boolean>{
    private final List<UserToFileMapping> mappings;
    public GiveAccess(ServiceAccess service, List<UserToFileMapping> mapping) {
        super(service);
        mappings = mapping;
    }
    public GiveAccess(ServiceAccess service, UserToFileMapping mapping){
        this(service, Arrays.asList(mapping));
    }

    @Override
    public Boolean execute() throws IOException {
        BatchRequest batch = getDrive().batch();
        JsonBatchCallback<Permission> jsonCallback = new JsonBatchCallback<Permission>() {
            @Override
            public void onFailure(GoogleJsonError gje, HttpHeaders hh) throws IOException {
                System.err.println(gje);
                System.err.println(hh);
            }

            @Override
            public void onSuccess(Permission t, HttpHeaders hh) throws IOException {
                System.out.println(t);
            }
        };
        
        Drive.Permissions perms = getDrive().permissions();
        for(UserToFileMapping mapping : mappings){
            Permission p = new Permission();
            p.setEmailAddress(mapping.getUser().getEmail());
            // from the documentation: "Valid values are: - user - group - domain - anyone"
            p.setType("user");
            p.setRole(mapping.getFile().getAccessType().getDriveRole());
            Drive.Permissions.Create create = perms.create(mapping.getFile().getFileId(), p);
            create.setSendNotificationEmail(Boolean.TRUE);
            // non-gmail accounts need notification emails to get access to the file
            // there is no way to check whether or not they are gmail, so we need to send notifications
            create.queue(batch, jsonCallback);
        }
        batch.execute();
        return true;
    }
}
