package gui.components;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Matt
 */
public class EditableItemList extends JComponent{
    private final JPanel itemList;
    private final JScrollPane scroll;
    private final GridBagConstraints gbc;
    
    public EditableItemList(){
        super();
        setLayout(new BorderLayout());
        
        // the list of items
        itemList = new JPanel();
        itemList.setLayout(new GridBagLayout());
        
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        scroll = new JScrollPane();
        add(scroll, BorderLayout.CENTER);
    }
}
