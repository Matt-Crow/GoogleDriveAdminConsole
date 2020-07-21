package gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Matt
 */
public class TextInputBox extends JComponent {
    private final JTextField input;
    
    public TextInputBox(String propertyName, String helpText){
        super();
        setLayout(new BorderLayout());
        
        add(new JLabel(propertyName), BorderLayout.PAGE_START);
        input = new JTextField();
        input.setToolTipText(helpText);
        add(input, BorderLayout.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
    }
    
    public final String getInput(){
        return input.getText();
    }
}
