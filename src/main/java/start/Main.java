package start;

import gui.MainWindow;
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
 * Main is the starting point of the application.
 * 
 * @author Matt Crow
 */
public class Main {
    public static final String APPLICATION_NAME = "Aerospace Camp Administration Console";
    
    public static void main(String[] args){
        Logger.log("Starting application...");
        try{
            FileSystem.getInstance().init();
            Logger.log("Application folders are good");
        } catch (IOException ex) {
            Logger.logError("Failed to create application folders");
            Logger.logError(ex);
            exitWithError(ex);
        }
        
        try {
            Logger.log("Validating Google Drive Service...");
            GoogleDriveService.validateService();
            Logger.log("Validation successful!");
        } catch (GeneralSecurityException ex) {
            Logger.log("Failed to validate service");
            Logger.logError(ex);
            saveLog();
            exitWithError(ex);
        } catch (IOException ex) {
            Logger.log("Failed to validate service");
            Logger.logError(ex);
            saveLog();
            exitWithError(ex);
        }
        
        createWindow(GoogleDriveService.getInstance());
    }
   
    private static void exitWithError(Exception ex){
        JOptionPane.showMessageDialog(null, "Failed to launch application:\n" + ex.toString(), "Fatal Error", JOptionPane.ERROR_MESSAGE);
        System.exit(ex.hashCode());
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
                saveLog();
            }
        });
    }
    
    private static void saveLog(){
        System.out.println("Saving log temporarily disabled in Main");
        System.out.println(Logger.getLog());
        /*
        try {
            FileSystem.getInstance().saveLog();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Failed to save log", JOptionPane.ERROR_MESSAGE);
        }*/
    }
}
