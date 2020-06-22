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
import structs.CertificationFormInfo;
import structs.FileListInfo;
import structs.UserToFileMapping;

/**
 *
 * @author Matt
 */
public class ParseCertFormPage extends AbstractFormPage{
    private final PropertyFileChooser certFormSel;
    private final PropertyFileChooser fileListSel;
    private final JTextField enterAccListId;
    private final JCheckBox isTest;
    private final JButton run;
    
    public ParseCertFormPage(MainPane inPane) {
        super(inPane);
        setLayout(new BorderLayout());
        
        // file selectors
        JPanel selGroup = new JPanel();
        selGroup.setLayout(new GridLayout(1, 2));
        
        certFormSel = new PropertyFileChooser("Certification Form Info", "Select a file containing certification form properties", new CertificationFormInfo());
        selGroup.add(certFormSel);
        
        fileListSel = new PropertyFileChooser("File List Info", "Select a file containing file list properties", new FileListInfo());
        selGroup.add(fileListSel);
        
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
        List<UserToFileMapping> resolvedMappings = parent.getBackend().getCmdFactory().parseCertificationForm(
            (CertificationFormInfo)certFormSel.getSelectedProperties(),
            (FileListInfo) fileListSel.getSelectedProperties(),
            enterAccListId.getText(),
            isTest.isSelected()
        ).execute();

        parent.addText("Parsing Certification Form yielded the following information:");
        resolvedMappings.forEach((mapping)->parent.addText(mapping.toString()));
    }
}
