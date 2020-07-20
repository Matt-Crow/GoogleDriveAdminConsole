package gui;

import drive.commands.utils.CommandFactory;
import fileUtils.FileList;
import fileUtils.FileSelector;
import fileUtils.FileType;
import fileUtils.UserList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import start.GoogleDriveService;
import structs.GoogleSheetProperties;
import structs.UserToFileMapping;
import sysUtils.Logger;

/**
 *
 * @author Matt
 */
public final class GuiBackend {
    private final CommandFactory cmdFact;
    private final MainPane frontEnd;
    
    public GuiBackend(MainPane pane, GoogleDriveService service){
        frontEnd = pane;
        cmdFact = new CommandFactory(service);
    }
    
    // not sure I like the idea of having one of these for each command
    public void askAddToAccessList(){
        String[] mcAccts = ask("Enter a list of Minecraft users to add to an access list, seperated by commas: ").split(",");
        String accListId = ask("Enter the ID of the access list to add them to: ");
        try {
            String[] newUsers = getCmdFactory().addToAccessListCmd(accListId, mcAccts).doExecute();
            writeOutput("New users:");
            for(String user : newUsers){
                writeOutput(String.format("* %s", user));
            }
        } catch (IOException ex) {
            reportError(ex);
        }
    }
    public void askCreateAccessList(){
        String parentId = ask("Enter the ID of the Google Drive folder to add this access list to: ");
        try{
            String newFileId = getCmdFactory().createAccessListCmd(parentId).doExecute().getId();
            writeOutput(String.format("Successfully created the access list in %s. It has an id of %s. Make sure to update the Science Report to reference this new file", parentId, newFileId));
        } catch (IOException ex) {
            reportError(ex);
        }
    }
    public void askDisplayAccessList(){
        String fileId = ask("Enter the ID of an access list to view: ");
        try{
            String[] mcUsers = getCmdFactory().getAccessListCmd(fileId).doExecute();
            writeOutput("Users are:");
            for(String user : mcUsers){
                writeOutput(String.format("* %s", user));
            }
        } catch (IOException ex) {
            reportError(ex);
        }
    }
    public void askClearAccessList(){
        String fileId = ask("Enter the ID of the access list to clear all users from: ");
        try{
            getCmdFactory().setAccessListCmd(fileId, new String[0]).doExecute();
            writeOutput(String.format("%s has been cleared.", fileId));
        } catch(IOException ex){
            reportError(ex);
        }
    }
    
    public void askReadFileList(){
        new FileSelector("Select a file containing file list properties", FileType.ANY, (File f)->{
            try {
                GoogleSheetProperties info = new GoogleSheetProperties();
                info.load(new FileInputStream(f));
                writeOutput(info.toString());
                FileList files = getCmdFactory().readFileList(info).doExecute();
                writeOutput("Contains the following files:");
                files.forEach((i)->writeOutput("* " + i.toString()));
            } catch (FileNotFoundException ex) {
                reportError(ex);
            } catch (IOException ex) {
                reportError(ex);
            }
        }).openDialog();
    }
    public void askReadCertForm(){
        new FileSelector("Select a file containing certification form properties", FileType.ANY, (File f)->{
            try{
                GoogleSheetProperties info = new GoogleSheetProperties();
                info.load(new FileInputStream(f));
                writeOutput(info.toString());
                UserList users = getCmdFactory().readCertForm(info).doExecute();
                writeOutput("Contains the following users:");
                users.forEach((i)->writeOutput(i.toString()));
            } catch (FileNotFoundException ex) {
                reportError(ex);
            } catch (IOException ex) {
                reportError(ex);
            }
        }).openDialog();
    }
    
    public void askParseCertificationForm(){
        new FileSelector("Select a file containing certification form properties", FileType.ANY, (File certFormInfoFile)->{
            new FileSelector("Select a file containing file list properties", FileType.ANY, (File fileListInfoFile)->{
                String accessListId = ask("Enter the file ID of the Minecraft server access list to add these users to");
                try{
                    GoogleSheetProperties userInfo = new GoogleSheetProperties();
                    userInfo.load(new FileInputStream(certFormInfoFile));
                    
                    GoogleSheetProperties fileInfo = new GoogleSheetProperties();
                    fileInfo.load(new FileInputStream(fileListInfoFile));
                    
                    writeOutput(userInfo.toString());
                    writeOutput(fileInfo.toString());
                    
                    List<UserToFileMapping> mappings = getCmdFactory().parseCertificationForm(userInfo, fileInfo, accessListId, false).doExecute();
                    writeOutput("Resolved the following mappings:");
                    mappings.forEach((m)->writeOutput(m.toString()));
                } catch (FileNotFoundException ex) {
                    reportError(ex);
                } catch (IOException ex) {
                    reportError(ex);
                }
            }).openDialog();
        }).openDialog();
    }
    
    public void askDownloadPermissions(){
        new FileSelector("Select a file containing file list properties", FileType.ANY, (File f)->{
            try {
                GoogleSheetProperties info = new GoogleSheetProperties();
                info.load(new FileInputStream(f));
                writeOutput(info.toString());
                String[] ret = getCmdFactory().updateDownloadOptions(info).doExecute();
                writeOutput("The following files were updated:");
                for(String id : ret){
                    writeOutput("* " + id);
                }
            } catch (FileNotFoundException ex) {
                reportError(ex);
            } catch (IOException ex) {
                reportError(ex);
            }
        }).openDialog();
    }
    
    
    public void askCreateDefaultFileListProps(){
        FileSelector.createNewFile("Where do you want to save the default file list properties?", (File newFile)->{
            try {
                new GoogleSheetProperties().save(newFile);
                writeOutput("Created file list properties in " + newFile.getAbsolutePath());
            } catch (FileNotFoundException ex) {
                reportError(ex);
            } catch (IOException ex) {
                reportError(ex);
            }
        });
    }
    public void askCreateDefaultCertFormProps(){
        FileSelector.createNewFile("Where do you want to save the default user list properties?", (File newFile)->{
            try{
                new GoogleSheetProperties().save(newFile);
                writeOutput("Created user list properties in " + newFile.getAbsolutePath());
            } catch (FileNotFoundException ex) {
                reportError(ex);
            } catch (IOException ex) {
                reportError(ex);
            }
        });
    }
    
    public void reportError(Exception ex){
        writeOutput(ex.getMessage());
        for( StackTraceElement frame : ex.getStackTrace()){
            writeOutput(String.format("- %s", frame.toString()));
        }
        
        Logger.logError(ex);
    }
    
    public String ask(String question) {
        return JOptionPane.showInputDialog(question);
    }

    public void writeOutput(String out) {
        frontEnd.addText(out);
    }

    public CommandFactory getCmdFactory() {
        return cmdFact;
    }

}
