package drive.commands.accessList;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import drive.commands.AbstractDriveCommand;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import services.ServiceAccess;

/**
 *
 * @author Matt
 */
public class SetAccessListContent extends AbstractDriveCommand<Boolean>{
    private final String accessListId;
    private final String[] newUserList;
    
    public SetAccessListContent(ServiceAccess service, String fileId, String[] newContent) {
        super(service);
        accessListId = fileId;
        newUserList = newContent;
    }

    @Override
    public Boolean execute() throws IOException {
        java.io.File temp = java.io.File.createTempFile("testing", "something");
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(temp));
        out.write(String.join("\n", newUserList));
        out.flush();
        out.close();
        com.google.api.services.drive.model.File googleFile = new com.google.api.services.drive.model.File();
        FileContent content = new FileContent("text/plain", temp);
        getDrive().files().update(accessListId, googleFile, content).execute();
        return true;
    }
}
