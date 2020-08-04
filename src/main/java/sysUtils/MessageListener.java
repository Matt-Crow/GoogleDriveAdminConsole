package sysUtils;

/**
 * The MessageListener should be
 * implemented by any class that
 * needs to be notified whenever
 * the Logger logs a message. 
 * Classes implementing this interface 
 * must add themselves as a message 
 * listener to the Logger.
 * 
 * @author Matt Crow
 */
public interface MessageListener {
    /**
     * Fired whenever the Logger logs a message.
     * Note that this includes both regular messages
     * and errors.
     * 
     * @param message the message logged by the logger.
     */
    public abstract void messageLogged(String message);
}
