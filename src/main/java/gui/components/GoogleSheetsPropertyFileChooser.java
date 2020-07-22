package gui.components;

import fileUtils.FileType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import structs.GoogleSheetProperties;
import sysUtils.FileSystem;
import sysUtils.Logger;

/**
 *
 * @author Matt
 */
public class GoogleSheetsPropertyFileChooser extends AbstractGoogleSheetsPropertyFileWidget {
    private final JFileChooser chooser;
    private boolean fileIsSelected;
    
    public GoogleSheetsPropertyFileChooser(String header, String selectText) {
        super(header, selectText);
        // might move this to FileSelector
        chooser = new JFileChooser(FileSystem.PROPS_FOLDER);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle(selectText);
        chooser.setFileFilter(new FileNameExtensionFilter(FileType.PROPERTIES.getDisplayText(), FileType.PROPERTIES.getExtensions()));
        
        fileIsSelected = false;
    }

    @Override
    public void buttonPressed() {
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            fileIsSelected = false;
            File f = chooser.getSelectedFile();
            try{
                GoogleSheetProperties selectedProperties = getSelectedProperties();
                selectedProperties.clear();
                selectedProperties.load(new FileInputStream(f));
                setFileIdText(selectedProperties.getFileId());
                setSheetNameText(selectedProperties.getSheetName());
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

}
