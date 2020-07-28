package sysUtils;

import fileUtils.FileReadWriteUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import start.Main;
import structs.GoogleSheetProperties;

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
public final class FileSystem {
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String APP_FOLDER = Paths.get(USER_HOME, Main.APPLICATION_NAME).toString();
    public static final String LOG_FOLDER = Paths.get(APP_FOLDER, "logs").toString();
    public static final String PROPS_FOLDER = Paths.get(APP_FOLDER, "properties").toString();
    public static final String CREDENTIALS_FOLDER = Paths.get(APP_FOLDER, "credentials").toString();
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM-dd-uuuu_hh_mm_a");
    
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
    public static final FileSystem getInstance(){
        if(instance == null){
            instance = new FileSystem();
        }
        return instance;
    }
    
    /**
     * Call this method at the start of the
     * application to ensure all the folders it
     * needs are created.
     * 
     * @throws IOException if the folders cannot
     * be created, nor do they exist. At this point,
     * this is considered a fatal error for the
     * application, and it should quit.
     */
    public final void init() throws IOException{
        createMissingDirs();
    }
    
    /**
     * Checks to see if a directory exists.
     * 
     * @param path the file path to check.
     * @return whether or not a directory
     * exists at the given location.
     */
    private boolean dirExists(String path){
        Path p = Paths.get(path);
        return Files.exists(p) && Files.isDirectory(p);
    }
    
    /**
     * Creates a directory at the given path, if no such directory
     * exists.
     * 
     * @param path the path of the directory to create if it doesn't
     * yet exist.
     * 
     * @throws IOException if the directory does not exist, but 
     * can't be created either.
     */
    private void createIfNoExist(String path) throws IOException{
        if(!dirExists(path)){
            Logger.log(String.format("Creating folder %s", path));
            Files.createDirectory(Paths.get(path));
        }
    }
    
    /**
     * Regenerates the directories the
     * application needs
     */
    private void createMissingDirs() throws IOException{
        String[] dirsToCreate = new String[]{
            APP_FOLDER, 
            LOG_FOLDER, 
            PROPS_FOLDER,
            CREDENTIALS_FOLDER
        };
        
        for(String dir : dirsToCreate){
            createIfNoExist(dir);
        }
    }
    
    /**
     * Saves some GoogleSheetProperties to the props folder.
     * 
     * @param name the name to give the property file.
     * @param props the GoogleSheetProperties to save.
     * @return the path to the file the properties were saved to
     * @throws IOException if the props fail to save.
     */
    public String saveProperties(String name, GoogleSheetProperties props) throws IOException{
        String path = Paths.get(PROPS_FOLDER, String.format("%s %s.properties", name, LocalDateTime.now().format(DATE_FORMAT))).toString();
        File f = new File(path);
        props.save(f);
        return f.getAbsolutePath();
    }
    /**
     * Saves the contents of the Logger to the
     * logs directory, with the current date
     * appended to the end of the file name.
     * 
     * @throws IOException if it fails to write
     * the file.
     */
    public void saveLog() throws IOException{
        String logText = Logger.getLog();
        String logPath = Paths.get(LOG_FOLDER, String.format("log %s.txt", LocalDateTime.now().format(DATE_FORMAT))).toString();
        FileReadWriteUtil.writeFile(new File(logPath), logText);
    }
}
