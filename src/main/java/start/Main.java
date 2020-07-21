package start;

import gui.MainWindow;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import sysUtils.FileSystem;
//import pluginUtils.DriveCommandService;
import sysUtils.Logger;

/**
 *
 * @author Matt
 */
public class Main {
    public static final String APPLICATION_NAME = "Aerospace Camp Administration Console";
    
    public static void main(String[] args){
        
        // should probably alert the user of fatal errors.
        Logger.log("Starting application...");
        try{
            FileSystem.getInstance().init();
            Logger.log("Application folders are good");
            GoogleDriveService service = null;
            try{
                service = GoogleDriveService.getInstance();
                createWindow(service);
            } catch(GeneralSecurityException gse){
                Logger.logError(gse);
            } catch(IOException ioe){
                Logger.logError(ioe);
            }
        } catch (IOException ex) {
            Logger.logError("Failed to create application folders");
            Logger.logError(ex);
        }   
    }
   
    
    private static void createWindow(GoogleDriveService service){
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
}
