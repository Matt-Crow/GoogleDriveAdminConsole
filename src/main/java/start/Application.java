package start;

import drive.GoogleDriveService;
import fileUtils.FileReadWriteUtil;
import gui.MainWindow;
import gui.components.MessagePopup;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import sysUtils.FileSystem;
import sysUtils.Logger;

/**
 * The Application class is the root of the program.
 * It is a singleton, so use Application.getInstance()
 * to access it.
 * 
 * @author Matt Crow
 */
public class Application {    
    private static Application instance;
    
    public static final String APPLICATION_NAME = "Google Drive Administration Console";
    
    private Application(){
        if(instance != null){
            throw new ExceptionInInitializerError("Application is a singleton: Don't directly invoke the constructor, use Application.getInstance() instead");
        }
    }
    
    /**
     * Use this method to get access to the Application
     * class.
     * 
     * @return the instance of the Application 
     */
    public static final Application getInstance(){
        if(instance == null){
            instance = new Application();
        }
        return instance;
    }
        
    /**
     * Initializes the FileSystem interface.
     * If it fails to do so, reports the fatal
     * error, and quits the application.
     */
    private void prepareFileSystem() throws IOException{
        FileSystem.getInstance().init();
        Logger.log("Application folders are good");
    }
    
    private void prepareDriveService() throws IOException, GeneralSecurityException{
        Logger.log("Validating Google Drive Service...");
        GoogleDriveService.validateService();
        Logger.log("Validation successful!");
    }
    
    private void createWindow(GoogleDriveService service){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.logError(ex);
        } catch (InstantiationException ex) {
            Logger.logError(ex);
        } catch (IllegalAccessException ex) {
            Logger.logError(ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.logError(ex);
        }
        MainWindow window = new MainWindow(service);
        window.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                System.out.println("CONTENTS OF LOGGER:");
                System.out.println(Logger.getLog());
            }
        });
    }
    
    private void install() throws IOException{
        String text = FileReadWriteUtil.readStream(Application.class.getResourceAsStream("/setupInstructions.txt"));
        text = text.replace("$(PATH)", FileSystem.CREDENTIALS_FOLDER);
        new MessagePopup(text, ()->{
            try {
                launch();
            } catch (IOException ex) {
                exitWithError(ex);
            } catch (GeneralSecurityException ex) {
                exitWithError(ex);
            }
        });
    }
    
    public final void launch() throws IOException, GeneralSecurityException{
        prepareDriveService();
        createWindow(GoogleDriveService.getInstance());
    }
    public final void start(){
        Logger.log("Launching application...");
        try {
            prepareFileSystem();
            if(GoogleDriveService.credentialsExist()){
                launch();
            } else {
                try {
                    install();
                } catch (IOException ex) {
                    exitWithError(ex);
                }
            }
        } catch (IOException ex) {
            exitWithError(ex);
        } catch (GeneralSecurityException ex) {
            exitWithError(ex);
        }
    }
    
    private void exitWithError(Exception ex){
        new MessagePopup("Failed to launch application:\n" + ex.toString(), ()->{
            Logger.logError(ex);
            try{
                String path = FileSystem.getInstance().saveLog();
                new MessagePopup(String.format("I've saved a log of the error to %s", path));
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }
        });
    }
}
