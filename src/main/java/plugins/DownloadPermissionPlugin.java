package plugins;

import gui.MainPane;
import gui.pages.AbstractFormPage;
import gui.pages.DownloadPermissionPage;
import pluginUtils.AbstractPlugin;

/**
 *
 * @author Matt
 */
public class DownloadPermissionPlugin implements AbstractPlugin {

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
