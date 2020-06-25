package gui.components;

import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import sysUtils.MessageListener;

/**
 *
 * @author Matt
 */
public class TextScroller extends JComponent implements MessageListener{
    private final JScrollPane scroll;
    private final JTextArea text;
    
    public TextScroller(){
        super();
        setLayout(new GridLayout(1, 1));
        text = new JTextArea();
        text.setWrapStyleWord(true);
        text.setLineWrap(true);
        text.setEditable(false);
        
        scroll = new JScrollPane(text);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll);
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
}
