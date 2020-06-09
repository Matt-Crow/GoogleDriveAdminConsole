package sheets;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Matt
 */
public class CsvParser {
    public static final String[] getColumn(List<List<Object>> data, String header){
        ArrayList<String> cellValues = new ArrayList<>();
        List<String> headers = data.get(0)
            .stream()
            .sequential()
            .map((columnHeader)->columnHeader.toString().toUpperCase())
            .sequential()
            .collect(Collectors.toList());
        
        int idx = headers.indexOf(header.toUpperCase());
        if(idx == -1){
            throw new RuntimeException("Data does not contain header " + header);
        }
        
        String cellData;
        for(int i = 1; i < data.size(); i++){
            if(data.get(i).size() <= idx){
                cellValues.add("");
            } else {
                cellValues.add(data.get(i).get(idx).toString());
            }
        }
        
        return cellValues.toArray(new String[cellValues.size()]);
    }
}
