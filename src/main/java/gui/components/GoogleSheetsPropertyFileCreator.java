package gui.components;

import java.io.IOException;
import structs.GoogleSheetProperties;
import sysUtils.FileSystem;
import sysUtils.Logger;

/**
 *
 * @author Matt
 */
public class GoogleSheetsPropertyFileCreator extends AbstractGoogleSheetsPropertyFileWidget {
    
    public GoogleSheetsPropertyFileCreator(String header) {
        super(header, true, "create");
    }

    @Override
    public void buttonPressed() {
        GoogleSheetProperties gsp = getSelectedProperties();
        gsp.clear();
        gsp.setFileId(getFileIdText());
        gsp.setSheetName(getSheetNameText());
        
        try {
            String path = FileSystem.getInstance().saveProperties(getLocalFileNameText(), gsp);
            MessagePopup.showMessage(this, String.format("Saved properties to %s", path));
        } catch (IOException ex) {
            Logger.logError(ex);
        }
        
    }

}
