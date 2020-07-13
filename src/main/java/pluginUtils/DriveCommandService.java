package pluginUtils;

import java.util.ServiceLoader;

/**
 * https://docs.oracle.com/javase/tutorial/ext/basics/spi.html
 * @author Matt
 */
public class DriveCommandService {
    private final ServiceLoader<AbstractDriveCommandPlugin> loader;
    public DriveCommandService(){
        loader = ServiceLoader.load(AbstractDriveCommandPlugin.class);
    }
    
    public void listDescs(){
        loader.forEach((s)->System.out.println(s.getDescription()));
    }
    
    public static void main(String[] args){
        new DriveCommandService().listDescs();
    }
}
