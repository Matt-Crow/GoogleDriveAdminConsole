package gui.pages;

import gui.MainPane;

/**
 *
 * @author Matt
 */
public abstract class FormSubmit extends PageContent{

    public FormSubmit(MainPane inPane) {
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
    
    public void submit(){
        PageContent self = (PageContent)this;
        MainPane parent = self.getPaneParent();
        parent.switchToTab(PageName.OUTPUT);
        parent.setTabSwitchingEnabled(false);
        submitAsync();
        parent.setTabSwitchingEnabled(true);
    }
    
    public abstract void doSubmit() throws Exception;
    
    
    
    
}
