package fileUtils;

import java.util.ArrayList;
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
        values = new ArrayList<>();
        padValues();
    }
    
    public CsvRow(CsvFile file, Object[] vals){
        this(file);
        for(int i = 0; i < vals.length; i++){
            values.set(i, CsvFile.sanitize(vals[i]));
        }
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
        values.set(idx, CsvFile.sanitize(value));
    }
    
    
    public String get(int idx){
        if(idx >= values.size()){
            padValues();
        }
        return values.get(idx);
    }
    
    public String get(String columnHeader){
        return get(parent.getColumnIdx(columnHeader));
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
            ret = get(columnHeader);
        } catch(MissingHeaderException ex){
            Logger.logError(ex);
        }
        return ret;
    }
    
    @Override
    public String toString(){
        return String.join(", ", values);
    }
}
