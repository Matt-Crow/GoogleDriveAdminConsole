package structs;

/**
 *
 * @author Matt
 */
public final class UserData {
    private final String name;
    private final String email;
    private final String mcUsername;
    private final String level;
    
    public UserData(String username, String emailAddr, String minecraftUsername, String participationLevel){
        name = username;
        email = emailAddr;
        mcUsername = minecraftUsername;
        level = participationLevel;
    }
    
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getMinecraftUsername(){
        return mcUsername;
    }
    public String getLevel(){
        return level;
    }
    
    @Override
    public String toString(){
        return String.format("User %s:" 
            + "\n\tEmail: %s"
            + "\n\tMinecraft Username: %s"
            + "\n\tLevel: %s", name, email, mcUsername, level);
    }
}
