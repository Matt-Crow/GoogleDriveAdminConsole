package start;

import gui.MainWindow;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 *
 * @author Matt
 */
public class Main {
    public static final String APPLICATION_NAME = "Aerospace Camp Administration Console";
    
    public static void main(String[] args) throws IOException, GeneralSecurityException{
        //new CmdLineInterface().run();
        //new MainWindow();
        Test.main(args);
    }
}
