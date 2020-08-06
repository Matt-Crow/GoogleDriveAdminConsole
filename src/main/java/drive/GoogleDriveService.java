package drive;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.BindException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import start.Application;
import sysUtils.FileSystem;

/**
 * The GoogleDriveService is a Singleton
 * class used to interface with Google Drive and Sheets.
 * 
 * Future versions may get rid of the Google Sheets
 * interface in favor of using CSV files to maximize
 * versatility.
 * 
 * @author Matt Crow
 */
public class GoogleDriveService {
    private final NetHttpTransport httpTransport;
    private final JsonFactory jsonFactory;
    private final String tokenDirPath;
    private final List<String> scopes;
    
    //maybe subclass these
    private final Drive driveService;
    private final Sheets sheetService;
    
    private static GoogleDriveService instance;
    public static final String CREDENTIAL_PATH = Paths.get(FileSystem.CREDENTIALS_FOLDER, "credentials.json").toString();
    
    private GoogleDriveService() throws GeneralSecurityException, IOException{
        if(instance != null){
            throw new ExceptionInInitializerError("DriveAccess is a singleton");
        }
        jsonFactory = JacksonFactory.getDefaultInstance();
        tokenDirPath = FileSystem.TOKENS_FOLDER;
        
        //If modifying these scopes, delete your previously saved tokens/ folder.
        scopes = new ArrayList<>();
        scopes.add(DriveScopes.DRIVE_READONLY);
        scopes.add(DriveScopes.DRIVE_METADATA);
        scopes.add(SheetsScopes.SPREADSHEETS_READONLY);
        httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        
        Credential credentials = createCredentials();
        driveService = new Drive.Builder(httpTransport, jsonFactory, credentials).setApplicationName(Application.APPLICATION_NAME).build();
        sheetService = new Sheets.Builder(httpTransport, jsonFactory, credentials).setApplicationName(Application.APPLICATION_NAME).build();
    }
    
    private Credential createCredentials() throws FileNotFoundException, IOException{
        //Load client secret
        InputStream in = new FileInputStream(CREDENTIAL_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(tokenDirPath)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver.Builder receiverBuild = new LocalServerReceiver.Builder();//.setPort(8888).build();
        LocalServerReceiver receiver = null;
        Credential credit = null;
        //http://svn.apache.org/viewvc/camel/trunk/components/camel-test/src/main/java/org/apache/camel/test/AvailablePortFinder.java?view=markup#l130
        //https://stackoverflow.com/questions/434718/sockets-discover-port-availability-using-java
        int minPort = 1100;
        int maxPort = 49151;
        for(int port = minPort; credit == null && port < maxPort; port++){
            try {
                receiver = receiverBuild.setPort(port).build();
                credit = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
            } catch (BindException portInUse){
                receiver = null;
                credit = null;
            }
        }
        if(credit == null){
            throw new IOException("No ports available");
        }
        return credit;
    }
    
    public static final boolean credentialsExist(){
        return Files.exists(Paths.get(CREDENTIAL_PATH));
    }
    
    /**
     * Call this method at the beginning of the program to ensure the Google Drive API works.
     * @return
     * @throws GeneralSecurityException
     * @throws IOException 
     */
    public static final boolean validateService() throws GeneralSecurityException, IOException{
        boolean isValidated = false;
        if(instance == null){
            instance = new GoogleDriveService();
            isValidated = true;
        } else {
            isValidated = true;
        }
        return isValidated;
    }
    /**
     * Use this method to gain access to the GoogleDriveService.
     * If the drive service has not yet been initialized, calls
     * the constructor.
     * 
     * @return the GoogleDriveService instance.
     */
    public static final GoogleDriveService getInstance(){
        if(instance == null){
            throw new NullPointerException("It looks like someone forgot to invoke GoogleDriveService.validateService()! Please make sure to call it before this method");
        }
        return instance;
    }
    
    public final Drive getDrive(){
        return driveService;
    }
    
    public final Sheets getSheets(){
        return sheetService;
    }
    
    public final void logOut(){
        Path tokens = Paths.get(FileSystem.TOKENS_FOLDER);
        if(tokens.toFile().isDirectory()){
            for(File f : tokens.toFile().listFiles()){
                f.delete();
            }
        }
    }
}
