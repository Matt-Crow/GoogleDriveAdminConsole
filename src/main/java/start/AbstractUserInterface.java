package start;

import drive.commands.CommandFactory;
import fileUtils.FileSelector;
import fileUtils.FileType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import structs.CertificationFormInfo;
import structs.DetailedFileInfo;
import structs.DetailedUserInfo;
import structs.FileListInfo;

/**
 *
 * @author Matt
 */
public abstract interface AbstractUserInterface {
    // not sure I like the idea of having one of these for each command
    public default void askAddToAccessList(){
        String[] mcAccts = ask("Enter a list of Minecraft users to add to an access list, seperated by commas: ").split(",");
        String accListId = ask("Enter the ID of the access list to add them to: ");
        try {
            String[] newUsers = getCmdFactory().addToAccessListCmd(accListId, mcAccts).execute();
            writeOutput("New users:");
            for(String user : newUsers){
                writeOutput(String.format("* %s", user));
            }
        } catch (IOException ex) {
            reportError(ex);
        }
    }
    public default void askCreateAccessList(){
        String parentId = ask("Enter the ID of the Google Drive folder to add this access list to: ");
        try{
            String newFileId = getCmdFactory().createAccessListCmd(parentId).execute().getId();
            writeOutput(String.format("Successfully created the access list in %s. It has an id of %s. Make sure to update the Science Report to reference this new file", parentId, newFileId));
        } catch (IOException ex) {
            reportError(ex);
        }
    }
    public default void askDisplayAccessList(){
        String fileId = ask("Enter the ID of an access list to view: ");
        try{
            String[] mcUsers = getCmdFactory().getAccessListCmd(fileId).execute();
            writeOutput("Users are:");
            for(String user : mcUsers){
                writeOutput(String.format("* %s", user));
            }
        } catch (IOException ex) {
            reportError(ex);
        }
    }
    public default void askClearAccessList(){
        String fileId = ask("Enter the ID of the access list to clear all users from: ");
        try{
            getCmdFactory().setAccessListCmd(fileId, new String[0]).execute();
            writeOutput(String.format("%s has been cleared.", fileId));
        } catch(IOException ex){
            reportError(ex);
        }
    }
    
    public default void askReadFileList(){
        new FileSelector("Select a file containing file list properties", FileType.ANY, (File f)->{
            try {
                FileListInfo info = new FileListInfo();
                info.load(new FileInputStream(f));
                writeOutput(info.toString());
                ArrayList<DetailedFileInfo> files = getCmdFactory().readFileList(info).execute();
                writeOutput("Contains the following files:");
                files.forEach((i)->writeOutput("* " + i.toString()));
            } catch (FileNotFoundException ex) {
                reportError(ex);
            } catch (IOException ex) {
                reportError(ex);
            }
        }).openDialog();
    }
    public default void askReadCertForm(){
        new FileSelector("Select a file containing certification form properties", FileType.ANY, (File f)->{
            try{
                CertificationFormInfo info = new CertificationFormInfo();
                info.load(new FileInputStream(f));
                writeOutput(info.toString());
                ArrayList<DetailedUserInfo> users = getCmdFactory().readCertForm(info).execute();
                writeOutput("Contains the following users:");
                users.forEach((i)->writeOutput(i.toString()));
            } catch (FileNotFoundException ex) {
                reportError(ex);
            } catch (IOException ex) {
                reportError(ex);
            }
        }).openDialog();
    }
    
    public default void askDownloadPermissions(){
        new FileSelector("Select a file containing file list properties", FileType.ANY, (File f)->{
            try {
                FileListInfo info = new FileListInfo();
                info.load(new FileInputStream(f));
                writeOutput(info.toString());
                String[] ret = getCmdFactory().updateDownloadOptions(info).execute();
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
    
    
    public default void askCreateDefaultFileListProps(){
        FileSelector.createNewFile("Where do you want to save the default file list properties?", (File newFile)->{
            try {
                new FileListInfo().save(newFile);
                writeOutput("Created file list properties in " + newFile.getAbsolutePath());
            } catch (FileNotFoundException ex) {
                reportError(ex);
            } catch (IOException ex) {
                reportError(ex);
            }
        });
    }
    public default void askCreateDefaultCertFormProps(){
        FileSelector.createNewFile("Where do you want to save the default user list properties?", (File newFile)->{
            try{
                new CertificationFormInfo().save(newFile);
                writeOutput("Created user list properties in " + newFile.getAbsolutePath());
            } catch (FileNotFoundException ex) {
                reportError(ex);
            } catch (IOException ex) {
                reportError(ex);
            }
        });
    }
    
    public default void reportError(Exception ex){
        writeOutput(ex.getMessage());
        for( StackTraceElement frame : ex.getStackTrace()){
            writeOutput(String.format("- %s", frame.toString()));
        }
    }
    public abstract String ask(String question);
    public abstract void writeOutput(String out);
    public abstract CommandFactory getCmdFactory();
}
