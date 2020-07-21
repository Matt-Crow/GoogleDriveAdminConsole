package gui.pluginPages;

import gui.MainPane;
import gui.components.TextInputBox;
import gui.pages.AbstractFormPage;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import structs.GoogleSheetProperties;
import sysUtils.FileSystem;
import sysUtils.Logger;

/**
 *
 * @author Matt
 */
public class GoogleSheetPropsPage extends AbstractFormPage {
    private final TextInputBox propertyFileName;
    private final TextInputBox spreadsheetId;
    private final TextInputBox sheetName;
    
    public GoogleSheetPropsPage(MainPane inPane) {
        super(inPane);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        propertyFileName = new TextInputBox("File name", "enter a name for this property file");
        add(propertyFileName);
        
        spreadsheetId = new TextInputBox("Spreadsheet ID", "enter the ID of a Google Spreadsheet here");
        add(spreadsheetId);
        
        sheetName = new TextInputBox("Sheet name", "enter the sheet name");
        add(sheetName);
        
        JButton run = new JButton("Create");
        run.addActionListener((e)->{
            submit();
        });
        add(run);
    }

    @Override
    public void doSubmit() throws Exception {
        GoogleSheetProperties gsp = new GoogleSheetProperties();
        gsp.setFileId(spreadsheetId.getInput());
        gsp.setSheetName(sheetName.getInput());
        
        String path = FileSystem.getInstance().saveProperties(propertyFileName.getInput(), gsp);
        Logger.log(String.format("Saved properties to %s", path));
    }

}
