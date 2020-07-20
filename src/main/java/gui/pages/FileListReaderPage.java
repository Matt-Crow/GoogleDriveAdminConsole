package gui.pages;

import fileUtils.FileList;
import gui.MainPane;
import gui.components.PropertyFileChooser;
import java.awt.BorderLayout;
import javax.swing.JButton;
import structs.GoogleSheetProperties;

/**
 *
 * @author Matt
 */
public class FileListReaderPage extends AbstractFormPage{
    private final PropertyFileChooser chooser;
    public FileListReaderPage(MainPane inPane) {
        super(inPane);
        setLayout(new BorderLayout());
        chooser = new PropertyFileChooser("File List Properies", "select the file sheet properties", new GoogleSheetProperties());
        add(chooser, BorderLayout.CENTER);
        
        JButton run = new JButton("Run");
        run.addActionListener((e)->{
            submit();
        });
        add(run, BorderLayout.PAGE_END);
    }

    @Override
    public void doSubmit() throws Exception {
        MainPane parent = getPaneParent();
        FileList files = parent.getBackend().getCmdFactory().readFileList(
            (GoogleSheetProperties) chooser.getSelectedProperties()
        ).doExecute();
        parent.addText("contains the following files:");
        files.forEach((f)->parent.addText(f.toString()));
    }

}
