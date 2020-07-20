package gui.components;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import pluginUtils.AbstractDriveCommandPlugin;

/**
 *
 * @author Matt
 */
public class PluginTab extends JPanel{
    private final JTabbedPane parent;
    private final AbstractDriveCommandPlugin plugin;
    
    public PluginTab(JTabbedPane inPane, AbstractDriveCommandPlugin forPlugin){
        super();
        parent = inPane;
        plugin = forPlugin;
    }
}
