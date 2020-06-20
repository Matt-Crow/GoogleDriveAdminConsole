package gui.components;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author Matt
 */
public class ListItem extends JComponent{
    private final EditableItemList inList;
    private final JTextField input;
    public ListItem(EditableItemList list){
        super();
        inList = list;
        
        setLayout(new BorderLayout());
        
        input = new JTextField();
        add(input, BorderLayout.CENTER);
        
        JButton del = new JButton("Remove");
        del.addActionListener((e)->{
            inList.removeItem(this);
        });
        add(del, BorderLayout.LINE_START);
    }
    
    public final String getContent(){
        return input.getText();
    }
}
