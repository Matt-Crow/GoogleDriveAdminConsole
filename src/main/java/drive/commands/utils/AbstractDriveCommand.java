package drive.commands.utils;

import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;
import start.GoogleDriveService;
import java.io.IOException;
import java.security.GeneralSecurityException;
import sysUtils.Logger;

/**
 *
 * @author Matt
 * @param <T> the type execute() will return. May move to subtype later
 */
public abstract class AbstractDriveCommand<T> {
    private final GoogleDriveService service;
    
    public AbstractDriveCommand(){
        GoogleDriveService serv = null;
        try {
            serv = GoogleDriveService.getInstance();
            // will be able to remove this try-catch once I split getInstance() into validate() and getInstance()
        } catch (GeneralSecurityException ex) {
            Logger.logError(ex);
        } catch (IOException ex) {
            Logger.logError(ex);
        }
        service = serv;
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
