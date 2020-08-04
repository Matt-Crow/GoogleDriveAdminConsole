package drive.commands.utils;

/**
 *
 * @author Matt
 */
public class DriveCommandResult<T> {
    private final String cmdName;
    private final T result;
    private final String[] msgs;
    private final Exception[] encounteredErrors;
    
    public DriveCommandResult(String commandName, T resultObject, String[] outputMessages, Exception[] problemsEncountered){
        cmdName = commandName;
        result = resultObject;
        msgs = outputMessages;
        encounteredErrors = problemsEncountered;
    }
    
    @Override
    public String toString(){
        StringBuilder b = new StringBuilder();
        b.append(String.format("=== RESULTS OF %s ===", cmdName));
        for(String msg : msgs){
            b.append(String.format("\n * %s", msg));
        }
        b.append("\n=== * ===");
        b.append(String.format("\nProcess yielded this:\n%s", result.toString()));
        if(encounteredErrors.length == 0){
            b.append("\nEncountered no errors :)");
        } else {
            b.append("\nEncountered the following errors:");
            for(Exception ex : encounteredErrors){
                b.append(String.format("\n!%s", ex.toString()));
            }
        }
        return b.toString();
    }
}
