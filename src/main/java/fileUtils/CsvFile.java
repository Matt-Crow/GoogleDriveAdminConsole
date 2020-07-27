package fileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * The CsvFile class is used to store the data from
 * Google Spreadsheets sheets. Later versions may also
 * add support for reading CSV text files into this class
 * as well.
 * 
 * @author Matt Crow
 */
public class CsvFile {
    private final HashMap<String, Integer> columns;
    private final ArrayList<String> headers;
    private final LinkedList<CsvRow> rows;
    
    /**
     * Creates an empty CSV file interface
     */
    public CsvFile(){
        columns = new HashMap<>();
        headers = new ArrayList<>();
        rows = new LinkedList<>();
    }
    
    /**
     * Converts the given List of Lists into a CsvFile.
     * This is used to convert Google Spreadsheets sheets into CsvFiles.
     * 
     * @param nestedLists the sheet content to convert into a CsvFile. Note that the first
     * row of this List (is such a row exists) is interpreted as the CsvFile's headers.
     * 
     * @return the CsvFile representation of the given data.
     * @throws CsvException if the given Lists contain duplicate headers.
     */
    public static final CsvFile from(List<List<Object>> nestedLists) throws CsvException{
        CsvFile ret = new CsvFile();
        if(!nestedLists.isEmpty()){
            nestedLists.get(0).forEach((header) -> {
                ret.addHeader(header.toString());
            });
            for(int rowNum = 1; rowNum < nestedLists.size(); rowNum++){
                ret.addRow(nestedLists.get(rowNum));
            }
        }
        return ret;
    }
    
    public final CsvFile addHeader(String header) throws CsvException{
        header = CsvFile.sanitize(header).toUpperCase();
        if(columns.containsKey(header)){
            throw new CsvException("This already has header " + header + ". Cannot duplicate headers");
        }
        columns.put(header, headers.size());
        headers.add(header);
        // pad out rows
        rows.forEach((row)->{
            row.padValues();
        });
        return this;
    }
    public final CsvFile addRow(List<Object> row){
        rows.add(new CsvRow(this, row.toArray()));
        return this;
    }
    
    public final int getColumnIdx(String header) throws MissingHeaderException{
        header = header.toUpperCase();
        if(!hasHeader(header)){
            throw new MissingHeaderException(header, this);
        }
        return columns.get(header);
    }
    public final boolean hasHeader(String header){
        return columns.containsKey(header.toUpperCase());
    }
    
    public final String[] getHeaders(){
        return headers.toArray(new String[]{});
    }
    
    public final int getHeaderCount(){
        return headers.size();
    }
    
    public final ArrayList<String> getColumn(String header) throws MissingHeaderException{
        header = header.toUpperCase();
        if(!hasHeader(header)){
            throw new MissingHeaderException(header, this);
        }
        ArrayList<String> ret = new ArrayList<>();
        int colIdx = columns.get(header);
        rows.forEach((CsvRow row)->{
            ret.add(row.get(colIdx));
        });
        return ret;
    }
    
    public final CsvFile forEachBodyRow(Consumer<CsvRow> rowConsumer){
        rows.forEach(rowConsumer);
        return this;
    }
    
    /**
     * Ensures the given input is in a valid
     * format.
     * @param input the input to sanitize into
     * a "pleasant" CSV format.
     * 
     * @return the sanitized form of the given input. 
     */
    public static final String sanitize(Object input){
        String ret = "";
        // first, ensure it is not null;
        if(input != null){
            // convert to string
            ret = input.toString();
            // trim
            ret = ret.trim();
            // remove undesirable characters
            while(ret.contains("\n")){
                ret = ret.replace("\n", "");
            }
            // remove exterior quotes
            while(ret.startsWith("\"") && ret.endsWith("\"")){
                ret = ret.substring(1, ret.length() - 1);
            }
            while(ret.startsWith("'") && ret.endsWith("'")){
                ret = ret.substring(1, ret.length() - 1);
            }
        } // returns empty string if null.
        return ret;
    }
    
    @Override
    public String toString(){
        StringBuilder b = new StringBuilder();
        //                                               wrap in quotes
        b.append(headers.stream().map((header)->String.format("\"%s\"", header)).collect(Collectors.joining(", ")));
        forEachBodyRow((row)->{
            b.append("\n").append(row.toString());
        });
        return b.toString();
    }
    
    public static void main(String[] args){
        List<List<Object>> ll = new ArrayList<>();
        ll.add(Arrays.asList("header 1", "header 2", "header 3"));
        ll.add(Arrays.asList(null, "\"Double quotes\"", "'Single Quotes'"));
        ll.add(Arrays.asList("\t\tHi\t", "A\nB", "\n This"));
        CsvFile f = CsvFile.from(ll);
        System.out.println(f.toString());
    }
}
