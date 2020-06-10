package start;

import com.google.api.services.drive.Drive;
import drive.DriveAccess;
import structs.CamperFile;
import structs.UserToFileMapping;
import drive.commands.CommandFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import structs.UserData;


// note this in readme: https://developers.google.com/resources/api-libraries/documentation/drive/v3/java/latest/overview-summary.html
// https://drive.google.com/uc?export=download&id=ID

// https://developers.google.com/sheets/api/quickstart/java
// https://developers.google.com/resources/api-libraries/documentation/sheets/v4/java/latest/

// add people to Camper group, that group has view access to files?

// for crying out loud REMEMBER .EXECUTE()!!!
/**
 * https://developers.google.com/drive/api/v3/quickstart/java?authuser=2
 * @author Matt
 */

public class Test {
    public static void main(String... args) throws IOException, GeneralSecurityException {
        Drive service = DriveAccess.getInstance().getDrive();
        CommandFactory factory = new CommandFactory(service);
        
        ArrayList<UserData> campers = factory.readCertForm("1piKiPp3mqMVDsjEIZl5YJ2juDDFN8IL1esunD8okza0").execute();
        ArrayList<CamperFile> fileList = factory.readFileList("1dtWFKcLKM8WyNVRV8G9Fmb-MANzvPqQwsiJOEFxCYOA", "files campers can view", "campers get a copy of these").execute();
        
        UserToFileMapping.constructUserFileList(campers, fileList).forEach(System.out::println);
        /*
        String[] emails = new String[]{
            "greengrappler12@gmail.com"
        };
        //1HHzcESLD0q4cqf3rUOETLhLjGvdqiASm Matt's test folder
        String[] copyFiles = new String[]{
            "1ja9-iMiXcjVgLPmusd6dGdikzyJkFu1Y",
            "1hwDSDpLJ4-CMbuUQeSmptxyyx0zRb_lvCXAIbzNz7kY"
        };
        for(String file : copyFiles){
            factory.makeCopyFor(file, "1HHzcESLD0q4cqf3rUOETLhLjGvdqiASm", emails[0], "Matt").execute();
        }*/
        
        /*
        String[] fileIds = new String[]{
            "1rekHw2cK9VR9SeFNCB1NkvW9DhWcfQ07jcaeTuS1Ntk",
            "1hwDSDpLJ4-CMbuUQeSmptxyyx0zRb_lvCXAIbzNz7kY",
            "1oupONSo0G97DzwY_8rukMNlCXMBM1Qmb",
            "1sLFJQ8TftVNkij5a4gy0m1HxZCStjAzN"
        };
        
        
        for(String fileId : fileIds){
            factory.giveViewAccess(fileId, emails).execute();
        }*
        /*
        String id = factory.createAccessListCmd("1HHzcESLD0q4cqf3rUOETLhLjGvdqiASm").execute().getId();
        factory.setAccessListCmd(id, new String[]{"BiPredicateTU", "ProfSchuster"}).execute();
        String[] userNames = factory.getAccessListCmd(id).execute();
        
        System.out.println("After setAccessListCmd, user names are:");
        Arrays.stream(userNames).forEach(System.out::println);
        
        String[] appended = factory.addToAccessListCmd(id, new String[]{"NonexistantJoe", "Bob"}).execute();
        System.out.println("After appending, it should contain");
        Arrays.stream(appended).forEach(System.out::println);
        
        String[] newContent = factory.getAccessListCmd(id).execute();
        System.out.println("After appending, it actually contains");
        Arrays.stream(newContent).forEach(System.out::println);
        */
    }
}