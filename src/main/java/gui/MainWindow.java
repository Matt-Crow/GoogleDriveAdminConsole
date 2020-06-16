package gui;

import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author Matt
 */
public class MainWindow extends JFrame{
    public MainWindow(){
        super();
        setTitle("Aerospace Museum STEM Camp Admin Console");
        // fullscreen
        setSize(
            (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 
            (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom
        );
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // EXIT_ON_CLOSE would cancel any commands in progress
        
        setContentPane(new MainPane());
        
        setVisible(true);
        revalidate();
        repaint();
    }
}
