package fileUtils;

/**
 *
 * @author Matt
 */
public class MissingHeaderException extends CsvException {
    private final String missingHeader;
    private final String[] foundHeaders;
    
    public MissingHeaderException(String reqHeader, CsvFile file) {
        super(String.format("Does not contain header %s. Valid headers are [%s]", reqHeader, String.join(", ", file.getHeaders())));
        missingHeader = reqHeader;
        foundHeaders = file.getHeaders();
    }
}
