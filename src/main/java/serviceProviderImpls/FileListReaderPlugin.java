package serviceProviderImpls;

import drive.commands.utils.AbstractDriveCommand;
import gui.MainPane;
import gui.pages.AbstractFormPage;
import gui.pages.FileListReaderPage;
import pluginUtils.AbstractDriveCommandPlugin;

/**
 *
 * @author Matt
 */
public class FileListReaderPlugin implements AbstractDriveCommandPlugin{

    @Override
    public AbstractDriveCommand createCommand() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
