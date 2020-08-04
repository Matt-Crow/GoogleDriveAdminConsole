package sysUtils;

/**
 * The ErrorListener should be 
 * implemented by any class that
 * needs to know when an error occurs.
 * Classes implementing this interface
 * must add themselves as an error listener
 * to the Logger.
 * 
 * @author Matt Crow
 */
public interface ErrorListener {
    /**
     * Fired whenever an error message is logged in
     * the Logger this is attached to.
     * @param errMsg the error message just logged. 
     * Note that this may contain a newline character 
     * at the end.
     */
    public abstract void errorLogged(String errMsg);
    
    /**
     * Fired whenever the error log logs an Exception.
     * @param ex the Exception logged by the logger.
     */
    public abstract void errorLogged(Exception ex);
    
    /**
     * Fired whenever the Logger this is 
     * attached to clears its error log.
     * May wind up not being used.
     */
    public abstract void errorLogCleared();
}
