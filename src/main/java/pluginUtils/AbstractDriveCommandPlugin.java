package pluginUtils;

import drive.commands.utils.AbstractDriveCommand;
import gui.pages.AbstractFormPage;

/**
 * In the future, I will likely want some sort
 * of plugin system to enable the addition of
 * new drive commands. 
 * 
 * @author Matt Crow
 */
public interface AbstractDriveCommandPlugin {
    /**
     * Will probably need to accept a hashmap or something
     * to pass parameters
     * @return 
     */
    public abstract AbstractDriveCommand createCommand();
    
    /**
     * 
     * @return 
     */
    public abstract AbstractFormPage getFormPage();
}
