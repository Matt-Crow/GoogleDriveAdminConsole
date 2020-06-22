package start;

import gui.MainWindow;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        new MainWindow();
        //Test.main(args);
    }
}
