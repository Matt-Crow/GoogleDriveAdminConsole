package gui;

import java.awt.Toolkit;
import javax.swing.JFrame;
import start.GoogleDriveService;
import start.Main;

/**
 *
 * @author Matt
 */
public class MainWindow extends JFrame{
    public MainWindow(GoogleDriveService service){
        super();
        setTitle(Main.APPLICATION_NAME);
        // fullscreen
        setSize(
            (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 
            (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom
        );
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // EXIT_ON_CLOSE would cancel any commands in progress
        
        setContentPane(new MainPane(service));
        
        setVisible(true);
        revalidate();
        repaint();
    }
}
