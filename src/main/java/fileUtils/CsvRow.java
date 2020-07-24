package fileUtils;

import java.util.Arrays;
import java.util.List;
import sysUtils.Logger;

/**
 *
 * @author Matt
 */
public class CsvRow {
    private final CsvFile parent;
    private final List<String> values;
    
    public CsvRow(CsvFile file){
        parent = file;
        values = Arrays.asList();
        padValues();
    }
    
    /**
     * Adds blank cells to this row
     * until it is as wide as its parent.
     */
    public void padValues(){
        int blanksToAdd = parent.getHeaderCount() - values.size();
        for(int i = 0; i < blanksToAdd; i++){
            values.add("");
        }
    }
    
    public void set(String columnHeader, String value){
        int idx = parent.getColumnIdx(columnHeader); // throws an exception if it doesn't exist
        
        if(idx >= values.size()){
            padValues();
        }
        values.set(idx, value);
    }
    
    public String get(String columnHeader){
        int idx = parent.getColumnIdx(columnHeader);
        
        if(idx >= values.size()){
            padValues();
        }
        return values.get(idx);
    }
    
    /**
     * Returns the value in the given column, or defaultValue if this CsvRow
     * does not contain the given column.
     * 
     * @param columnHeader
     * @param defaultValue
     * @return 
     */
    public String getOrDefault(String columnHeader, String defaultValue){
        String ret = defaultValue;
        try{
            int idx = parent.getColumnIdx(columnHeader);
            ret = get(columnHeader);
        } catch(MissingHeaderException ex){
            Logger.logError(ex);
        }
        return ret;
    }
}
