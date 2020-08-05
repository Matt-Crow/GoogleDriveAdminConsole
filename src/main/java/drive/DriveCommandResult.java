package drive;

/**
 *
 * @author Matt
 */
public class DriveCommandResult<T> {
    private final String cmdName;
    private final T result;
    private final String[] msgs;
    private final Exception[] encounteredErrors;
    private boolean fatalError;
    
    public DriveCommandResult(String commandName, T resultObject, String[] outputMessages, Exception[] problemsEncountered){
        cmdName = commandName;
        result = resultObject;
        msgs = outputMessages;
        encounteredErrors = problemsEncountered;
        fatalError = false;
    }
    
    public final void setFatalError(boolean b){
        fatalError = b;
    }
    public final boolean hasFailed(){
        return fatalError;
    }
    
    public final T getResult(){
        return result;
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
