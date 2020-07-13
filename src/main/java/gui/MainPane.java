package gui;

import gui.pages.IndividualAccessPage;
import gui.pages.PageContent;
import gui.pages.OutputPage;
import gui.pages.ParseCertFormPage;
import gui.pages.PageName;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import pluginUtils.AbstractDriveCommandPlugin;
import pluginUtils.DriveCommandService;
import start.GoogleDriveService;

/**
 *
 * @author Matt
 */
public class MainPane extends JPanel{
    private final OutputPage outputPage;
    private final ParseCertFormPage parseFormPage;
    private final IndividualAccessPage indivAccPage;
    private final JMenuBar menu;
    private final GuiBackend backend;
    private final JTabbedPane contentArea;
    
    private final HashMap<PageName, PageContent> pages;
    
    public MainPane(GoogleDriveService service){
        super();
        
        backend = new GuiBackend(this, service);
        
        setLayout(new BorderLayout());
        
        // construct the page content area
        pages = new HashMap<>();
        
        contentArea = new JTabbedPane();
        add(contentArea, BorderLayout.CENTER);
        
        outputPage = new OutputPage(this);
        contentArea.addTab(PageName.OUTPUT.getDisplayValue(), outputPage);
        pages.put(PageName.OUTPUT, outputPage);
        
        parseFormPage = new ParseCertFormPage(this);
        contentArea.addTab(PageName.PARSE_FORM.getDisplayValue(), parseFormPage);
        pages.put(PageName.PARSE_FORM, parseFormPage);
        
        indivAccPage = new IndividualAccessPage(this);
        contentArea.addTab(PageName.INDIV_ACC.getDisplayValue(), indivAccPage);
        pages.put(PageName.INDIV_ACC, indivAccPage);
        
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
        addMenuItem(driveManage, "Read a file list", ()->backend.askReadFileList());
        addMenuItem(driveManage, "Read a user list", ()->backend.askReadCertForm());
        menu.add(driveManage);
        
        JMenu props = new JMenu("Create properties");
        addMenuItem(props, "Create default file list properties", ()->backend.askCreateDefaultFileListProps());
        addMenuItem(props, "Create default user list properties", ()->backend.askCreateDefaultCertFormProps());
        menu.add(props);
        
        
        
        
        
        
        /*
        NEW STUFF
        */
        loadServices();
        
        
        
        
        
        
        
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
    
    private void loadServices(){
        HashMap<String, JMenu> menus = new HashMap<>();
        String type;
        for(AbstractDriveCommandPlugin plugin : DriveCommandService.getInstance().getAllPlugins()){
            type = plugin.getType().toLowerCase();
            if(!menus.containsKey(type)){
                menus.put(type, new JMenu(type));
            }
            addMenuItem(menus.get(type), plugin.getName(), ()->System.out.println(plugin.getDescription()));
        }
        menus.values().forEach((subMenu)->menu.add(subMenu));
    }
    
    private JMenuItem addMenuItem(JMenu addTo, String text, Runnable r){
        JMenuItem newItem = new JMenuItem(text);
        newItem.addActionListener((e)->r.run());
        addTo.add(newItem);
        return newItem;
    }
    
    public final void switchToTab(PageName tabName){
        contentArea.setSelectedComponent(pages.get(tabName));
    }
    
    public final void setTabSwitchingEnabled(boolean allowSwitching){
        contentArea.setEnabled(allowSwitching);
    }
    
    public final void addText(String appendMe){
        outputPage.addText(appendMe);
    }
}
