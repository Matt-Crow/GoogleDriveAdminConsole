package drive.commands.accessList;

import drive.commands.AbstractDriveCommand;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import services.ServiceAccess;

/**
 *
 * @author Matt
 */
public class GetAccessList extends AbstractDriveCommand<String[]>{
    private final String fileId;
    
    public GetAccessList(ServiceAccess service, String fileId){
        super(service);
        this.fileId = fileId;
    }
    
    @Override
    public String[] execute() throws IOException{        
        InputStream content = getDrive().files().get(fileId).executeMediaAsInputStream();
        InputStreamReader reader = new InputStreamReader(content);
        BufferedReader buff = new BufferedReader(reader);
        String[] array = buff.lines().filter((String userName)->!"".equals(userName.trim())).toArray((int size)->new String[size]);
        return array;
    }

}
