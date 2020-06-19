package gui;

import gui.components.PropertyFileSelector;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import structs.CertificationFormInfo;
import structs.FileListInfo;

/**
 *
 * @author Matt
 */
public class ParseCertFormPage extends PageContent{
    private final PropertyFileSelector certFormSel;
    private final PropertyFileSelector fileListSel;
    private final JTextField enterAccListId;
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
        
        run = new JButton("Run");
        run.addActionListener((e)->{
            
        });
        bottom.add(run, BorderLayout.PAGE_END);
        
        add(bottom, BorderLayout.PAGE_END);
    }

}
