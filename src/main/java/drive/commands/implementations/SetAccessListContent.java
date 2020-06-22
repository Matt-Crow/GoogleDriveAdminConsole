package drive.commands.implementations;

import com.google.api.client.http.FileContent;
import drive.commands.utils.AbstractDriveCommand;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashSet;
import start.ServiceAccess;

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
        HashSet<String> noDupes = new HashSet<>(Arrays.asList(newContent));
        newUserList = noDupes
            .stream()
            .map((name)->name.trim())
            .filter((name)->name.length() != 0)
            .toArray((size)->new String[size]);
        Arrays.sort(newUserList);
    }
    
    public final String[] getNewContent(){
        return newUserList;
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
