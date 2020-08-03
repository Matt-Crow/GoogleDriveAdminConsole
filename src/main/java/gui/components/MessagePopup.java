package gui.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Matt
 */
public class MessagePopup extends JFrame {
    
    public MessagePopup(String msg, Runnable onClose){
        super();
        JPanel body = new JPanel();
        body.setLayout(new BorderLayout());
        
        body.add(new TextScroller(msg), BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        JButton close = new JButton("Close");
        close.addActionListener((e)->{
            SwingUtilities.invokeLater(onClose);
            dispose(); // does this fire window closing?
        });
        bottom.add(close);
        body.add(bottom, BorderLayout.PAGE_END);
        
        setContentPane(body);
        
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                SwingUtilities.invokeLater(onClose);
            }
        });
        pack();
        setVisible(true);
        revalidate();
        repaint();
        SwingUtilities.invokeLater(()->{
            requestFocus();
        });
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public MessagePopup(String msg){
        this(msg, ()->{});
    }
    
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
