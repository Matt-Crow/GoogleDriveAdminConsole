package gui.components;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashSet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Matt
 */
public class EditableStringList extends JComponent{
    private final JPanel itemList;
    private final JScrollPane scroll;
    private final GridBagConstraints gbc;
    private final HashSet<StringListItem> items;
    public EditableStringList(String listTitle){
        super();
        
        items = new HashSet<>();
        
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
            addItem();
        });
        add(addAnItem, BorderLayout.PAGE_END);
    }
    
    public void removeItem(StringListItem i){
        items.remove(i);
        itemList.remove(i);
        itemList.revalidate();
        itemList.repaint();
    }
    private void addItem(){
        StringListItem newItem = new StringListItem(this);
        items.add(newItem);
        itemList.add(newItem, gbc);
        itemList.revalidate();
        itemList.repaint();
    }
    
    public final String[] getItems(){
        return items.stream().map((listItem)->listItem.getContent()).toArray((size)->new String[size]);
    }
}
