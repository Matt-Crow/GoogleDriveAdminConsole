package plugins.implementations.userListReader;

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
public class UserListReaderPlugin implements AbstractPlugin{
    private String cachedHelpText = null;
    
    @Override
    public AbstractFormPage getFormPage(MainPane pane) {
        return new UserListReaderPage(pane);
    }

    @Override
    public String getType() {
        return "read";
    }

    @Override
    public String getName() {
        return "read user list";
    }

    @Override
    public String getDescription() {
        return "Takes a property file, and gets all the users listed in the Google Sheet the properties reference";
    }

    @Override
    public String getHelp(){
        String ret = "couldn't get help for " + getName();
        if(cachedHelpText == null){
            try {
                cachedHelpText = FileReadWriteUtil.readStream(UserListReaderPlugin.class.getResourceAsStream("/help/userListReaderHelp.txt"));
                ret = cachedHelpText;
            } catch (IOException ex) {
                Logger.logError(ex);
            }
        } else {
            ret = cachedHelpText;
        }
        return ret;
    }
}
