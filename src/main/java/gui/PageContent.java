package gui;

import javax.swing.JPanel;

/**
 *
 * @author Matt Crow
 */
public class PageContent extends JPanel{
    private final MainPane parent;
    
    public PageContent(MainPane inPane){
        parent = inPane;
    }
    
    public final MainPane getPaneParent(){
        return parent;
    }
}
