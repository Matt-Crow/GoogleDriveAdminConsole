package structs;

/**
 * This class is used to store data
 * downloaded from the user list via
 * the ParseCertificationForm command.
 * It is used to connect various account
 * information datums together.
 * 
 * @author Matt Crow
 */
public final class DetailedUserInfo extends SimpleUserInfo{
    private final String name;
    private final String mcUsername;
    private final String level;
    
    /**
     * 
     * @param username the human name of this user
     * @param emailAddr the email address of this user
     * @param minecraftUsername the minecraft username of this user
     * @param participationLevel the camp level this user is participating at.
     */
    public DetailedUserInfo(String username, String emailAddr, String minecraftUsername, String participationLevel){
        super(emailAddr);
        name = username;
        mcUsername = minecraftUsername;
        level = participationLevel;
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
    
    /**
     * 
     * @return the camp participation level of this user.
     */
    public String getLevel(){
        return level;
    }
    
    @Override
    public String toString(){
        return String.format("User %s:" 
            + "\n\tEmail: %s"
            + "\n\tMinecraft Username: %s"
            + "\n\tLevel: %s", name, getEmail(), mcUsername, level);
    }
}
