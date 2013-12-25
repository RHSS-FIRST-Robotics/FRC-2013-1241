package theory6.utilities;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.IOException;
import javax.microedition.io.Connector;
import com.sun.squawk.microedition.io.FileConnection;

/**
 *
 * @author Team 1241
 */
public class CSVLogger {

    private static OutputStream OS;
    private static PrintStream printStream;
    private static FileConnection kConnection;
    
    /**
     * Creates the log through the class constructor.
     * @param String filePath (the path of the file to be written to)
     */
    public CSVLogger(String filePath) {
        
        try 
        {
            if (OS != null) {
                OS.close();
                OS = null;
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

            OS = kConnection.openOutputStream();
            
            printStream = new PrintStream(OS);
        } 
        
        catch (IOException e) 
        {
            System.out.println("Unable to create file log.");
        }
    }
    
    /*
     * Writes the specified headings into the CSV file 
     * 
     * eg. Time, Left Enc, Right Enc, Gyro etc.
     *
     * @param timeHeading, leftEncHeading, rightEncHeading, gyroHeading
     */
    public void createEncoderGyroDataheadings(String timeHeading,
                String leftEncHeading,String rightEncHeading,String gyroHeading)                                                     
    {
        printStream.print(timeHeading);
        printStream.print(",");
        printStream.print(leftEncHeading);
        printStream.print(",");
        printStream.print(rightEncHeading);
        printStream.print(",");
        printStream.println(gyroHeading);
    }
    
    /**
     * Writes the specified doubles by appending them in CSV format.
     * Creates a new line after the four parameters are filled
     * 
     * eg. 9.03,9.04,9.05,9.06 
     *     9.07,9.08,9.09.9.10
     *
     * @param elapsedTime, leftEnc, rightEnc, gyro
     */
    public void writeEncoderGyroData(double elapsedTime,
                                     double leftEnc,double rightEnc,double gyro)                                                     
    {
        printStream.print(elapsedTime);
        printStream.print(",");
        printStream.print(leftEnc);
        printStream.print(",");
        printStream.print(rightEnc);
        printStream.print(",");
        printStream.println(gyro);
    }  
    
    public void writeShooterData(double shooterRPS)                                                     
    {
        printStream.print(System.currentTimeMillis()/1000);
        printStream.print(",");
        printStream.println(shooterRPS);
    }
    
    /**
     * Closes the file temporarily for other file writing operations
     */
    public static synchronized void pause(){
        try {
            kConnection.close();
            OS.close();
        } catch (IOException e){
            System.out.println("CSV Log Closed");
        }
    }

    /**
     * Reopens the last file for resumed logging functionality
     */
    public static synchronized void resume(){
        try {
            kConnection.create();
            OS = kConnection.openOutputStream();
        } catch (IOException e){
            System.out.println("CSV Log Resumed");
        }
    }
}//end class CSVLogger
