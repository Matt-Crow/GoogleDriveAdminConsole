package gui.components;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Matt
 */
public class EditableItemList extends JComponent{
    private final JPanel itemList;
    private final JScrollPane scroll;
    private final GridBagConstraints gbc;
    private final ArrayList<JTextField> fields; // better data struct for this
    
    public EditableItemList(String listTitle){
        super();
        
        fields = new ArrayList<>();
        
        setLayout(new BorderLayout());
        
        add(new JLabel(listTitle), BorderLayout.PAGE_START);
        // the list of items
        itemList = new JPanel();
        itemList.setLayout(new GridBagLayout());
        
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        
        scroll = new JScrollPane(itemList);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);
        
        JButton addAnItem = new JButton("Add a new item");
        addAnItem.addActionListener((e)->{
            addItem(new JTextField());
        });
        add(addAnItem, BorderLayout.PAGE_END);
    }
    
    private void removeItem(JComponent j){
        itemList.remove(j);
        itemList.revalidate();
        itemList.repaint();
    }
    private void addItem(JComponent j){
        JPanel newItem = new JPanel();
        newItem.setLayout(new BorderLayout());
        newItem.add(j, BorderLayout.CENTER);
        JButton del = new JButton("Remove");
        del.addActionListener((e)->{
            removeItem(newItem);
        });
        newItem.add(del, BorderLayout.LINE_START);
        itemList.add(newItem, gbc);
        itemList.revalidate();
        itemList.repaint();
    }
}
