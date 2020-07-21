package gui.pages;

import gui.MainPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author Matt
 */
public class CreateSheetPropertyPage extends AbstractFormPage {
    private final JTextField propertyFileName;
    private final JTextField spreadsheetId;
    private final JTextField sheetName;
    
    public CreateSheetPropertyPage(MainPane inPane) {
        super(inPane);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        propertyFileName = new JTextField();
        propertyFileName.setToolTipText("enter a name for this property file");
        add(propertyFileName);
        
        spreadsheetId = new JTextField();
        spreadsheetId.setToolTipText("enter the ID of a Google Spreadsheet here");
        add(spreadsheetId);
        
        sheetName = new JTextField();
        sheetName.setToolTipText("enter the sheet name");
        add(sheetName);
        
        JButton run = new JButton("Create");
        run.addActionListener((e)->{
            submit();
        });
        add(run);
    }

    @Override
    public void doSubmit() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
