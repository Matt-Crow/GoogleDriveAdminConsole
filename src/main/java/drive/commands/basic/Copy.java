package drive.commands.basic;

import structs.AccessType;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import drive.commands.AbstractDriveCommand;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import services.ServiceAccess;
import structs.CamperFile;
import structs.UserData;
import structs.UserToFileMapping;

/**
 *
 * @author Matt
 */
public class Copy extends AbstractDriveCommand<UserToFileMapping[]>{
    private final String toDirId;    
    private final List<UserToFileMapping> mappings;
    
    //Idea: make this get the output dir from the camper info
    public Copy(ServiceAccess service, List<UserToFileMapping> userToFiles, String outputDirId) {
        super(service);
        mappings = userToFiles;
        toDirId = outputDirId;
    }
    public Copy(ServiceAccess service, UserToFileMapping mapping, String outputDirId){
        this(service, Arrays.asList(mapping), outputDirId);
    }

    @Override
    public UserToFileMapping[] execute() throws IOException {
        ArrayList<UserToFileMapping> copiedFiles = new ArrayList<>();
        
        File orig = null;
        File copy = null;
        for(UserToFileMapping mapping : mappings){
            orig = getDrive().files().get(mapping.getFile().getFileId()).execute();
            // cannot directly copy folders. Since batching is async, we must do this unbatched
            if(orig.getMimeType().equals("application/vnd.google-apps.folder")){
                copy = new CreateFolder(getServiceAccess(), mapping.getUser().getName() + " 's " + orig.getName(), toDirId).execute();
                FileList children = getDrive().files().list()
                    .setSpaces("drive")
                    .setQ("'" + orig.getId() + "' in parents and trashed = false").execute();
                for(File child : children.getFiles()){
                    new Copy(
                        getServiceAccess(), 
                        new UserToFileMapping(
                            mapping.getUser(),
                            new CamperFile(child.getId(), child.getDescription(), "child url here", AccessType.EDIT)
                        ), copy.getId()
                    ).execute();
                };
            } else {
                File changes = new File();

                ArrayList<String> parents = new ArrayList<>();
                parents.add(toDirId);
                changes.setParents(parents);
                changes.setName(mapping.getUser().getName() + "'s " + orig.getName());

                // can't change permissions with copy, so I need to use Permissions afterward
                copy = getDrive().files().copy(mapping.getFile().getFileId(), changes).execute();
                copiedFiles.add(new UserToFileMapping(
                    mapping.getUser(),
                    new CamperFile(
                        copy.getId(), 
                        copy.getDescription(), 
                        "url here", 
                        AccessType.EDIT
                    )
                ));
            }
        }
        
        new GiveAccess(
            getServiceAccess(), 
            copiedFiles // may be too many to batch
        ).execute();
        
        return copiedFiles.toArray(new UserToFileMapping[copiedFiles.size()]);
    }

}
