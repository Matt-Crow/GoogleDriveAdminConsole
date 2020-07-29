package gui.components;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This component is meant to server as a decorator
 * or container for a single other component.
 * 
 * All it does is add a "hide/show" button above
 * the component is it decorating.
 * 
 * @author Matt Crow
 */
public class HidableDecorator extends JComponent {
    private final String text;
    private final JButton toggleHide;
    private final JComponent content;
    private boolean isHidden;
    
    public HidableDecorator(String header, JComponent wraping){
        super();
        setLayout(new BorderLayout());
        text = header;
        isHidden = false;
        
        toggleHide = new JButton("Hide " + text);
        toggleHide.addActionListener((e)->toggleHidden());
        add(toggleHide, BorderLayout.PAGE_START);
        
        content = wraping;
        add(content, BorderLayout.CENTER);
    }
    
    @Override
    public void setEnabled(boolean b){
        toggleHide.setEnabled(b);
        content.setEnabled(b);
    }
    
    public final void toggleHidden(){
        setHidden(!isHidden);
    }
    
    public final void setHidden(boolean b){
        isHidden = b;
        if(b){
            toggleHide.setText("Show " + text);
            content.setVisible(false);
        } else {
            toggleHide.setText("Hide " + text);
            content.setVisible(true);
        }
    }
    
    public static void main(String[] args){
        HidableDecorator h = new HidableDecorator("Label", new JLabel("is visible?"));
        
        JPanel body = new JPanel();
        body.setLayout(new GridLayout(1, 1));
        body.add(h);
        
        JFrame f = new JFrame();
        f.setContentPane(body);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        
        System.out.println(h.getLocation());
    }
}
