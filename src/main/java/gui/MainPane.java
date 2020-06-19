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
        
        Runnable notImpl = () -> {
            throw new UnsupportedOperationException();
        };
        
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
        addMenuItem(servAcc, "Add a Minecraft username to an access list", ()->backend.askAddToAccessList());
        addMenuItem(servAcc, "Create a new access list", ()->backend.askCreateAccessList());
        addMenuItem(servAcc, "Display an access list", ()->backend.askDisplayAccessList());
        addMenuItem(servAcc, "Clear an access list", ()->backend.askClearAccessList());
        menu.add(servAcc);
        
        JMenu driveManage = new JMenu("Drive management");
        addMenuItem(driveManage, "Update download permissions for files", ()->backend.askDownloadPermissions());
        addMenuItem(driveManage, "Give an email access to files", notImpl);
        addMenuItem(driveManage, "Read a file list", ()->backend.askReadFileList());
        addMenuItem(driveManage, "Read a certification form", ()->backend.askReadCertForm());
        menu.add(driveManage);
        
        JMenu props = new JMenu("Create properties");
        addMenuItem(props, "Create default file list properties", ()->backend.askCreateDefaultFileListProps());
        addMenuItem(props, "Create default certification form properties", ()->backend.askCreateDefaultCertFormProps());
        menu.add(props);
        
        JMenu newCamp = new JMenu("New Camp");
        addMenuItem(newCamp, "Parse a certification form", notImpl);
        menu.add(newCamp);
        
        add(menu, BorderLayout.PAGE_START);
    }
    
    private JMenuItem addMenuItem(JMenu addTo, String text, Runnable r){
        JMenuItem newItem = new JMenuItem(text);
        newItem.addActionListener((e)->r.run());
        addTo.add(newItem);
        return newItem;
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
