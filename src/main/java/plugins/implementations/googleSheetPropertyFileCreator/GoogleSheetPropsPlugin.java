package plugins.implementations.googleSheetPropertyFileCreator;

import fileUtils.FileReadWriteUtil;
import gui.MainPane;
import gui.pages.AbstractFormPage;
import java.io.IOException;
import plugins.utils.AbstractPlugin;
import sysUtils.Logger;

/**
 *
 * @author Matt
 */
public class GoogleSheetPropsPlugin implements AbstractPlugin {
    private String cachedHelpText = null;
    
    @Override
    public AbstractFormPage getFormPage(MainPane pane) {
        return new GoogleSheetPropsPage(pane);
    }

    @Override
    public String getType() {
        return "create";
    }

    @Override
    public String getName() {
        return "create sheet properties";
    }

    @Override
    public String getDescription() {
        return "creates a new set of Google Sheet properties, which allow the application to locate Google Sheets in your drive";
    }

    @Override
    public String getHelp(){
        String ret = "couldn't get help for " + getName();
        if(cachedHelpText == null){
            try {
                cachedHelpText = FileReadWriteUtil.readStream(GoogleSheetPropsPlugin.class.getResourceAsStream("/help/googleSheetPropertyFileCreatorHelp.txt"));
                ret = cachedHelpText;
            } catch (IOException ex) {
                Logger.logError(ex);
            }
        }
        return ret;
    }
}
