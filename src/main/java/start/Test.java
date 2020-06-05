package start;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import drive.DriveAccess;
import drive.commands.AbstractDriveCommand;
import drive.commands.GetAccessList;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

/**
 * https://developers.google.com/drive/api/v3/quickstart/java?authuser=2
 * @author Matt
 */

public class Test {
    public static void main(String... args) throws IOException, GeneralSecurityException {
        Drive service = DriveAccess.getInstance().getDrive();
        
        GetAccessList c = new GetAccessList("1QHJvkVWmpgRZzY9bTeNSBSK_EDfMMHC8", service);
        System.out.println(Arrays.toString(c.execute()));
        
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
        }
    }
}