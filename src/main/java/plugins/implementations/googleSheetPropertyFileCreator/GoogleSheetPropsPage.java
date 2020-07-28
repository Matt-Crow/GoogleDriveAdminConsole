package plugins.implementations.googleSheetPropertyFileCreator;

import gui.MainPane;
import gui.components.GoogleSheetsPropertyFileCreator;
import gui.pages.AbstractFormPage;

/**
 *
 * @author Matt
 */
public class GoogleSheetPropsPage extends AbstractFormPage {
    public GoogleSheetPropsPage(MainPane inPane) {
        super(inPane);
        add(new GoogleSheetsPropertyFileCreator("New Property File"));
    }

    @Override
    public void doSubmit() throws Exception {
        // do nothing: The file creator handles this
    }

}
