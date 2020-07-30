package gui;

import gui.components.HidableDecorator;
import gui.components.TextScroller;
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
    private final JMenuBar menu;
    private final JPanel bodyPane;
    
    private final OutputPage outputPage;
    private final HidableDecorator outputWrapper;
    
    private final JPanel cmdPane;
    private final HidableDecorator cmdWrapper;
    
    private final TextScroller helpText;
    private final HidableDecorator helpWrapper;
    
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
        outputWrapper.setSize(outputWrapper.getPreferredSize());
        
        // middle
        cmdPane = new JPanel();
        cmdPane.setLayout(new GridLayout(1, 1));
        cmdWrapper = new HidableDecorator("Command", cmdPane);
        
        // right
        helpText = new TextScroller();
        helpWrapper = new HidableDecorator("Help", helpText);
        helpWrapper.setSize(helpWrapper.getMinimumSize());
        
        // combine left and middle into one split pane
        JSplitPane split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, outputWrapper, cmdWrapper);
        
        //combine split1 and right into one split pane so we have 3 side-by-side panels
        JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, split1, helpWrapper);
        
        // add split2, while now contains left, middle, and right
        /*
        split2
        \_split1
          \_left
          \_middle
        \_right
        */
        bodyPane.add(split2, BorderLayout.CENTER);
        
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
        //cmdWrapper.repaint();
        helpText.setText(plugin.getHelp());
        repaint();
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
        repaint();
    }
    
    public final void setTabSwitchingEnabled(boolean allowSwitching){
        cmdWrapper.setEnabled(allowSwitching);
    }
    
    public final void addText(String appendMe){
        outputPage.addText(appendMe);
    }
}
