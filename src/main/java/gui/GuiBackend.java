package gui;

import drive.commands.CommandFactory;
import javax.swing.JOptionPane;
import start.ServiceAccess;
import start.AbstractUserInterface;

/**
 *
 * @author Matt
 */
public class GuiBackend implements AbstractUserInterface{
    private final CommandFactory cmdFact;
    private final MainPane frontEnd;
    
    public GuiBackend(MainPane pane){
        frontEnd = pane;
        cmdFact = new CommandFactory(ServiceAccess.getInstance());
    }
    
    @Override
    public String ask(String question) {
        return JOptionPane.showInputDialog(question);
    }

    @Override
    public void writeOutput(String out) {
        frontEnd.addText(out);
    }

    @Override
    public CommandFactory getCmdFactory() {
        return cmdFact;
    }

}
