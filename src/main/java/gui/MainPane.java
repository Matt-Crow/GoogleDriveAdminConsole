package gui;

import gui.components.HidableDecorator;
import gui.pages.AbstractFormPage;
import gui.pages.OutputPage;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import plugins.utils.PluginLoader;
import start.GoogleDriveService;
import plugins.utils.AbstractPlugin;

/**
 * 
 * @author Matt
 */
public class MainPane extends JPanel{
    private final OutputPage outputPage;
    private final JMenuBar menu;
    private final JPanel bodyPane;
    private final JPanel cmdPane;
    private final HidableDecorator outputWrapper;
    private final HidableDecorator cmdWrapper;
    
    public MainPane(GoogleDriveService service){
        super();
        
        setLayout(new BorderLayout());
        
        // construct the menu bar
        menu = new JMenuBar();
        loadServices();
        add(menu, BorderLayout.PAGE_START);
        
        // construct the page content area
        bodyPane = new JPanel(); // do I need this?
        bodyPane.setLayout(new GridLayout(1, 1));
        
        // left side
        outputPage = new OutputPage(this);
        outputWrapper = new HidableDecorator("Output", outputPage);
        
        // right side
        cmdPane = new JPanel();
        cmdPane.setLayout(new GridLayout(1, 1));
        cmdWrapper = new HidableDecorator("Command", cmdPane);
        
        // combine left and right into one split pane
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, outputWrapper, cmdWrapper);
        bodyPane.add(split, BorderLayout.CENTER);
        
        add(bodyPane, BorderLayout.CENTER);
    }
    
    /**
     * Takes each AbstractPlugin from the PluginLoader,
     * and adds menus for each of them.
     */
    private void loadServices(){
        HashMap<String, JMenu> menus = new HashMap<>();
        String type;
        for(AbstractPlugin plugin : PluginLoader.getInstance().getAllPlugins()){
            type = plugin.getType().toLowerCase();
            if(!menus.containsKey(type)){
                menus.put(type, new JMenu(type));
            }
            
            addMenuItem(menus.get(type), plugin.getName(), ()->openTab(plugin));
        }
        menus.values().forEach((subMenu)->menu.add(subMenu));
    }
    
    private void openTab(AbstractPlugin plugin){
        AbstractFormPage page = plugin.getFormPage(this);
        cmdPane.removeAll();
        cmdPane.add(page);
        cmdWrapper.repaint();
    }
    
    private JMenuItem addMenuItem(JMenu addTo, String text, Runnable r){
        JMenuItem newItem = new JMenuItem(text);
        newItem.addActionListener((e)->r.run());
        addTo.add(newItem);
        return newItem;
    }
    
    public final void switchToOutputTab(){
        outputWrapper.setHidden(false);
        cmdWrapper.setHidden(true);
    }
    
    public final void setTabSwitchingEnabled(boolean allowSwitching){
        cmdWrapper.setEnabled(allowSwitching);
    }
    
    public final void addText(String appendMe){
        outputPage.addText(appendMe);
    }
}
