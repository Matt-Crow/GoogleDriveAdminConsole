package gui.pages;

import gui.MainPane;

/**
 *
 * @author Matt
 */
public abstract class AbstractFormPage extends PageContent{

    public AbstractFormPage(MainPane inPane) {
        super(inPane);
    }
    
    private Thread submitAsync(){
        Thread t = new Thread(){
            @Override
            public void run(){
                try{
                    doSubmit();
                } catch (Exception ex) {
                    getPaneParent().getBackend().reportError(ex);
                }
            }
        };
        t.start();
        return t;
    }
    
    public final void submit(){
        MainPane parent = getPaneParent();
        parent.switchToTab(PageName.OUTPUT);
        parent.setTabSwitchingEnabled(false);
        submitAsync();
        parent.setTabSwitchingEnabled(true); // may need to move this
    }
    
    public abstract void doSubmit() throws Exception;
    
    
    
    
}
