package gui.pages;

import gui.MainPane;
import gui.components.EditableItemList;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Matt
 */
public class IndividualAccessPage extends PageContent{
    private final EditableItemList users;
    private final EditableItemList files;
    
    public IndividualAccessPage(MainPane inPane) {
        super(inPane);
        setLayout(new BorderLayout());
        
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(1, 2));
        users = new EditableItemList("Emails");
        center.add(users);
        files = new EditableItemList("File IDS");
        center.add(files);
        add(center, BorderLayout.CENTER);
        
        JButton run = new JButton("Give these emails access to these files");
        run.addActionListener((e)->{
            //this.getPaneParent().getBackend().getCmdFactory().giveAccess(mappings);
        });
        add(run, BorderLayout.PAGE_END);
    }

}
