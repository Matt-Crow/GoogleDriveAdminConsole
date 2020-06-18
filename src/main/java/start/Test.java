package start;

import com.google.api.services.drive.Drive;
import services.ServiceAccess;
import structs.DetailedFileInfo;
import structs.UserToFileMapping;
import drive.commands.CommandFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import structs.CertificationFormInfo;
import structs.FileListInfo;
import structs.SimpleFileInfo;
import structs.DetailedUserInfo;
import structs.SimpleUserInfo;

// for crying out loud REMEMBER .EXECUTE()!!!
/**
 * 
 * https://cloud.google.com/compute/docs/api-rate-limits
 * https://console.developers.google.com/apis/api/drive.googleapis.com/quotas?authuser=2&project=camp-administration-console
 * 
 * @author Matt
 */

public class Test {
    public static void main(String... args) throws IOException, GeneralSecurityException {
        ServiceAccess service = ServiceAccess.getInstance();
        String accessListId = "1QHJvkVWmpgRZzY9bTeNSBSK_EDfMMHC8";
        
        CertificationFormInfo testCertFormInfo = new CertificationFormInfo(
            "1-eLPUcOp3MZyEo0g71hSt5P4-MD52kWh8FBomjVTVl4",
            "Form Responses 1",
            "Participants Name",
            "Participant's email",
            "Participant's Minecraft username",
            "Participating At What Level?"
        );
        
        CertificationFormInfo realCertFormInfo = new CertificationFormInfo(
            "1piKiPp3mqMVDsjEIZl5YJ2juDDFN8IL1esunD8okza0",
            "Form Responses 1",
            "Participants Name",
            "Participant's email",
            "Participant's Minecraft username ... Add To Science Report",
            "Participating At What Level?"
        );
        
        CertificationFormInfo june15 = new CertificationFormInfo(
            "1nF8Rbuc1Mnb0v_8ZB81Ci-Apge1bVQmMcA4AGHKsf4M",
            "Form Responses 1",
            "Participants Name",
            "Participant's email",
            "Participant's Minecraft username ... Add To Science Report",
            "Participating At What Level?"
        );
        
        FileListInfo fromAdminConsole = new FileListInfo(
            "1dtWFKcLKM8WyNVRV8G9Fmb-MANzvPqQwsiJOEFxCYOA",
            "files campers can view",
            "campers get a copy of these",
            "ID",
            "desc",
            "URL",
            "Level"
        );
        
        CommandFactory factory = new CommandFactory(service);
        
        factory.updateDownloadOptions(fromAdminConsole).execute();
        
        
        String[] emails = new String[]{
            "greengrappler12@gmail.com"
        };
        
        String[] fileIds = new String[]{
            "1rekHw2cK9VR9SeFNCB1NkvW9DhWcfQ07jcaeTuS1Ntk",
            "1hwDSDpLJ4-CMbuUQeSmptxyyx0zRb_lvCXAIbzNz7kY",
            "1oupONSo0G97DzwY_8rukMNlCXMBM1Qmb",
            "1sLFJQ8TftVNkij5a4gy0m1HxZCStjAzN"
        };
        
        List<SimpleUserInfo> users = Arrays.stream(emails).map((email)->{
            return new SimpleUserInfo(email);
        }).collect(Collectors.toList());
        
        List<SimpleFileInfo> files = Arrays.stream(fileIds).map((fId)->{
            return new SimpleFileInfo(fId);
        }).collect(Collectors.toList());
        
        List<UserToFileMapping> resolveMe = UserToFileMapping.constructUserFileList(users, files);
        
        factory.giveAccess(resolveMe).execute();
        /*
        factory.parseCertificationForm(
            june15,//testCertFormInfo, 
            fromAdminConsole,
            accessListId
        );//.execute();
        
        factory.addToAccessListCmd(accessListId, new String[]{
            "ProfSchuster",
            "BiPredicateTU",
            "fuzzbucket01"
        });//.execute();
        */
        //ArrayList<UserData> campers = factory.readCertForm("1piKiPp3mqMVDsjEIZl5YJ2juDDFN8IL1esunD8okza0").execute();
        //ArrayList<CamperFile> fileList = factory.readFileList("1dtWFKcLKM8WyNVRV8G9Fmb-MANzvPqQwsiJOEFxCYOA", "files campers can view", "campers get a copy of these").execute();
        
        //UserToFileMapping.constructUserFileList(campers, fileList).forEach(System.out::println);
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