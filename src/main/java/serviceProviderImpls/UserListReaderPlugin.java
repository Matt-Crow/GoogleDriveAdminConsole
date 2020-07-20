package serviceProviderImpls;

import drive.commands.utils.AbstractDriveCommand;
import gui.MainPane;
import gui.pages.AbstractFormPage;
import gui.pages.UserListReaderPage;
import pluginUtils.AbstractDriveCommandPlugin;

/**
 *
 * @author Matt
 */
public class UserListReaderPlugin implements AbstractDriveCommandPlugin{

    @Override
    public AbstractDriveCommand createCommand() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
