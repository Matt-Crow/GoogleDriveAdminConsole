package drive.commands.utils;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.services.drive.DriveRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import start.ServiceAccess;
import sysUtils.Logger;

/**
 *
 * @author Matt
 */
public class CommandBatch<T> extends AbstractDriveCommand<List<T>>{
    private final List<List<DriveRequest<T>>> batches;
    
    private static final int MAX_BATCH_SIZE = 100;
    
    public CommandBatch(ServiceAccess access, List<? extends DriveRequest<T>> reqs){
        super(access);
        batches = new ArrayList<>();
        // perform batching
        int numReqs = reqs.size();
        for(int i = 0; i < numReqs; i++){
            if(i % MAX_BATCH_SIZE == 0){
                // new batch
                batches.add(new ArrayList<>());
            }
            batches.get(i / MAX_BATCH_SIZE).add(reqs.get(i));
        }
    }

    @Override
    public List<T> execute() throws IOException {
        List<T> ret = new ArrayList<>();
        JsonBatchCallback<T> jsonCallback = new JsonBatchCallback<T>() {
            @Override
            public void onFailure(GoogleJsonError gje, HttpHeaders hh) throws IOException {
                Logger.logError(gje.toPrettyString());
                Logger.logError(hh.toString());
            }

            @Override
            public void onSuccess(T t, HttpHeaders hh) throws IOException {
                Logger.log(t.toString());
                ret.add(t);
            }
        };
        
        BatchRequest currentBatchReq = null;
        for(List<DriveRequest<T>> batch : batches){
            currentBatchReq = getDrive().batch();
            for(DriveRequest<T> req : batch){
                try{
                    req.queue(currentBatchReq, jsonCallback);
                } catch(IOException ex){
                    Logger.logError(ex);
                }
            }
            try{
                currentBatchReq.execute();
            } catch(IOException ex){
                Logger.logError(ex);
            }
        }
        return ret;
    }
}
