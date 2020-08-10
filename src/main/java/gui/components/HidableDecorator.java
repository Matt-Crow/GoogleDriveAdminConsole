package gui.components;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private final ArrayList<ToggleHideListener> hideListeners;
    
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
        
        hideListeners = new ArrayList<>();
    }
    
    @Override
    public void setEnabled(boolean b){
        toggleHide.setEnabled(b);
        content.setEnabled(b);
    }
    
    @Override
    public void repaint(){
        content.repaint();
        super.repaint();
    }
    
    /**
     * Adds a hide listener, which will be notified when
     * this' hide state is changed.
     * 
     * @param thl the hide listener to add
     */
    public void addHideListener(ToggleHideListener thl){
        hideListeners.add(thl);
    }
    
    /**
     * Removes a hide listener from this, if it is attached.
     * @param thl the hide listener to remove
     * @return true iff the hide listener was attached before
     * calling this method.
     */
    public boolean removeHideListener(ToggleHideListener thl){
        return hideListeners.remove(thl);
    }
    
    public final void toggleHidden(){
        setHidden(!isHidden);
    }
    
    /**
     * Not to be confused with setVisible(boolean).
     * 
     * Sets whether or not this should render
     * components besides the toggle hide button.
     * Fires all attached ToggleHideListeners.
     * 
     * @param b if true, this will render its
     * non-hide button contents invisible. Else,
     * makes them visible.
     */
    public final void setHidden(boolean b){
        isHidden = b;
        if(b){
            toggleHide.setText("Show " + text);
            content.setVisible(false);
        } else {
            toggleHide.setText("Hide " + text);
            content.setVisible(true);
        }
        repaint();
        hideListeners.forEach((hl)->hl.hideStateToggled(isHidden));
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
