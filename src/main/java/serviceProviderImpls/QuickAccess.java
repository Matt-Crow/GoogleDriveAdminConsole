package serviceProviderImpls;

import drive.commands.utils.AbstractDriveCommand;
import gui.pages.AbstractFormPage;
import pluginUtils.AbstractDriveCommandPlugin;

/**
 *
 * @author Matt
 */
public class QuickAccess implements AbstractDriveCommandPlugin{

    @Override
    public AbstractDriveCommand createCommand() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbstractFormPage getFormPage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDescription() {
        return "Quick access is used to give access to files to multiple users without having to parse forms";
    }

}
