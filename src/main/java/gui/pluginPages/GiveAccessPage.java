package gui.pluginPages;

import drive.commands.implementations.ShareFiles;
import gui.MainPane;
import gui.components.GoogleSheetsPropertyFileChooser;
import gui.pages.AbstractFormPage;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
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
    private final GoogleSheetsPropertyFileChooser userPropSel;
    private final GoogleSheetsPropertyFileChooser filePropSel;
    private final JCheckBox isTest;
    private final JButton run;
    
    public GiveAccessPage(MainPane inPane) {
        super(inPane);
        setLayout(new BorderLayout());
        
        // file selectors
        JPanel selGroup = new JPanel();
        selGroup.setLayout(new GridLayout(1, 2));
        
        JPanel left = new JPanel();
        left.setLayout(new FlowLayout(FlowLayout.CENTER));
        selGroup.add(left);
        
        userPropSel = new GoogleSheetsPropertyFileChooser("User Spreadsheet Info", "Select a file containing user spreadsheet properties");
        userPropSel.setBorder(BorderFactory.createRaisedBevelBorder());
        left.add(userPropSel);
        
        JPanel right = new JPanel();
        right.setLayout(new FlowLayout(FlowLayout.CENTER));
        selGroup.add(right);
        
        filePropSel = new GoogleSheetsPropertyFileChooser("File Spreadsheet Info", "Select a file containing file spreadsheet properties");
        filePropSel.setBorder(BorderFactory.createRaisedBevelBorder());
        right.add(filePropSel);
        
        add(selGroup, BorderLayout.CENTER);
        
        // bottom
        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        isTest = new JCheckBox("This is just a test");
        isTest.setSelected(true);
        bottom.add(isTest);
        
        run = new JButton("Run");
        run.addActionListener((e)->{
            submit();
        });
        bottom.add(run);
        
        add(bottom, BorderLayout.PAGE_END);
    }
    
    @Override
    public void doSubmit() throws IOException {
        MainPane parent = getPaneParent();
        List<UserToFileMapping> resolvedMappings = new ShareFiles(
            userPropSel.getSelectedProperties(),
            filePropSel.getSelectedProperties(),
            isTest.isSelected()
        ).doExecute();

        parent.addText("Giving access yielded the following information:");
        resolvedMappings.forEach((mapping)->parent.addText(mapping.toString()));
    }
}
