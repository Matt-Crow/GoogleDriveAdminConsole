package gui.components;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author Matt
 */
public class MessagePopup {
    
    public static final void showMessage(Component from, String msg, String title, int msgType){
        // https://stackoverflow.com/questions/16409387/joptionpane-output-text-copy
        JOptionPane.showMessageDialog(from, new TextScroller(msg), title, msgType);
    }
    public static final void showMessage(Component from, String msg){
        showMessage(from, msg, "message", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    public static void main(String[] args){
        showMessage(null, "Hello?", "title here", JOptionPane.INFORMATION_MESSAGE);
    }
}
