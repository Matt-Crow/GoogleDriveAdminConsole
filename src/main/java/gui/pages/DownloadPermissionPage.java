package gui.pages;

import gui.MainPane;
import gui.components.PropertyFileChooser;
import java.awt.BorderLayout;
import javax.swing.JButton;
import structs.GoogleSheetProperties;

/**
 *
 * @author Matt
 */
public class DownloadPermissionPage extends AbstractFormPage {
    private final PropertyFileChooser chooser;
    
    public DownloadPermissionPage(MainPane inPane) {
        super(inPane);
        setLayout(new BorderLayout());
        
        chooser = new PropertyFileChooser("File Sheet Info", "select the property file containing file list info", new GoogleSheetProperties());
        add(chooser, BorderLayout.CENTER);
        
        JButton run = new JButton("Run");
        run.addActionListener((e)->submit());
        add(run, BorderLayout.PAGE_END);
    }

    @Override
    public void doSubmit() throws Exception {
        MainPane parent = getPaneParent();
        String[] output = parent
            .getBackend()
            .getCmdFactory()
            .updateDownloadOptions(
                (GoogleSheetProperties) chooser.getSelectedProperties()
            ).doExecute();
        parent.addText("Updated download permissions for the following files:");
        for(String out : output){
            parent.addText(out);
        }
    }

}
