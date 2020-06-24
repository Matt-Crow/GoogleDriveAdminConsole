package structs;

import fileUtils.FileList;
import java.util.ArrayList;
import java.util.List;

/**
 * The UserToFileMapping is used to designate who should have access to what file on the drive.
 * The GiveViewAccess class is responsible for resolving the mapping.
 * @see drive.commands.basic.GiveViewAccess
 * @author Matt Crow
 */
public class UserToFileMapping {
    private final SimpleUserInfo user;
    private final SimpleFileInfo file;
    
    public UserToFileMapping(SimpleUserInfo camper, SimpleFileInfo camperFile){
        user = camper;
        file = camperFile;
    }
    
    public static ArrayList<UserToFileMapping> constructUserFileList(List<? extends SimpleUserInfo> users, FileList files){
        ArrayList<UserToFileMapping> products = new ArrayList<>();
        users.forEach((user) -> {
            files.forEach((file) -> {
                products.add(new UserToFileMapping(user, file));
            });
        });
        return products;
    }
    
    public SimpleUserInfo getUser(){
        return user;        
    }
    public SimpleFileInfo getFile(){
        return file;
    }
    
    @Override
    public String toString(){
        return String.format(
            "%s should be given access to %s", 
            user.getEmail(),
            file.getFileId()
        );
    }
}
