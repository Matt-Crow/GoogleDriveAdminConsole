package start;

import gui.MainWindow;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import sysUtils.Logger;

/**
 *
 * @author Matt
 */
public class Main {
    public static final String APPLICATION_NAME = "Aerospace Camp Administration Console";
    
    public static void main(String[] args) throws IOException, GeneralSecurityException{
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
        MainWindow window = new MainWindow();
        window.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                System.out.println(Logger.getLog());
            }
        });
    }
}
