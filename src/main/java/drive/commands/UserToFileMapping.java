package drive.commands;

import java.util.ArrayList;
import users.UserData;

/**
 * @author Matt
 */
public class UserToFileMapping {
    private final UserData user;
    private final CamperFile file;
    
    public UserToFileMapping(UserData camper, CamperFile camperFile){
        user = camper;
        file = camperFile;
    }
    
    public static ArrayList<UserToFileMapping> constructUserFileList(ArrayList<UserData> users, ArrayList<CamperFile> files){
        ArrayList<UserToFileMapping> products = new ArrayList<>();
        for(UserData user : users){
            for(CamperFile file : files){
                products.add(new UserToFileMapping(user, file));
            }
        }
        return products;
    }
    
    @Override
    public String toString(){
        return String.format(
            "%s should be given %s access to %s", 
            user.getName(),
            file.getAccessType().getDriveRole(),
            file.getFileId()
        );
    }
}
