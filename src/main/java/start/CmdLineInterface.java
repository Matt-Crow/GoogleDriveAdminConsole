package start;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Once I have a better idea of
 * what sort of interfaces we'll
 * want for interacting with the 
 * program, I'll be able to pull
 * some of this into a superclass.
 * @author Matt Crow
 */
public class CmdLineInterface {
    //private Scanner inputReader;
    public CmdLineInterface(){
        //inputReader = null;
    }
    
    public void run() throws IOException{
        //inputReader = new Scanner(System.in);
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        String textInput = "";
        int choice = 0;
        while(choice != -1){
            System.out.println("Enter -1 to quit");
            textInput = read.readLine();
            try{
                choice = Integer.parseInt(textInput);
                System.out.println("You entered " + choice);
            } catch(NumberFormatException e){
                System.out.printf("Not a valid number: %s\n", textInput);
            }
            //inputReader.nextLine();
            
        }
    }
}
