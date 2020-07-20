package serviceProviderImpls;

import drive.commands.utils.AbstractDriveCommand;
import gui.MainPane;
import gui.pages.AbstractFormPage;
import gui.pages.GiveAccessPage;
import pluginUtils.AbstractDriveCommandPlugin;

/**
 *
 * @author Matt
 */
public class GiveAccessPlugin implements AbstractDriveCommandPlugin {

    @Override
    public AbstractDriveCommand createCommand() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbstractFormPage getFormPage(MainPane pane) {
        return new GiveAccessPage(pane);
    }

    @Override
    public String getType() {
        return "drive access";
    }

    @Override
    public String getName() {
        return "give access";
    }

    @Override
    public String getDescription() {
        return "Give access is used to read user- and file- spreadsheets, then give each user listed access to each file listed";
    }

}
