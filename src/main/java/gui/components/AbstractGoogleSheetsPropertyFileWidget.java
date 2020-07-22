package gui.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import structs.GoogleSheetProperties;

/**
 * The AbstractGoogleSheetsPropertyFileWidget is used to allow the user
 * to select and create .property files used by this program
 * 
 * @author Matt Crow
 */
public abstract class AbstractGoogleSheetsPropertyFileWidget extends JComponent{
    private final GoogleSheetProperties selectedProperties;
    private final JTextArea localFileNameDisplay;
    private final JTextArea fileIdDisplay;
    private final JTextArea sheetNameDisplay;
    private final JButton button;
    
    public AbstractGoogleSheetsPropertyFileWidget(String header, boolean makeFieldsEditable, String buttonText){
        super();
        
        selectedProperties = new GoogleSheetProperties();
        
        // initialize constraints
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        // TODO add file name as well
        
        // first row is just the header
        JLabel head = new JLabel(header);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(head, gbc);
        
        // second row is the local property file name
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        
        JLabel localNameLabel = new JLabel("Property File Name");
        localNameLabel.setToolTipText("The box on the right contains the name this property file has on your computer");
        gbc.gridx = 0;
        add(localNameLabel, gbc);
        
        localFileNameDisplay = new JTextArea("Property file name will appear here!");
        localFileNameDisplay.setEditable(makeFieldsEditable);
        gbc.gridx = 1;
        add(localFileNameDisplay, gbc);
        
        // third row is the file ID display
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        
        JLabel idLabel = new JLabel("File ID");
        idLabel.setToolTipText("This is the file ID of the Google Spreadsheet this property file is referencing");
        gbc.gridx = 0;
        add(idLabel, gbc);
        
        fileIdDisplay = new JTextArea("file ID will appear here!");
        fileIdDisplay.setEditable(makeFieldsEditable);
        gbc.gridx = 1;
        add(fileIdDisplay, gbc);
        
        // fourth row is the sheet name display
        gbc.gridy = 3;
        
        JLabel nameLabel = new JLabel("Sheet Name");
        nameLabel.setToolTipText("This is the name of the sheet, contained in the aforementioned Google Spreadsheet, this property file is referencing");
        gbc.gridx = 0;
        add(nameLabel, gbc);
        
        sheetNameDisplay = new JTextArea("sheet name will appear here!");
        sheetNameDisplay.setEditable(makeFieldsEditable);
        gbc.gridx = 1;
        add(sheetNameDisplay, gbc);
        
        // last row is the button
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        button = new JButton(buttonText);
        button.addActionListener((e)->{
            buttonPressed();
        });
        add(button, gbc);
        
        setMaximumSize(getPreferredSize());
        
    }
    
    public void setLocalFileNameText(String text){
        localFileNameDisplay.setText(text);
    }
    public String getLocalFileNameText(){
        return localFileNameDisplay.getText();
    }
    
    public void setFileIdText(String text){
        fileIdDisplay.setText(text);
    }
    public String getFileIdText(){
        return fileIdDisplay.getText();
    }
    
    public void setSheetNameText(String text){
        sheetNameDisplay.setText(text);
    }
    public String getSheetNameText(){
        return sheetNameDisplay.getText();
    }
    
    public final GoogleSheetProperties getSelectedProperties(){
        return selectedProperties;
    }
    
    public abstract void buttonPressed();
}
