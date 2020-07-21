package pluginUtils;

import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;
import plugins.DownloadPermissionPlugin;
import plugins.FileListReaderPlugin;
import plugins.GiveAccessPlugin;
import plugins.GoogleSheetPropsPlugin;
import plugins.QuickAccessPlugin;
import plugins.UserListReaderPlugin;

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
public class PluginLoader {
    private final ServiceLoader<AbstractPlugin> loader;
    
    private static PluginLoader instance;
    
    private PluginLoader(){
        if(instance != null){
            throw new RuntimeException();
        }
        loader = ServiceLoader.load(AbstractPlugin.class);
    }
    
    public static PluginLoader getInstance(){
        if(instance == null){
            instance = new PluginLoader();
        }
        return instance;
    }
    
    /**
     * This will need to be redone once the loader works
     * @return 
     */
    public List<AbstractPlugin> getAllPlugins(){
        return Arrays.asList(
            new QuickAccessPlugin(),
            new GiveAccessPlugin(),
            new FileListReaderPlugin(),
            new UserListReaderPlugin(),
            new DownloadPermissionPlugin(),
            new GoogleSheetPropsPlugin()
        );
    }
    
    public void listDescs(){
        getAllPlugins().forEach((s)->System.out.println(s.getDescription()));
    }
    
    public static void main(String[] args){
        PluginLoader.getInstance().listDescs();
    }
}
