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
}
