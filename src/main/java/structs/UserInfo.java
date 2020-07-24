package structs;

/**
 * This class is used to store data
 * downloaded from the user list via
 * the ReadUserList command.
 * It is used to connect various account
 * information datums together.
 * 
 * @author Matt Crow
 */
public final class UserInfo implements Groupable {
    private final String email;
    private final Groups groups;
    private final String name;
    private final String mcUsername;
    
    /**
     * 
     * @param username the human name of this user
     * @param emailAddr the email address of this user
     * @param minecraftUsername the minecraft username of this user
     * @param inGroups the groups this user belongs to
     */
    public UserInfo(String username, String emailAddr, String minecraftUsername, Groups inGroups){
        email = emailAddr;
        name = username;
        mcUsername = minecraftUsername;
        groups = inGroups;
    }
    
    /**
     * 
     * @return the human name of this user.
     */
    public String getName(){
        return name;
    }
    
    /**
     * 
     * @return the email address of this user 
     */
    public final String getEmail(){
        return email;
    }
    
    /**
     * 
     * @return the Minecraft username of this user.
     */
    public String getMinecraftUsername(){
        return mcUsername;
    }
    
    @Override
    public Groups getGroups() {
        return groups;
    }
    
    @Override
    public String toString(){
        return String.format("User %s:" 
            + "\n\tEmail: %s"
            + "\n\tMinecraft Username: %s"
            + "\n\tGroups: %s", 
            name, email, mcUsername, groups.toString());
    }
}
