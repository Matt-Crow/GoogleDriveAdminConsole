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
    private final Groups groups;
    
    /**
     * 
     * @param username the human name of this user
     * @param emailAddr the email address of this user
     * @param minecraftUsername the minecraft username of this user
     * @param groupNames the names of the groups this user belongs to, separated by '/''s
     */
    public DetailedUserInfo(String username, String emailAddr, String minecraftUsername, String groupNames){
        super(emailAddr);
        name = username;
        mcUsername = minecraftUsername;
        groups = new Groups(groupNames);
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
    
    // group class for this
    @Override
    public boolean shouldGet(SimpleFileInfo info){
        //     has no designated group             or                          this user is in the group
        return !(info instanceof DetailedFileInfo) || Groups.intersects(((DetailedFileInfo)info).getGroups(), groups);
    }
    
    @Override
    public String toString(){
        return String.format("User %s:" 
            + "\n\tEmail: %s"
            + "\n\tMinecraft Username: %s"
            + "\n\tGroup: %s", 
            name, getEmail(), mcUsername, groups.toString());
    }
}
