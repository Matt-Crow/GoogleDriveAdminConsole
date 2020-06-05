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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import start.Main;

/**
 *
 * @author Matt
 */
public class DriveAccess {
    private final NetHttpTransport httpTransport;
    private final JsonFactory jsonFactory;
    private final String tokenDirPath;
    private final String credentialFilePath;
    private final List<String> scopes;
    private final Drive driveService;
    
    private static DriveAccess instance;
    
    private DriveAccess() throws GeneralSecurityException, IOException{
        if(instance != null){
            throw new ExceptionInInitializerError("DriveAccess is a singleton");
        }
        jsonFactory = JacksonFactory.getDefaultInstance();
        tokenDirPath = "tokens";
        credentialFilePath = "/credentials.json";
        
        //If modifying these scopes, delete your previously saved tokens/ folder.
        scopes = new ArrayList<>();
        scopes.add(DriveScopes.DRIVE);
        scopes.add(DriveScopes.DRIVE_FILE); // not sure which I need
        
        httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        driveService = new Drive.Builder(httpTransport, jsonFactory, createCredentials()).setApplicationName(Main.APPLICATION_NAME).build();
    }
    
    private Credential createCredentials() throws FileNotFoundException, IOException{
        //Load client secret
        InputStream in = DriveAccess.class.getResourceAsStream(credentialFilePath);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + credentialFilePath);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(tokenDirPath)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    
    }
    
    public static final DriveAccess getInstance() throws IOException, GeneralSecurityException{
        if(instance == null){
            instance = new DriveAccess();
        }
        return instance;
    }
    
    public final Drive getDrive(){
        return driveService;
    }
}
