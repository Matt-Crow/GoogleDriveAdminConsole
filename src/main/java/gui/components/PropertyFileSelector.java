package gui.components;

import fileUtils.FileSelector;
import fileUtils.FileType;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author Matt
 */
public class PropertyFileSelector extends JComponent{
    private final Properties selectedProperties;
    private final TextScroller output;
    private final JButton chooseFile;
    
    public PropertyFileSelector(String header, String selectText, Properties defaultProps){
        super();
        
        selectedProperties = defaultProps;
        
        setLayout(new BorderLayout());
        
        add(new JLabel(header), BorderLayout.PAGE_START);
        
        output = new TextScroller();
        add(output, BorderLayout.CENTER);
        
        chooseFile = new JButton("Select File");
        chooseFile.addActionListener((e)->{
            new FileSelector(selectText, FileType.ANY, (File f)->{
                try{
                    selectedProperties.clear();
                    selectedProperties.load(new FileInputStream(f));
                    output.clear();
                    output.addText(String.format("Contents of %s :", f.getAbsolutePath()));
                    output.addText(selectedProperties.toString());
                } catch (FileNotFoundException ex) {
                    output.addText("Failed to locate file " + f.getAbsolutePath());
                } catch (IOException ex) {
                    output.addText("Oops! " + ex.getLocalizedMessage());
                }
            }).openDialog();
        });
        add(chooseFile, BorderLayout.PAGE_END);
    }
    
    public final Properties getSelectedProperties(){
        return selectedProperties;
    }
}
