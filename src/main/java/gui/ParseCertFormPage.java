package gui;

import gui.components.PropertyFileSelector;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;
import javax.swing.BoxLayout;
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
public class ParseCertFormPage extends PageContent{
    private final PropertyFileSelector certFormSel;
    private final PropertyFileSelector fileListSel;
    private final JTextField enterAccListId;
    private final JCheckBox isTest;
    private final JButton run;
    
    public ParseCertFormPage(MainPane inPane) {
        super(inPane);
        setLayout(new BorderLayout());
        
        // file selectors
        JPanel selGroup = new JPanel();
        selGroup.setLayout(new GridLayout(1, 2));
        
        certFormSel = new PropertyFileSelector("Certification Form Info", "Select a file containing certification form properties", new CertificationFormInfo());
        selGroup.add(certFormSel);
        
        fileListSel = new PropertyFileSelector("File List Info", "Select a file containing file list properties", new FileListInfo());
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
            parseCertForm();
        });
        bottom.add(run, BorderLayout.PAGE_END);
        
        add(bottom, BorderLayout.PAGE_END);
    }
    
    private void parseCertForm(){
        Thread t = new Thread(){
            @Override
            public void run(){
                MainPane parent = getPaneParent();
                try {
                    List<UserToFileMapping> resolvedMappings = parent.getBackend().getCmdFactory().parseCertificationForm(
                        (CertificationFormInfo)certFormSel.getSelectedProperties(),
                        (FileListInfo) fileListSel.getSelectedProperties(),
                        enterAccListId.getText(),
                        isTest.isSelected()
                    ).execute();

                    parent.addText("Parsing Certification Form yielded the following information:");
                    resolvedMappings.forEach((mapping)->parent.addText(mapping.toString()));
                } catch (IOException ex) {
                    parent.getBackend().reportError(ex);
                }
            }
        };
        
        t.start();
        
    }
}
