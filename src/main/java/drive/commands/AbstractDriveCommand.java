package drive.commands;

import com.google.api.services.drive.Drive;
import java.io.IOException;

/**
 *
 * @author Matt
 * @param <T> the type execute() will return. May move to subtype later
 */
public abstract class AbstractDriveCommand<T> {
    private final Drive driveService;
    public AbstractDriveCommand(Drive d){
        driveService = d;
    }
    
    public final Drive getDrive(){
        return driveService;
    }
    
    public abstract T execute() throws IOException;
}
