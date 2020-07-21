package sysUtils;

import java.nio.file.Paths;

/**
 * The FileSystem class is
 * used by the application
 * to store and load files
 * in the application directory,
 * this way, the user doesn't
 * need to tell it where to
 * locate some files.
 * 
 * @author Matt Crow
 */
public class FileSystem {
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String APP_FOLDER = Paths.get(USER_HOME, "googleDriveAdminConsole").toString();
    private static final String LOG_FOLDER = Paths.get(APP_FOLDER, "logs").toString();
    private static final String PROPS_FOLDER = Paths.get(APP_FOLDER, "properties").toString();
    
    private static FileSystem instance;
    
    private FileSystem(){
        if(instance != null){
            throw new ExceptionInInitializerError("FileSystem is a singleton: Don't call the constructor manually, please use FileSystem.getInstance() instead");
        }
    }
    
    /**
     * Use this method to gain access to the
     * various application folder methods.
     * 
     * @return the FileSystem interface used
     * by the application.
     */
    public final FileSystem getInstance(){
        if(instance == null){
            instance = new FileSystem();
        }
        return instance;
    }
}
