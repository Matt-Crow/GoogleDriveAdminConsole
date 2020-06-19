package gui.components;

import fileUtils.FileSelector;
import fileUtils.FileType;
import gui.TextScroller;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import structs.CertificationFormInfo;

/**
 *
 * @author Matt
 */
public class CertFormPropSelector extends JComponent{
    private CertificationFormInfo selectedInfo;
    private final TextScroller output;
    private final JButton chooseFile;
    
    public CertFormPropSelector(){
        super();
        selectedInfo = new CertificationFormInfo();
        
        setLayout(new BorderLayout());
        
        add(new JLabel("Certification Form Info"), BorderLayout.PAGE_START);
        
        output = new TextScroller();
        add(output, BorderLayout.CENTER);
        
        chooseFile = new JButton("Select File");
        chooseFile.addActionListener((e)->{
            new FileSelector("Select a file containing certification form properties", FileType.ANY, (File certFormInfoFile)->{
                try{
                    selectedInfo.clear();
                    selectedInfo.load(new FileInputStream(certFormInfoFile));
                    output.clear();
                    output.addText("Contents of " + certFormInfoFile + ":");
                    output.addText(selectedInfo.toString());
                } catch (FileNotFoundException ex) {
                    output.addText("Failed to locate file " + certFormInfoFile.getAbsolutePath());
                } catch (IOException ex) {
                    output.addText("Oops! " + ex.getLocalizedMessage());
                }
            }).openDialog();
        });
        add(chooseFile, BorderLayout.PAGE_END);
    }
    
    public final CertificationFormInfo getSelectedInfo(){
        return selectedInfo;
    }
}
