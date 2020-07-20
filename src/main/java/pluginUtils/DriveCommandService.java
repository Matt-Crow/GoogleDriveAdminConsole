package pluginUtils;

import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;
import serviceProviderImpls.FileListReaderPlugin;
import serviceProviderImpls.GiveAccessPlugin;
import serviceProviderImpls.QuickAccessPlugin;
import serviceProviderImpls.UserListReaderPlugin;

/**
 * https://docs.oracle.com/javase/tutorial/ext/basics/spi.html
 * https://stackoverflow.com/questions/13254620/meta-inf-services-in-jar-with-gradle
 * 
 * 
 * Currently doesn't work
 * Will likely rename this at some point
 * 
 * @author Matt
 */
public class DriveCommandService {
    private final ServiceLoader<AbstractDriveCommandPlugin> loader;
    
    private static DriveCommandService instance;
    
    private DriveCommandService(){
        if(instance != null){
            throw new RuntimeException();
        }
        loader = ServiceLoader.load(AbstractDriveCommandPlugin.class);
    }
    
    public static DriveCommandService getInstance(){
        if(instance == null){
            instance = new DriveCommandService();
        }
        return instance;
    }
    
    /**
     * This will need to be redone once the loader works
     * @return 
     */
    public List<AbstractDriveCommandPlugin> getAllPlugins(){
        return Arrays.asList(
            new QuickAccessPlugin(),
            new GiveAccessPlugin(),
            new FileListReaderPlugin(),
            new UserListReaderPlugin()
        );
    }
    
    public void listDescs(){
        getAllPlugins().forEach((s)->System.out.println(s.getDescription()));
    }
    
    public static void main(String[] args){
        DriveCommandService.getInstance().listDescs();
    }
}
