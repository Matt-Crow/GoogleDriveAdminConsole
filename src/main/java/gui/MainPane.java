package gui;

import gui.components.HidableDecorator;
import gui.pages.AbstractFormPage;
import gui.pages.OutputPage;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import plugins.utils.PluginLoader;
import start.GoogleDriveService;
import sysUtils.Logger;
import plugins.utils.AbstractPlugin;

/**
 *
 * @author Matt
 */
public class MainPane extends JPanel{
    private final OutputPage outputPage;
    private final JMenuBar menu;
    //private final JTabbedPane contentArea;
    private final JPanel body;
    private final JPanel center;
    private final HidableDecorator hideOutput;
    private final HidableDecorator centerHide;
    
    public MainPane(GoogleDriveService service){
        super();
        
        setLayout(new BorderLayout());
        
        // construct the page content area
        
        //contentArea = new JTabbedPane();
        //add(contentArea, BorderLayout.CENTER);
        body = new JPanel();
        body.setLayout(new BorderLayout());
        
        center = new JPanel();
        center.setLayout(new GridLayout(1, 1));
        centerHide = new HidableDecorator("Command", center);
        body.add(centerHide, BorderLayout.CENTER);
        
        add(body, BorderLayout.CENTER);
        
        
        
        outputPage = new OutputPage(this);
        hideOutput = new HidableDecorator("Output", outputPage);
        body.add(hideOutput, BorderLayout.LINE_START);
        //contentArea.addTab("output", outputPage);
        
        
        // construct the menu bar
        menu = new JMenuBar();
        loadServices();
        add(menu, BorderLayout.PAGE_START);
    }
    
    /**
     * Takes each AbstractPlugin from the PluginLoader,
     * and adds menus for each of them.
     * TODO: make this add pages for each plugin
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
    
    // I'll want to improve this. Probably want a plugin tabbed pane class
    // do we care if we have multiple tabs open for a single plugin?
    private void openTab(AbstractPlugin plugin){
        AbstractFormPage page = plugin.getFormPage(this);
        body.add(page, BorderLayout.CENTER);
        
        /*
        // the component it shows when the tab is clicked
        contentArea.add(plugin.getName(), page);

        // create the content of the tab
        JPanel tab = new JPanel();
        tab.setLayout(new BorderLayout());
        tab.add(new JLabel(plugin.getName()), BorderLayout.CENTER);
        JButton close = new JButton("X"); // may want this smaller
        close.addActionListener((e)->{
            
            Note: this idx is different from the one the
            page was originally inserted into, so we mustn't
            get this index from outside the action listener.

            for example:
            given tabs [a] [b]
            user opens [c]: tabs are now [a] [b] [c] (c inserted at 2)
            user closes [b]: tabs are now [a] [c]
            user closes [c], which is at index 1, instead of the index 2 it was inserted in
            
            int idx = contentArea.indexOfComponent(page);
            if(idx == -1){
                Logger.logError("Cannot close tab " + plugin.getName());
            } else {
                contentArea.remove(idx);
            }
        });
        tab.add(close, BorderLayout.LINE_END);

        // the actual tab that shows up in the tab list
        contentArea.setTabComponentAt(contentArea.getTabCount() - 1, tab);

        contentArea.setSelectedComponent(page);*/
    }
    
    private JMenuItem addMenuItem(JMenu addTo, String text, Runnable r){
        JMenuItem newItem = new JMenuItem(text);
        newItem.addActionListener((e)->r.run());
        addTo.add(newItem);
        return newItem;
    }
    
    public final void switchToOutputTab(){
        hideOutput.setHidden(false);
        centerHide.setHidden(true);
        //contentArea.setSelectedComponent(outputPage);
    }
    
    public final void setTabSwitchingEnabled(boolean allowSwitching){
        centerHide.setEnabled(allowSwitching);
        //contentArea.setEnabled(allowSwitching);
    }
    
    public final void addText(String appendMe){
        outputPage.addText(appendMe);
    }
}
