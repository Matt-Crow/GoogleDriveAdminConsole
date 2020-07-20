package gui.pages;

import gui.MainPane;
import gui.components.PropertyFileChooser;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import structs.GoogleSheetProperties;
import structs.UserToFileMapping;

/**
 * This page handles reading a user- and file-list,
 * then gives each user access to each file.
 * 
 * I need a better name for this
 * 
 * @author Matt Crow
 */
public class GiveAccessPage extends AbstractFormPage{
    private final PropertyFileChooser userPropSel;
    private final PropertyFileChooser filePropSel;
    private final JTextField enterAccListId;
    private final JCheckBox isTest;
    private final JButton run;
    
    public GiveAccessPage(MainPane inPane) {
        super(inPane);
        setLayout(new BorderLayout());
        
        // file selectors
        JPanel selGroup = new JPanel();
        selGroup.setLayout(new GridLayout(1, 2));
        
        userPropSel = new PropertyFileChooser("User Spreadsheet Info", "Select a file containing user spreadsheet properties", new GoogleSheetProperties());
        selGroup.add(userPropSel);
        
        filePropSel = new PropertyFileChooser("File Spreadsheet Info", "Select a file containing file spreadsheet properties", new GoogleSheetProperties());
        selGroup.add(filePropSel);
        
        add(selGroup, BorderLayout.CENTER);
        
        // bottom
        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());
        
        enterAccListId = new JTextField("Enter access list id");
        bottom.add(enterAccListId, BorderLayout.CENTER);
        
        isTest = new JCheckBox("This is just a test");
        isTest.setSelected(true);
        bottom.add(isTest, BorderLayout.LINE_END);
        
        run = new JButton("Run");
        run.addActionListener((e)->{
            submit();
        });
        bottom.add(run, BorderLayout.PAGE_END);
        
        add(bottom, BorderLayout.PAGE_END);
    }
    
    @Override
    public void doSubmit() throws IOException {
        MainPane parent = getPaneParent();
        List<UserToFileMapping> resolvedMappings = parent.getBackend().getCmdFactory().parseCertificationForm((GoogleSheetProperties)userPropSel.getSelectedProperties(),
            (GoogleSheetProperties) filePropSel.getSelectedProperties(),
            enterAccListId.getText(),
            isTest.isSelected()
        ).doExecute();

        parent.addText("Giving access yielded the following information:");
        resolvedMappings.forEach((mapping)->parent.addText(mapping.toString()));
    }
}
