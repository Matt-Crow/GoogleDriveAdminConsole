package fileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * This class is used to manage
 * file reading / writing on the
 * local file system, or streams
 * from the GoogleDrive API.
 * 
 * @author Matt Crow
 */
public class FileReadWriteUtil {
    
    /**
     * Reads a stream, and returns its contents as a string.
     * @param in the InputStream to read.
     * @return the contents of the given stream
     * @throws IOException if an error occurs while reading the stream
     */
    public static String readStream(InputStream in) throws IOException{
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            while(reader.ready()){
                sb.append(reader.readLine()).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
    
    /**
     * Reads the contents of a given text file, and returns them as a string.
     * @param f the file to read
     * @return the text contents of the given file.
     * @throws FileNotFoundException if the given file does not exist on the local file system.
     * @throws IOException if an error occurs when reading the given file.
     */
    public static String readFile(File f) throws FileNotFoundException, IOException{
        return readStream(new FileInputStream(f));
    }
    
    /**
     * Writes the given string to an output stream.
     * @param out the stream to write to.
     * @param s the string to write
     * @throws IOException if any errors occur when writing to the stream.
     */
    public static void writeStream(OutputStream out, String s) throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {
            writer.write(s);
            writer.flush();
        }
    }
    
    /**
     * Writes the given stream to the given file
     * @param f the file to write to
     * @param s the string to write
     * @throws FileNotFoundException if the given file does not exist on the user's local file system
     * @throws IOException if any errors occur when writing to the file.
     */
    public static void writeFile(File f, String s) throws FileNotFoundException, IOException{
        writeStream(new FileOutputStream(f), s);
    }
}
