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
public class SimpleUserInfo implements Groupable {
    private final String email;
    private final Groups groups;
    
    /**
     * 
     * @param emailAddr the email address of this user 
     * @param g the Groups this user belongs to
     */
    public SimpleUserInfo(String emailAddr, Groups g){
        email = emailAddr;
        groups = g;
    }
    
    /**
     * 
     * @param emailAddr the email address of this user 
     */
    public SimpleUserInfo(String emailAddr){
        this(emailAddr, new Groups(Groups.ALL_GROUP));
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

    @Override
    public Groups getGroups() {
        return groups;
    }
}
