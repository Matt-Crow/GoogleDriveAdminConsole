package start;

/**
 *
 * @author Matt
 */
public class Application {
    private static Application instance;
    
    private Application(){
        if(instance != null){
            throw new ExceptionInInitializerError("Application is a singleton: Don't directly invoke the constructor, use Application.getInstance() instead");
        }
    }
    
    public static final void validate(){
        if(instance == null){
            instance = new Application();
        }
        // do checks
    }
    
    public static final Application getInstance(){
        if(instance == null){
            throw new NullPointerException("Looks like someone forgot to call Application.validate()!");
        }
        return instance;
    }
    
    private boolean isInstalled(){
        return false; // for now
    }
    
    private void install(){
        
    }
    
    private void launch(){
        
    }
    
    public final void start(){
        if(!isInstalled()){
            install();
        }
        launch();
    }
}
