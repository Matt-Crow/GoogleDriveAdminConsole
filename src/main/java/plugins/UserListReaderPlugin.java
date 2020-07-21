package plugins;

import gui.MainPane;
import gui.pages.AbstractFormPage;
import gui.pluginPages.UserListReaderPage;
import pluginUtils.AbstractPlugin;

/**
 *
 * @author Matt
 */
public class UserListReaderPlugin implements AbstractPlugin{
    
    @Override
    public AbstractFormPage getFormPage(MainPane pane) {
        return new UserListReaderPage(pane);
    }

    @Override
    public String getType() {
        return "drive access";
    }

    @Override
    public String getName() {
        return "read user list";
    }

    @Override
    public String getDescription() {
        return "Takes a property file, and gets all the users listed in the Google Sheet the properties reference";
    }

}
