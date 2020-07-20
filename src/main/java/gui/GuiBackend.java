package gui;

import drive.commands.utils.CommandFactory;
import fileUtils.FileSelector;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JOptionPane;
import start.GoogleDriveService;
import structs.GoogleSheetProperties;
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
    /*
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
    */
    
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
