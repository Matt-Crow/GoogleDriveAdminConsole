package gui.pluginPages;

import gui.MainPane;
import gui.components.GoogleSheetsPropertyFileChooser;
import gui.pages.AbstractFormPage;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import structs.GoogleSheetProperties;

/**
 *
 * @author Matt
 */
public class DownloadPermissionPage extends AbstractFormPage {
    private final GoogleSheetsPropertyFileChooser chooser;
    
    public DownloadPermissionPage(MainPane inPane) {
        super(inPane);
        setLayout(new BorderLayout());
        
        JPanel center = new JPanel();
        center.setLayout(new FlowLayout(FlowLayout.CENTER));
        add(center, BorderLayout.CENTER);
        
        chooser = new GoogleSheetsPropertyFileChooser("File Sheet Info", "select the property file containing file list info");
        center.add(chooser);
        
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
