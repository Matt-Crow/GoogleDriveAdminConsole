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
    private final UserInfo user;
    private final FileInfo file;
    
    public UserToFileMapping(UserInfo person, FileInfo driveFile){
        user = person;
        file = driveFile;
    }
    
    public static ArrayList<UserToFileMapping> constructUserFileList(List<UserInfo> users, FileList files){
        ArrayList<UserToFileMapping> products = new ArrayList<>();
        users.forEach((user) -> {
            files.forEach((file) -> {
                if(Groups.intersects(user, file)){
                    products.add(new UserToFileMapping(user, file));
                }
            });
        });
        return products;
    }
    
    public UserInfo getUser(){
        return user;        
    }
    public FileInfo getFile(){
        return file;
    }
    
    @Override
    public String toString(){
        return String.format(
            "%s\nshould be given access to\n%s", 
            user.toString(),
            file.toString()
        );
    }
}
