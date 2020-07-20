package serviceProviderImpls;

import drive.commands.utils.AbstractDriveCommand;
import gui.MainPane;
import gui.pages.AbstractFormPage;
import gui.pages.IndividualAccessPage;
import pluginUtils.AbstractDriveCommandPlugin;

/**
 *
 * @author Matt
 */
public class QuickAccessPlugin implements AbstractDriveCommandPlugin{

    @Override
    public AbstractDriveCommand createCommand() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbstractFormPage getFormPage(MainPane pane) {
        return new IndividualAccessPage(pane);
    }

    @Override
    public String getDescription() {
        return "Quick access is used to give access to files to multiple users without having to parse forms";
    }

    @Override
    public String getType() {
        return "drive access";
    }

    @Override
    public String getName() {
        return "quick access";
    }

}
