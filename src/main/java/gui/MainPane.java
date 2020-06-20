package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Matt
 */
public class MainPane extends JPanel{
    private final OutputPage outputPage;
    private final ParseCertFormPage parseFormPage;
    private final JMenuBar menu;
    private final GuiBackend backend;
    
    private static final String OUTPUT = "output";
    
    public MainPane(){
        super();
        
        Runnable notImpl = () -> {
            throw new UnsupportedOperationException();
        };
        
        backend = new GuiBackend(this);
        
        setLayout(new BorderLayout());
        
        // construct the page content area
        JTabbedPane contentArea = new JTabbedPane();
        add(contentArea, BorderLayout.CENTER);
        
        outputPage = new OutputPage(this);
        contentArea.addTab(OUTPUT, outputPage);
        
        parseFormPage = new ParseCertFormPage(this);
        contentArea.addTab("Parse Cert Fomr", parseFormPage);
        
        // construct the menu bar
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
        
        // exit button
        JPanel end = new JPanel();
        end.setLayout(new FlowLayout());
        JButton exit = new JButton("EMERGENCY EXIT");
        exit.addActionListener((e)->{
            System.exit(0);
        });
        end.add(exit);
        add(end, BorderLayout.PAGE_END);
    }
    
    public final GuiBackend getBackend(){
        return backend;
    }
    
    private JMenuItem addMenuItem(JMenu addTo, String text, Runnable r){
        JMenuItem newItem = new JMenuItem(text);
        newItem.addActionListener((e)->r.run());
        addTo.add(newItem);
        return newItem;
    }
    
    public final void addText(String appendMe){
        outputPage.addText(appendMe);
    }
}
