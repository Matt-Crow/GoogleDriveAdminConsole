package gui;

import gui.components.CertFormPropSelector;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Matt
 */
public class ParseCertFormPage extends PageContent{
    //private final JButton selectCertFormInfo;
    //private final JButton selectUserListInfo;
    //private final JTextField enterAccListId;
    //private final JButton run;
    
    public ParseCertFormPage(MainPane inPane) {
        super(inPane);
        setLayout(new BorderLayout());
        
        // left hand side first
        JPanel leftSide = new JPanel();
        leftSide.setLayout(new GridLayout(1, 1));
        leftSide.add(new CertFormPropSelector());
        add(leftSide, BorderLayout.LINE_START);
        /*
        selectCertFormInfo = new JButton("Select Certification Form Property File");
        selectCertFormInfo.addActionListener((e)->{
        
        });
        add(selectCertFormInfo);
        
        selectUserListInfo = new JButton("Select User List Property File");
        selectUserListInfo.addActionListener((e)->{
        
        });
        add(selectUserListInfo);
        
        enterAccListId = new JTextField("Enter access list id");
        add(enterAccListId);
        
        run = new JButton("Run");
        run.addActionListener((e)->{
            
        });
        add(run);*/
    }

}
