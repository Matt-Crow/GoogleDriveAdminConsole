package gui.pages;

import fileUtils.FileList;
import gui.MainPane;
import gui.components.EditableStringList;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JPanel;
import structs.SimpleFileInfo;
import structs.SimpleUserInfo;
import structs.UserToFileMapping;

/**
 *
 * @author Matt
 */
public class IndividualAccessPage extends AbstractFormPage{
    private final EditableStringList users;
    private final EditableStringList files;
    
    public IndividualAccessPage(MainPane inPane) {
        super(inPane);
        setLayout(new BorderLayout());
        
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(1, 2));
        users = new EditableStringList("Emails");
        center.add(users);
        files = new EditableStringList("File IDS");
        center.add(files);
        add(center, BorderLayout.CENTER);
        
        JButton run = new JButton("Give these emails access to these files");
        run.addActionListener((e)->{
            submit();
        });
        add(run, BorderLayout.PAGE_END);
    }
    
    @Override
    public void doSubmit() throws IOException{
        MainPane parent = getPaneParent();
        
        String[] emailStrs = users.getItems();
        String[] fileIdStrs = files.getItems();
        
        List<SimpleUserInfo> userInfo = Arrays.stream(emailStrs).map((str)->new SimpleUserInfo(str)).collect(Collectors.toList());
        List<SimpleFileInfo> fileInfo = Arrays.stream(fileIdStrs).map((str)->new SimpleFileInfo(str)).collect(Collectors.toList());
        FileList fileList = new FileList(fileInfo);
        List<UserToFileMapping> mappings = UserToFileMapping.constructUserFileList(userInfo, fileList);
        
        parent.getBackend().getCmdFactory().giveAccess(mappings).doExecute();
        parent.addText("Successfully gave acccess to the following files:");
        mappings.forEach((mapping)->{
            parent.addText(String.format("* %s", mapping.toString()));
        });
    }

}
