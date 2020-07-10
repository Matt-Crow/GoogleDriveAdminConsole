package drive.commands.utils;

import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;
import start.GoogleDriveService;
import java.io.IOException;

/**
 *
 * @author Matt
 * @param <T> the type execute() will return. May move to subtype later
 */
public abstract class AbstractDriveCommand<T> {
    private final GoogleDriveService service;
    public AbstractDriveCommand(GoogleDriveService serv){
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
    
    
    public abstract T execute() throws IOException;
}
