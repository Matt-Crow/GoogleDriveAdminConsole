package start;

import gui.MainWindow;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import pluginUtils.DriveCommandService;
import sysUtils.Logger;

/**
 *
 * @author Matt
 */
public class Main {
    public static final String APPLICATION_NAME = "Aerospace Camp Administration Console";
    
    
    public static void main(String[] args) throws IOException, GeneralSecurityException{
        DriveCommandService.main(args);
        
        GoogleDriveService service = null;
        try{
            service = GoogleDriveService.getInstance();
            createWindow(service);
        } catch(GeneralSecurityException gse){
            gse.printStackTrace();
        } catch(IOException ioe){
            ioe.printStackTrace();
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
