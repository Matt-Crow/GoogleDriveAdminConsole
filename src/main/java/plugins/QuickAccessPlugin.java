package plugins;

import gui.MainPane;
import gui.pages.AbstractFormPage;
import gui.pluginPages.QuickAccessPage;
import pluginUtils.AbstractPlugin;

/**
 *
 * @author Matt
 */
public class QuickAccessPlugin implements AbstractPlugin{

    @Override
    public AbstractFormPage getFormPage(MainPane pane) {
        return new QuickAccessPage(pane);
    }

    @Override
    public String getDescription() {
        return "Quick access is used to give access to files to multiple users without having to parse forms";
    }

    @Override
    public String getType() {
        return "share";
    }

    @Override
    public String getName() {
        return "quick access";
    }

}
