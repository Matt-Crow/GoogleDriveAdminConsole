package start;

import com.google.api.services.drive.Drive;
import drive.DriveAccess;
import drive.commands.CommandFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;


// note this in readme: https://developers.google.com/resources/api-libraries/documentation/drive/v3/java/latest/overview-summary.html
// https://drive.google.com/uc?export=download&id=ID
/**
 * https://developers.google.com/drive/api/v3/quickstart/java?authuser=2
 * @author Matt
 */

public class Test {
    public static void main(String... args) throws IOException, GeneralSecurityException {
        Drive service = DriveAccess.getInstance().getDrive();
        CommandFactory factory = new CommandFactory(service);
        //1HHzcESLD0q4cqf3rUOETLhLjGvdqiASm Matt's test folder
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
        
        /*
        // Print the names and IDs for up to 10 files.
        FileList result = service.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name)")
                .execute();
        List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%s (%s)\n", file.getName(), file.getId());
            }
        }*/
    }
}