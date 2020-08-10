package drive;

import java.io.IOException;

/**
 * The AbstractDriveCommand class serves as the
 * base for custom commands meant to interact with
 * the Google Drive or any files contained therein.
 * 
 * @author Matt Crow
 * @param <T> the type execute() will return.
 */
public abstract class AbstractDriveCommand<T> {
    private final GoogleDriveService service;
    
    /**
     * Creates a new drive command,
     * but does not yet run it. You 
     * must invoke the execute() method
     * to perform the command instructions.
     */
    public AbstractDriveCommand(){
        service = GoogleDriveService.getInstance();
    }
    
    /**
     * Use this method to gain easy access to the Google
     * Drive services used by the program.
     * 
     * @return the application's instance of GoogleDriveService
     */
    public final GoogleDriveService getServiceAccess(){
        return service;
    }
    
    /**
     * Executes the command.
     * 
     * @return 
     * @throws IOException if any fatal errors occur during command execution 
     */
    public abstract T execute() throws IOException;
}
