package fileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Matt
 */
public class CsvFile {
    private final HashMap<String, Integer> columns;
    private final ArrayList<String> headers;
    private final LinkedList<String[]> rows;
    
    public CsvFile(){
        columns = new HashMap<>();
        headers = new ArrayList<>();
        rows = new LinkedList<>();
    }
}
