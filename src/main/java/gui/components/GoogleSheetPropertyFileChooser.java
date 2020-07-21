package gui.components;

import fileUtils.FileType;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import structs.GoogleSheetProperties;
import sysUtils.FileSystem;

/**
 * The GoogleSheetPropertyFileChooser is used to allow the user
 to select .property files created by the user via
 this program.
 * 
 * @author Matt Crow
 */
public class GoogleSheetPropertyFileChooser extends JComponent{
    private final JFileChooser chooser;
    private final Properties selectedProperties;
    private final TextScroller output;
    private final JButton chooseFile;
    private boolean fileIsSelected;
    
    public GoogleSheetPropertyFileChooser(String header, String selectText){
        super();
        
        selectedProperties = new GoogleSheetProperties();
        
        setLayout(new BorderLayout());
        
        add(new JLabel(header), BorderLayout.PAGE_START);
        
        output = new TextScroller();
        add(output, BorderLayout.CENTER);
        
        chooseFile = new JButton("Select File");
        chooseFile.addActionListener((e)->{
            chooseFile();
        });
        add(chooseFile, BorderLayout.PAGE_END);
        
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
                output.clear();
                output.addText(String.format("Contents of %s :", f.getAbsolutePath()));
                output.addText(selectedProperties.toString());
                fileIsSelected = true;
            } catch (FileNotFoundException ex) {
                output.addText("Failed to locate file " + f.getAbsolutePath());
            } catch (IOException ex) {
                output.addText("Oops! " + ex.getLocalizedMessage());
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
