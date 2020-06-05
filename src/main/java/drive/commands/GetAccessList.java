package drive.commands;

import com.google.api.services.drive.Drive;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 *
 * @author Matt
 */
public class GetAccessList extends AbstractDriveCommand<String[]>{
    private final String fileId;
    
    public GetAccessList(String fileId, Drive d){
        super(d);
        this.fileId = fileId;
    }
    
    @Override
    public String[] execute() throws IOException{
        URL url = new URL(String.format("https://drive.google.com/uc?export=download&id=%s", fileId));
        InputStream content = url.openStream();
        
        //InputStream content = getDrive().files().get(fileId).executeMediaAsInputStream();
        InputStreamReader reader = new InputStreamReader(content);
        BufferedReader buff = new BufferedReader(reader);
        String[] array = buff.lines().filter((String userName)->!"".equals(userName.trim())).toArray((int size)->new String[size]);
        return array;
    }

}
