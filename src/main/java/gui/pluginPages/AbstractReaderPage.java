package gui.pluginPages;

import gui.MainPane;
import gui.components.GoogleSheetPropertyFileChooser;
import gui.pages.AbstractFormPage;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
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
        JPanel center = new JPanel();
        center.setLayout(new FlowLayout(FlowLayout.CENTER));
        add(center, BorderLayout.CENTER);
        
        chooser = new GoogleSheetPropertyFileChooser(propChooserHeader, propChooserPopupText);
        center.add(chooser);
        
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
