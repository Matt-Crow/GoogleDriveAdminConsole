package plugins;

import gui.MainPane;
import gui.pages.AbstractFormPage;
import gui.pages.FileListReaderPage;
import pluginUtils.AbstractPlugin;

/**
 *
 * @author Matt
 */
public class FileListReaderPlugin implements AbstractPlugin{

    @Override
    public AbstractFormPage getFormPage(MainPane pane) {
        return new FileListReaderPage(pane);
    }

    @Override
    public String getType() {
        return "drive access";
    }

    @Override
    public String getName() {
        return "read file list";
    }

    @Override
    public String getDescription() {
        return "Reads a Google Sheets property file, then gets all the files listed in that Google Sheet";
    }
}
