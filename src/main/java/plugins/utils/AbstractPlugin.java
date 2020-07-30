package plugins.utils;

import gui.MainPane;
import gui.pages.AbstractFormPage;

/**
 * In the future, I will likely want some sort
 * of plugin system to enable the addition of
 * new drive commands. 
 * 
 * @author Matt Crow
 */
public interface AbstractPlugin {
    
    /**
     * 
     * @param pane
     * @return
     */
    public abstract AbstractFormPage getFormPage(MainPane pane);
    
    /**
     * 
     * @return the menu header to put 
     * this under in the main GUI
     */
    public abstract String getType();
    
    /**
     * 
     * @return the name to list this 
     * under the menu in the GUI
     */
    public abstract String getName();
    public abstract String getDescription();
    
    public default String getHelp(){
        return "Help text here";
    }
}
