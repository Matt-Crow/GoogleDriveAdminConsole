package gui.pluginPages;

import gui.pluginPages.AbstractReaderPage;
import fileUtils.FileList;
import gui.MainPane;
import structs.GoogleSheetProperties;

/**
 *
 * @author Matt
 */
public class FileListReaderPage extends AbstractReaderPage {
    public FileListReaderPage(MainPane inPane) {
        super(inPane, "File List Properies", "select the file sheet properties");
    }
    
    @Override
    public void parse(GoogleSheetProperties props) throws Exception{
        MainPane parent = getPaneParent();
        FileList files = parent.getBackend().getCmdFactory().readFileList(
            props
        ).doExecute();
        parent.addText("contains the following files:");
        files.forEach((f)->parent.addText(f.toString()));
    }
}
