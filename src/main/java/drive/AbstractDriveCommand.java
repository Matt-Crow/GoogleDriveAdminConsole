package drive;

import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;
import java.io.IOException;
import sysUtils.Logger;

/**
 *
 * @author Matt
 * @param <T> the type execute() will return. May move to subtype later
 */
public abstract class AbstractDriveCommand<T> {
    private final GoogleDriveService service;
    
    public AbstractDriveCommand(){
        service = GoogleDriveService.getInstance();
    }
    
    public final GoogleDriveService getServiceAccess(){
        return service;
    }
    public final Drive getDrive(){
        return service.getDrive();
    }
    public final Sheets getSheets(){
        return service.getSheets();
    }
    
    /**
     * Executes the command, automatically logging
     * any errors that occur, then re-throws them.
     * 
     * @return
     * @throws IOException 
     */
    public final T execute() throws IOException {
        T ret = null;
        try {
            ret = doExecute();
        } catch(IOException ex){
            Logger.logError(ex);
            throw ex;
        }
        return ret;
    }
    
    public abstract T doExecute() throws IOException;
}
