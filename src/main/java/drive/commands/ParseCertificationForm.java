package drive.commands;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import java.io.IOException;

/**
 * make this add multiple people to multiple files using threads
 * @author Matt
 */
public class ParseCertificationForm extends AbstractDriveCommand<File[]>{

    public ParseCertificationForm(Drive d) {
        super(d);
    }

    @Override
    public File[] execute() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
