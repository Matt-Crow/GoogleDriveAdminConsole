package gui;

/**
 *
 * @author Matt
 */
public class OutputPage extends PageContent{
    private final TextScroller textScroller;
    
    public OutputPage(MainPane inPane) {
        super(inPane);
        textScroller = new TextScroller();
        add(textScroller);
    }

    public final void addText(String appendMe){
        textScroller.addText(appendMe);
    }
}
