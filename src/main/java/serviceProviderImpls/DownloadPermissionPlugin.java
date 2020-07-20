package serviceProviderImpls;

import drive.commands.utils.AbstractDriveCommand;
import gui.MainPane;
import gui.pages.AbstractFormPage;
import gui.pages.DownloadPermissionPage;
import pluginUtils.AbstractDriveCommandPlugin;

/**
 *
 * @author Matt
 */
public class DownloadPermissionPlugin implements AbstractDriveCommandPlugin {

    @Override
    public AbstractDriveCommand createCommand() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbstractFormPage getFormPage(MainPane pane) {
        return new DownloadPermissionPage(pane);
    }

    @Override
    public String getType() {
        return "drive access";
    }

    @Override
    public String getName() {
        return "update download permissions";
    }

    @Override
    public String getDescription() {
        return "update download permissions for files in a file list";
    }

}
