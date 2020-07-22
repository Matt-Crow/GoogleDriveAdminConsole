package structs;

/**
 * This class is used as the
 * minimum information required
 * to get a user for sharing files.
 * It is extended by DetailedUserInfo,
 * which is more descriptive.
 * 
 * @author Matt Crow
 */
public class SimpleUserInfo {
    private final String email;
    
    /**
     * 
     * @param emailAddr the email address of this user 
     */
    public SimpleUserInfo(String emailAddr){
        email = emailAddr;
    }
    
    /**
     * 
     * @return the email address of this user 
     */
    public final String getEmail(){
        return email;
    }
    
    @Override
    public String toString(){
        return email;
    }
    
    public boolean shouldGet(SimpleFileInfo file){
        return true;
    }
}
