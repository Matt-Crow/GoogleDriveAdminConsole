package structs;

import java.util.ArrayList;

/**
 * The UserToFileMapping is used to design who should have access to what file on the drive.
 * The GiveViewAccess class is responsible for resolving the mapping.
 * @see drive.commands.basic.GiveViewAccess
 * @author Matt Crow
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
        users.forEach((user) -> {
            files.forEach((file) -> {
                products.add(new UserToFileMapping(user, file));
            });
        });
        return products;
    }
    
    public UserData getUser(){
        return user;        
    }
    public CamperFile getFile(){
        return file;
    }
    
    @Override
    public String toString(){
        return String.format(
            "%s should be given access to %s", 
            user.getName(),
            file.getFileId()
        );
    }
}
