package plugins.implementations.fileListReader;

import plugins.implementations.AbstractReaderPage;
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
        FileList files = new ReadFileList(props).execute();
        parent.addText(String.format("%s's sheet named '%s' contains the following files:", props.getFileId(), props.getSheetName()));
        parent.addText(files.toString());
    }
}
