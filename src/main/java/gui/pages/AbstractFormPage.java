package gui.pages;

import gui.MainPane;
import sysUtils.Logger;

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
                    Logger.logError(ex);
                }
                getPaneParent().setTabSwitchingEnabled(true);
            }
        };
        t.start();
        return t;
    }
    
    public final void submit(){
        MainPane parent = getPaneParent();
        parent.switchToOutputTab();
        parent.setTabSwitchingEnabled(false);
        submitAsync();
    }
    
    public abstract void doSubmit() throws Exception;
    
    
    
    
}
