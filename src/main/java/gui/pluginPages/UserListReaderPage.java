package gui.pluginPages;

import gui.pluginPages.AbstractReaderPage;
import fileUtils.UserList;
import gui.MainPane;
import structs.GoogleSheetProperties;

/**
 *
 * @author Matt
 */
public class UserListReaderPage extends AbstractReaderPage {

    public UserListReaderPage(MainPane inPane) {
        super(inPane, "User List Properties", "select the user sheet properties");
    }

    @Override
    public void parse(GoogleSheetProperties props) throws Exception {
        MainPane parent = getPaneParent();
        UserList users = parent.getBackend().getCmdFactory().readUserList(
            props
        ).doExecute();
        parent.addText("contains the following users:");
        users.forEach((f)->parent.addText(f.toString()));}

}
