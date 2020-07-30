package plugins.implementations.shareFiles;

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
public class ShareFilesPlugin implements AbstractPlugin {
    private String cachedHelpText = null;
    
    @Override
    public AbstractFormPage getFormPage(MainPane pane) {
        return new ShareFilesPage(pane);
    }

    @Override
    public String getType() {
        return "share";
    }

    @Override
    public String getName() {
        return "share files";
    }

    @Override
    public String getDescription() {
        return "Share files is used to read user- and file- spreadsheets, then give each user listed access to each file listed, if they are in the correct group";
    }

    @Override
    public String getHelp(){
        String ret = "couldn't get help for " + getName();
        if(cachedHelpText == null){
            try {
                cachedHelpText = FileReadWriteUtil.readStream(ShareFilesPlugin.class.getResourceAsStream("/help/shareFilesHelp.txt"));
                ret = cachedHelpText;
            } catch (IOException ex) {
                Logger.logError(ex);
            }
        }
        return ret;
    }
}
