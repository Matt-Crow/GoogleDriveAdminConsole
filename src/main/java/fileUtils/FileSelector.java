package fileUtils;

import java.io.File;
import java.util.function.Consumer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import sysUtils.Logger;

/**
 *
 * @author Matt
 */
public class FileSelector {
    private final JFileChooser chooser;
    private final Consumer<File> onChoose;
    private final Consumer<Exception> onFail;
    
    public FileSelector(String text, FileType type, Consumer<File> onSelect, Consumer<Exception> eatBadNews){
        chooser = new JFileChooser();
        chooser.setDialogTitle(text);
        chooser.setFileSelectionMode((type == FileType.DIR) ? JFileChooser.DIRECTORIES_ONLY : JFileChooser.FILES_ONLY);
        if(type != FileType.ANY){
            chooser.setFileFilter(new FileNameExtensionFilter(type.getDisplayText(), type.getExtensions()));
        }
        onChoose = onSelect;
        onFail = eatBadNews;
    }
    public FileSelector(String text, FileType type, Consumer<File> onSelect){
        this(text, type, onSelect, (Exception ex)->Logger.logError(ex));
    }
    
    public void openDialog(){
        if(chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION){
            try {
                onChoose.accept(chooser.getSelectedFile());
            } catch(Exception ex){
                onFail.accept(ex);
            }
        }
    }
    
    public static void createNewFile(String text, Consumer<File> onSelect){
        new FileSelector(text, FileType.DIR, (File root)->{
            String fileName = JOptionPane.showInputDialog("Enter a name (with extension) for this new file:");
            if(fileName == null || fileName.isEmpty()){
                fileName = "name-not-set.txt";
            }
            File newFile = new File(root.getAbsolutePath() + File.separator + fileName);
            onSelect.accept(newFile);
        }).openDialog();
    }
}
