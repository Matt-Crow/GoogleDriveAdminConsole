package sysUtils;

import java.util.LinkedList;

/**
 *
 * @author Matt Crow
 */
public class Logger {
    private static final StringBuilder MSG_LOG = new StringBuilder();
    private static final LinkedList<MessageListener> MESSAGE_LISTENERS = new LinkedList<>();
    private static final LinkedList<ErrorListener> ERROR_LISTENERS = new LinkedList<>();
    private static boolean ERROR_FLAG = false;
    
    private Logger(){
        throw new ExceptionInInitializerError("Logger is a static class: It should not be instantiated");
    }
    
    /**
     * Registers the given object as a message listener so it will be notified
     * when this logs a message.
     * 
     * @param listener the object to receive string messages from this. 
     */
    public static final void addMessageListener(MessageListener listener){
        MESSAGE_LISTENERS.add(listener);
    }
    
    /**
     * Registers the given object as an error listener so it will be
     * notified when this logs an exception or error message.
     * 
     * @param listener the object to receive error messages from this.
     */
    public static final void addErrorListener(ErrorListener listener){
        ERROR_LISTENERS.add(listener);
    }
    
    public static final void log(String msg){
        String source = getLogCaller();
        String loggedMsg = String.format("[%s] %s\n", source, msg);
        MSG_LOG.append(loggedMsg);
        
        if(MESSAGE_LISTENERS.isEmpty()){
            System.out.print(loggedMsg);
        } else {
            MESSAGE_LISTENERS.forEach((listener)->listener.messageLogged(msg));
        }
    }
    
    private static String getLogCaller(){
        int backStep = 3; // Top of the stack is getStackTrace(), [1] is this, [2] is the logging method, so [3] is the method calling the logging method
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        /*
        for(StackTraceElement e : stack){
            System.out.println(e);
        }
        */
        String source = (stack.length <= backStep) ? "UNKNOWN" : stack[backStep].toString();
        return source;
    }
    
    public static void main(String[] args){
        log("Hello?");
    }
}
