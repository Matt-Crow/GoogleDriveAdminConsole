package gui.pages;

import gui.MainPane;
import gui.components.TextScroller;
import java.awt.GridLayout;
import sysUtils.Logger;

/**
 *
 * @author Matt
 */
public class OutputPage extends PageContent{
    private final TextScroller textScroller;
    
    public OutputPage(MainPane inPane) {
        super(inPane);
        setLayout(new GridLayout(1, 1));
        textScroller = new TextScroller();
        add(textScroller);
        Logger.addMessageListener(textScroller);
    }

    public final void addText(String appendMe){
        textScroller.addText(appendMe);
    }
}
