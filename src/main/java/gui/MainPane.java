package gui;

import java.awt.BorderLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Matt
 */
public class MainPane extends JPanel{
    private final JScrollPane scroll;
    private final JTextArea text;
    private final JMenuBar menu;
    public MainPane(){
        super();
        setLayout(new BorderLayout());
        
        text = new JTextArea();
        text.setWrapStyleWord(true);
        text.setLineWrap(true);
        text.setEditable(false);
        
        scroll = new JScrollPane(text);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);
        
        menu = new JMenuBar();
        JMenu servAcc = new JMenu("Server access");
        JMenuItem addToAcc = new JMenuItem("Add a Minecraft username to an access list");
        addToAcc.addActionListener((e)->{
            System.out.println("out");
        });
        servAcc.add(addToAcc);
        menu.add(servAcc);
        add(menu, BorderLayout.PAGE_START);
    }
}
