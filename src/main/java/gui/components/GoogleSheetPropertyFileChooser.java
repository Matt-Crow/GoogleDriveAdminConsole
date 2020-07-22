package gui.components;

import fileUtils.FileType;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import structs.GoogleSheetProperties;
import sysUtils.FileSystem;
import sysUtils.Logger;

/**
 * The GoogleSheetPropertyFileChooser is used to allow the user
 * to select .property files created by the user via
 * this program.
 * 
 * @author Matt Crow
 */
public class GoogleSheetPropertyFileChooser extends JComponent{
    private final JFileChooser chooser;
    private final GoogleSheetProperties selectedProperties;
    private final JTextArea fileIdDisplay;
    private final JTextArea sheetNameDisplay;
    private final JButton chooseFile;
    private boolean fileIsSelected;
    
    public GoogleSheetPropertyFileChooser(String header, String selectText){
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
        
        /*
        output = new JTextArea("***Property information appears here***");
        output.setEditable(false);
        output.setWrapStyleWord(true);
        output.setLineWrap(true);
        add(output, BorderLayout.CENTER);
        */
        
        // last row is the button
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        chooseFile = new JButton("Select File");
        chooseFile.addActionListener((e)->{
            chooseFile();
        });
        add(chooseFile, gbc);
        
        setMaximumSize(getPreferredSize());
        
        fileIsSelected = false;
        
        // might move this to FileSelector
        chooser = new JFileChooser(FileSystem.PROPS_FOLDER);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle(selectText);
        chooser.setFileFilter(new FileNameExtensionFilter(FileType.PROPERTIES.getDisplayText(), FileType.PROPERTIES.getExtensions()));
    }
    
    private void chooseFile(){
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            fileIsSelected = false;
            File f = chooser.getSelectedFile();
            try{
                selectedProperties.clear();
                selectedProperties.load(new FileInputStream(f));
                fileIdDisplay.setText(selectedProperties.getFileId());
                sheetNameDisplay.setText(selectedProperties.getSheetName());
                fileIsSelected = true;
            } catch (FileNotFoundException ex) {
                Logger.logError("Failed to locate file " + f.getAbsolutePath());
            } catch (IOException ex) {
                Logger.logError("Oops! " + ex.getLocalizedMessage());
            }
        }
    }
    
    /**
     * @return whether or not the user has
     * selected a valid property file.
     */
    public final boolean isFileSelected(){
        return fileIsSelected;
    }
    
    public final Properties getSelectedProperties(){
        return selectedProperties;
    }
}
