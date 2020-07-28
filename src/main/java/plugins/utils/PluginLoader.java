package plugins.utils;

import plugins.utils.AbstractPlugin;
import java.util.Arrays;
import java.util.List;
import plugins.implementations.downloadPermission.DownloadPermissionPlugin;
import plugins.implementations.fileListReader.FileListReaderPlugin;
import plugins.implementations.shareFiles.GiveAccessPlugin;
import plugins.implementations.googleSheetPropertyFileCreator.GoogleSheetPropsPlugin;
import plugins.implementations.userListReader.UserListReaderPlugin;

/**
 * https://docs.oracle.com/javase/tutorial/ext/basics/spi.html
 * https://stackoverflow.com/questions/13254620/meta-inf-services-in-jar-with-gradle
 * 
 * 
 * Currently doesn't work
 * 
 * Change this to do the following:
 * 1. Iterate over the classpath (not just loaded classes)
 * 2. Gather each class implementing AbstractPlugin
 * 
 * @author Matt
 */
public class PluginLoader {
    //private final ServiceLoader<AbstractPlugin> loader;
    
    private static PluginLoader instance;
    
    private PluginLoader(){
        if(instance != null){
            throw new RuntimeException();
        }
        //loader = ServiceLoader.load(AbstractPlugin.class);
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
