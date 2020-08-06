package structs;

import drive.GoogleDriveFileId;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import start.Application;

/**
 * GoogleSheetProperties are locally
 * stored files used to locate remotely
 * stored Google Sheets files.
 * 
 * @author Matt Crow
 */
public class GoogleSheetProperties extends Properties {
    private static final String SPREADSHEET_ID_KEY = "spreadsheetId";
    private static final String SHEET_NAME_KEY = "sheetName";
    
    /**
     * Creates a set of GoogleSheetProperties in the Java VM memory.
     * Note this does not create a property file on the computer.
     * 
     * @param spreadsheetFileId the ID of a Google Spreadsheet file in the 
     * user's Google Drive.
     * 
     * @param sheetName the name of the sheet within the aforementioned Google
     * Spreadsheet workbook that these properties should reference.
     */
    public GoogleSheetProperties(String spreadsheetFileId, String sheetName){
        super();
        setProperty(SPREADSHEET_ID_KEY, spreadsheetFileId); // I don't think this can be a string
        setProperty(SHEET_NAME_KEY, sheetName);
    }
    
    /**
     * Sets the file ID stored in these properties.
     * @param fileId the ID to store.
     */
    public final void setFileId(String fileId){
        setProperty(SPREADSHEET_ID_KEY, fileId);
    }
    
    /**
     * 
     * @return the Google Spreadsheet 
     * ID property stored in this.
     */
    public final String getFileId(){
        // well, I gotta extract the ID somewhere
        return new GoogleDriveFileId(getProperty(SPREADSHEET_ID_KEY)).toString();
    }
    
    /**
     * Sets the sheet name stored in these properties.
     * @param name the sheet name to store.
     */
    public final void setSheetName(String name){
        setProperty(SHEET_NAME_KEY, name);
    }
    
    /**
     * 
     * @return the name of the sheet within
     * the Google Spreadsheet this is
     * referencing.
     */
    public final String getSheetName(){
        return getProperty(SHEET_NAME_KEY);
    }
    
    /**
     * Writes the contents of this to the given output stream.
     * 
     * @param out the stream to write to.
     * @throws IOException if any errors occur when writing.
     */
    public void save(OutputStream out) throws IOException{
        store(out, String.format("This file contains information about a Google Spreadsheet, which %s uses to locate a sheet in that file", Application.APPLICATION_NAME));
    }
    
    /**
     * Writes the contents of this to the given file.
     * 
     * @param f the file to write to.
     * @throws FileNotFoundException if the given file does not exist.
     * @throws IOException if any errors occur when writing.
     */
    public void save(File f) throws FileNotFoundException, IOException{
        FileOutputStream fout = new FileOutputStream(f);
        try{
            save(fout);
        } catch(FileNotFoundException ex){
            fout.close();
            throw ex;
        } catch(IOException ex){
            fout.close();
            throw ex;
        }
    }
    
    @Override
    public String toString(){
        StringBuilder bob = new StringBuilder();
        bob.append("Google Sheet Properties:\n");
        forEach((k, v)->bob.append(String.format("* %s : %s \n", k, v)));
        return bob.toString();
    }
}
