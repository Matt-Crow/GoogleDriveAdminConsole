package start;

import drive.commands.CommandFactory;
import java.io.IOException;

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
