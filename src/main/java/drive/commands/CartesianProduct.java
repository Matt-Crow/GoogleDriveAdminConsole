package drive.commands;

import java.util.ArrayList;
import users.UserData;

/**
 * Placeholder name
 * @author Matt
 */
public class CartesianProduct {
    private final UserData user;
    private final CamperFile file;
    
    public CartesianProduct(UserData camper, CamperFile camperFile){
        user = camper;
        file = camperFile;
    }
    
    public static ArrayList<CartesianProduct> constructUserFileList(ArrayList<UserData> users, ArrayList<CamperFile> files){
        ArrayList<CartesianProduct> products = new ArrayList<>();
        for(UserData user : users){
            for(CamperFile file : files){
                products.add(new CartesianProduct(user, file));
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
