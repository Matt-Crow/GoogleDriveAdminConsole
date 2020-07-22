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
 to select .property files created by the user via
 this program.
 * 
 * @author Matt Crow
 */
public abstract class AbstractGoogleSheetsPropertyFileWidget extends JComponent{
    private final GoogleSheetProperties selectedProperties;
    private final JTextArea fileIdDisplay;
    private final JTextArea sheetNameDisplay;
    private final JButton chooseFile;
    
    public AbstractGoogleSheetsPropertyFileWidget(String header, String selectText){
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
        
        // second row is the file ID display
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        
        JLabel idLabel = new JLabel("File ID");
        gbc.gridx = 0;
        add(idLabel, gbc);
        
        fileIdDisplay = new JTextArea("file ID will appear here!");
        fileIdDisplay.setEditable(false);
        gbc.gridx = 1;
        add(fileIdDisplay, gbc);
        
        // third row is the sheet name display
        gbc.gridy = 2;
        
        JLabel nameLabel = new JLabel("Sheet Name");
        gbc.gridx = 0;
        add(nameLabel, gbc);
        
        sheetNameDisplay = new JTextArea("sheet name will appear here!");
        sheetNameDisplay.setEditable(false);
        gbc.gridx = 1;
        add(sheetNameDisplay, gbc);
        
        // last row is the button
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        chooseFile = new JButton("Select File");
        chooseFile.addActionListener((e)->{
            buttonPressed();
        });
        add(chooseFile, gbc);
        
        setMaximumSize(getPreferredSize());
        
    }
    
    public void setFileIdText(String text){
        fileIdDisplay.setText(text);
    }
    
    public void setSheetNameText(String text){
        sheetNameDisplay.setText(text);
    }
    
    public abstract void buttonPressed();
    
    public final GoogleSheetProperties getSelectedProperties(){
        return selectedProperties;
    }
}
