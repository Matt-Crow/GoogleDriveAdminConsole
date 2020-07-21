package plugins;

import gui.MainPane;
import gui.pages.AbstractFormPage;
import gui.pluginPages.GiveAccessPage;
import pluginUtils.AbstractPlugin;

/**
 *
 * @author Matt
 */
public class GiveAccessPlugin implements AbstractPlugin {

    @Override
    public AbstractFormPage getFormPage(MainPane pane) {
        return new GiveAccessPage(pane);
    }

    @Override
    public String getType() {
        return "share";
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
