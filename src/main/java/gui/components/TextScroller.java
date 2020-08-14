package gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import sysUtils.ErrorListener;
import sysUtils.MessageListener;

/**
 *
 * @author Matt
 */
public class TextScroller extends JComponent implements MessageListener, ErrorListener {
    private final JScrollPane scroll;
    private final JTextArea text;
    
    public TextScroller(){
        super();
        setLayout(new BorderLayout());
        JPanel head = new JPanel();
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener((e)->{
            clear();
        });
        head.add(clearButton);
        add(head, BorderLayout.PAGE_START);
        
        
        //setLayout(new GridLayout(1, 1));
        text = new JTextArea(20, 20);
        text.setWrapStyleWord(true);
        text.setLineWrap(true);
        text.setEditable(false);
        
        scroll = new JScrollPane(text);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);
    }
    
    public TextScroller(String defaultText){
        this();
        setText(defaultText);
    }
    
    @Override
    public final Dimension getPreferredSize(){
        return scroll.getPreferredSize();
    }
    
    @Override
    public final Dimension getMinimumSize(){
        return scroll.getMinimumSize();
    }
    
    public final void setText(String newText){
        text.setText(newText);
    }
    
    public final void addText(String appendMe){
        text.append(appendMe);
        text.append("\n");
        SwingUtilities.invokeLater(()->{
            JScrollBar bar = scroll.getVerticalScrollBar();
            bar.setValue(bar.getMaximum());
            revalidate();
            repaint();
        });
    }
    
    public final void clear(){
        text.setText("");
        SwingUtilities.invokeLater(()->{
            JScrollBar bar = scroll.getVerticalScrollBar();
            bar.setValue(bar.getMinimum());
            revalidate();
            repaint();
        });
    }

    @Override
    public void messageLogged(String message) {
        addText(message);
    }

    @Override
    public void errorLogged(String errMsg) {
        addText("Uh oh: " + errMsg);
    }

    @Override
    public void errorLogged(Exception ex) {
        errorLogged(ex.getMessage());
    }

    @Override
    public void errorLogCleared() {}
}
