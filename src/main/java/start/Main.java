package start;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 *
 * @author Matt
 */
public class Main {
    public static final String APPLICATION_NAME = "Aerospace Camp Administration Console";
    
    public static void main(String[] args) throws IOException, GeneralSecurityException{
        new CmdLineInterface().run();
        //Test.main(args);
    }
}
