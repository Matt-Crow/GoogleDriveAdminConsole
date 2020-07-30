package plugins.implementations.downloadPermission;

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
public class DownloadPermissionPlugin implements AbstractPlugin {
    private String cachedHelpText = null;
    
    @Override
    public AbstractFormPage getFormPage(MainPane pane) {
        return new DownloadPermissionPage(pane);
    }

    @Override
    public String getType() {
        return "share";
    }

    @Override
    public String getName() {
        return "update download permissions";
    }

    @Override
    public String getDescription() {
        return "update download permissions for files in a file list";
    }
    
    @Override
    public String getHelp(){
        String ret = "couldn't get help for " + getName();
        if(cachedHelpText == null){
            try {
                cachedHelpText = FileReadWriteUtil.readStream(DownloadPermissionPlugin.class.getResourceAsStream("/help/downloadPermissionHelp.txt"));
                ret = cachedHelpText;
            } catch (IOException ex) {
                Logger.logError(ex);
            }
        }
        return ret;
    }
}
