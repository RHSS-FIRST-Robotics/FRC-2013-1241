/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.utilities;

import com.sun.squawk.microedition.io.FileConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.io.Connector;

/**
 *
 * @author Team 1241
 */
public class TextLogger {
    
    private static DataOutputStream kDos;
    private static FileConnection kConnection;

    /**
     * Creates the text log through the class constructor. 
     * @param filePath (where to create the file and what to call it)
     */
    public TextLogger(String filePath) {
        try {
            if (kDos != null) {
                kDos.close();
                kDos = null;
            }
            if (kConnection != null) {
                kConnection.close();
                kConnection = null;
            }

            kConnection = (FileConnection) Connector.open(filePath, Connector.READ_WRITE);
            if (kConnection.exists()) {
                kConnection.delete();
                kConnection.close();
                kConnection = (FileConnection)Connector.open(filePath, Connector.READ_WRITE);
            }

            kConnection.create();

            kDos = kConnection.openDataOutputStream();
        } catch (IOException e) {
            System.out.println("Unable to create file log.");
        }
    }
    
    /**
     * Writes the specified string (plus a newline) into the log file.
     *
     * @param output The string to be written.
     */
    public static synchronized void write(String output){
        String kOutput = output + "\n";
        try {
            if (kDos != null)
                kDos.write(kOutput.getBytes(), 0, kOutput.getBytes().length);
        } catch (IOException e){
            System.out.println("Unable to write to log.");
        }
    }

    /**
     * Closes the file temporarily for other file writing operations
     */
    public static synchronized void pause(){
        try {
            kConnection.close();
            kDos.close();
        } catch (IOException e){
            write(e.getMessage());
        }
    }

    /**
     * Reopens the last file for resumed logging functionality
     */
    public static synchronized void resume(){
        try {
            kConnection.create();
            kDos = kConnection.openDataOutputStream();
        } catch (IOException e){
            write(e.getMessage());
        }
    }
    
}
