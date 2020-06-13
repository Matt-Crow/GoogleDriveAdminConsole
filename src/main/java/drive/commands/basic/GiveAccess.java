package drive.commands.basic;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import drive.commands.AbstractDriveCommand;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import services.ServiceAccess;
import structs.AccessType;
import structs.UserToFileMapping;

/**
 * GiveAccess is used to grant either view or edit access to files.
 * It automatically batches the requests it makes.
 * 
 * @author Matt Crow
 */
public class GiveAccess extends AbstractDriveCommand<Boolean>{
    private static final int MAX_BATCH_SIZE = 100;
    /**
     * The list of details on the files, users, and permissions it should grant access for
     */
    private final List<UserToFileMapping> mappings;
    
    private final List<List<UserToFileMapping>> batches;
    
    /**
     * Constructs a request to give access to a given list of user-to-file mappings.
     * Note that, like all other DriveCommands, this DOES NOT automatically execute the request: 
     * MAKE SURE YOU CALL THE .execute() method AAAAHHHHHH
     * @param service the Google Services singleton... might just access this globally in the future.
     * @param mapping a List of the various UserToFileMappings this should satisfy.
     */
    public GiveAccess(ServiceAccess service, List<UserToFileMapping> mapping) {
        super(service);
        mappings = mapping;
        batches = new ArrayList<>();
        int totalReqs = mappings.size();
        for(int reqNum = 0; reqNum < totalReqs; reqNum++){
            if(reqNum % MAX_BATCH_SIZE == 0){
                // new batch
                batches.add(new ArrayList<>());
            }
            batches.get(reqNum / MAX_BATCH_SIZE).add(mappings.get(reqNum));
        }
    }
    public GiveAccess(ServiceAccess service, UserToFileMapping mapping){
        this(service, Arrays.asList(mapping));
    }
    
    /**
     * Splits the list of mappings this needs to resolve into
     * batches of mappings. Each of these batches should be fed
     * into a batch request in .execute().
     * 
     * @return the batched mappings
     */
    /*
    private List<List<UserToFileMapping>> constructBatches(){
        List<List<UserToFileMapping>> batches = new ArrayList<>();
        int totalReqs = mappings.size();
        for(int reqNum = 0; reqNum < totalReqs; reqNum++){
            if(reqNum % MAX_BATCH_SIZE == 0){
                // new batch
                batches.add(new ArrayList<>());
            }
            batches.get(reqNum / MAX_BATCH_SIZE).add(mappings.get(reqNum));
        }
        return batches;
    } */
    
    /**
     * Batches all of the UserToFile mappings contained herein,
     * giving people the access to files detailed in each mapping.
     * 
     * @return true, as a placeholder
     * @throws IOException if anything fails. Note that this automatically catches failures in each batch
     */
    @Override
    public Boolean execute() throws IOException {
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
        BatchRequest batchReq = null;
        Drive.Permissions perms = getDrive().permissions();
        Permission p = null;
        
        for(List<UserToFileMapping> batch : batches){ // maybe parallel stream this
            batchReq = getDrive().batch();
            
            for(UserToFileMapping mapping : batch){
                p = new Permission();
                p.setEmailAddress(mapping.getUser().getEmail());
                // from the documentation: "Valid values are: - user - group - domain - anyone"
                p.setType("user");
                p.setRole(AccessType.VIEW.getDriveRole());//mapping.getFile().getAccessType().getDriveRole());
                Drive.Permissions.Create create = perms.create(mapping.getFile().getFileId(), p);
                create.setSendNotificationEmail(Boolean.TRUE);
                // non-gmail accounts need notification emails to get access to the file
                // there is no way to check whether or not they are gmail, so we need to send notifications
                create.queue(batchReq, jsonCallback);
            }
            try{
                batchReq.execute();
            }catch(IOException ex){
                ex.printStackTrace();
            }      
            
        }
        return true;
    }
    
    @Override
    public String toString(){
        StringBuilder bob = new StringBuilder();
        bob.append("GiveAccess:\n");
        batches.forEach((batch) -> {
            bob.append("===BATCH===\n");
            batch.forEach((mapping)->{
                bob.append("\t").append(mapping.toString()).append("\n");
            });
        });
        return bob.toString();
    }
}
