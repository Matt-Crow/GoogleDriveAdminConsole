package plugins.implementations.fileListReader;

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
public class FileListReaderPlugin implements AbstractPlugin{
    private String cachedHelpText = null;
    
    @Override
    public AbstractFormPage getFormPage(MainPane pane) {
        return new FileListReaderPage(pane);
    }

    @Override
    public String getType() {
        return "read";
    }

    @Override
    public String getName() {
        return "read file list";
    }

    @Override
    public String getDescription() {
        return "Reads a Google Sheets property file, then gets all the files listed in that Google Sheet";
    }
    
    @Override
    public String getHelp(){
        String ret = "couldn't get help for " + getName();
        if(cachedHelpText == null){
            try {
                cachedHelpText = FileReadWriteUtil.readStream(FileListReaderPlugin.class.getResourceAsStream("/help/fileListReaderHelp.txt"));
                ret = cachedHelpText;
            } catch (IOException ex) {
                Logger.logError(ex);
            }
        }
        return ret;
    }
}
