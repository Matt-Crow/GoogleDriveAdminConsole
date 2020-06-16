package gui;

import java.awt.BorderLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author Matt
 */
public class MainPane extends JPanel{
    private final JScrollPane scroll;
    private final JTextArea text;
    private final JMenuBar menu;
    private final GuiBackend backend;
    
    public MainPane(){
        super();
        backend = new GuiBackend(this);
        
        setLayout(new BorderLayout());
        
        text = new JTextArea();
        text.setWrapStyleWord(true);
        text.setLineWrap(true);
        text.setEditable(false);
        
        scroll = new JScrollPane(text);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);
        
        menu = new JMenuBar();
        JMenu servAcc = new JMenu("Server access");
        
        JMenuItem addToAcc = new JMenuItem("Add a Minecraft username to an access list");
        addToAcc.addActionListener((e)->{
            backend.askAddToAccessList();
        });
        servAcc.add(addToAcc);
        
        JMenuItem createAcc = new JMenuItem("Create a new access list");
        createAcc.addActionListener((e)->{
            backend.askCreateAccessList();
        });
        servAcc.add(createAcc);
        
        JMenuItem getAcc = new JMenuItem("Display an access list");
        getAcc.addActionListener((e)->{
            backend.askDisplayAccessList();
        });
        servAcc.add(getAcc);
        
        JMenuItem delAcc = new JMenuItem("Clear an access list");
        delAcc.addActionListener((e)->{
            backend.askClearAccessList();
        });
        servAcc.add(delAcc);
        
        menu.add(servAcc);
        add(menu, BorderLayout.PAGE_START);
        
        
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
}
