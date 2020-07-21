package gui.pluginPages;

import gui.MainPane;
import gui.components.GoogleSheetPropertyFileChooser;
import gui.pages.AbstractFormPage;
import java.awt.BorderLayout;
import javax.swing.JButton;
import structs.GoogleSheetProperties;

/**
 *
 * @author Matt
 */
public abstract class AbstractReaderPage extends AbstractFormPage {
    private final GoogleSheetPropertyFileChooser chooser;
    
    public AbstractReaderPage(MainPane inPane, String propChooserHeader, String propChooserPopupText) {
        super(inPane);
        setLayout(new BorderLayout());
        chooser = new GoogleSheetPropertyFileChooser(propChooserHeader, propChooserPopupText);
        add(chooser, BorderLayout.CENTER);
        
        JButton run = new JButton("Run");
        run.addActionListener((e)->{
            submit();
        });
        add(run, BorderLayout.PAGE_END);
    }

    public abstract void parse(GoogleSheetProperties props) throws Exception;
    
    @Override
    public final void doSubmit() throws Exception {
        parse(
            (GoogleSheetProperties) chooser.getSelectedProperties()
        );
    }

}
