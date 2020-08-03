package start;

import fileUtils.FileReadWriteUtil;
import gui.MainWindow;
import gui.components.MessagePopup;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.swing.JOptionPane;
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
    
    public static final String APPLICATION_NAME = "Aerospace Camp Administration Console";
    
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
    private void prepareFileSystem(){
        try {
            FileSystem.getInstance().init();
            Logger.log("Application folders are good");
        } catch (IOException ex) {
            Logger.logError("Failed to create application folder");
            Logger.logError(ex);
            ex.printStackTrace();
            exitWithError(ex);
        }
    }
    
    private void prepareDriveService(){
        try {
            Logger.log("Validating Google Drive Service...");
            GoogleDriveService.validateService();
            Logger.log("Validation successful!");
        } catch (GeneralSecurityException ex) {
            Logger.log("Failed to validate service");
            Logger.logError(ex);
            //saveLog(); I'll probably want to make it save the logs
            exitWithError(ex);
        } catch (IOException ex) {
            Logger.log("Failed to validate service");
            Logger.logError(ex);
            //saveLog();
            exitWithError(ex);
        }
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
        MessagePopup.showMessage(null, text); // I don't like this: too easy to lose track of the popup
    }
    
    public final void start(){
        Logger.log("Launching application...");
        prepareFileSystem();
        /*
        TODO: If credentials don't exist, tell them to put them in such and such place with a given name,
        say "look at readme for instructions on how to create client secret for yourself"
        Include copy of credentials in drive so Randy and camp don't have to go through that,
        tell them as much when I share this
        */
        if(!GoogleDriveService.credentialsExist()){
            try {
                install();
            } catch (IOException ex) {
                exitWithError(ex);
            }
        }
        prepareDriveService();
        createWindow(GoogleDriveService.getInstance());
    }
    
    private void exitWithError(Exception ex){
        MessagePopup.showMessage(null, "Failed to launch application:\n" + ex.toString(), "Fatal Error", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}
