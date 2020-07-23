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
public final class DetailedUserInfo extends SimpleUserInfo{
    private final String name;
    private final String mcUsername;
    
    /**
     * 
     * @param username the human name of this user
     * @param emailAddr the email address of this user
     * @param minecraftUsername the minecraft username of this user
     * @param inGroups the groups this user belongs to
     */
    public DetailedUserInfo(String username, String emailAddr, String minecraftUsername, Groups inGroups){
        super(emailAddr, inGroups);
        name = username;
        mcUsername = minecraftUsername;
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
     * @return the Minecraft username of this user.
     */
    public String getMinecraftUsername(){
        return mcUsername;
    }
    
    @Override
    public String toString(){
        return String.format("User %s:" 
            + "\n\tEmail: %s"
            + "\n\tMinecraft Username: %s"
            + "\n\tGroup: %s", 
            name, getEmail(), mcUsername, getGroups().toString());
    }
}
