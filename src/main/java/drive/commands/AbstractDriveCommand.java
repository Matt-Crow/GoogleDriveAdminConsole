package drive.commands;

import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;
import services.ServiceAccess;
import java.io.IOException;

/**
 *
 * @author Matt
 * @param <T> the type execute() will return. May move to subtype later
 */
public abstract class AbstractDriveCommand<T> {
    private final ServiceAccess service;
    public AbstractDriveCommand(ServiceAccess serv){
        service = serv;
    }
    
    public final ServiceAccess getServiceAccess(){
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
