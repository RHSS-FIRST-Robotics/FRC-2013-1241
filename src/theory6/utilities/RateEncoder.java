/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.utilities;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Sagar
 */
public class RateEncoder extends Encoder implements Runnable{
    
    double prevWheelTime = 0;
    double cpr = 256; //counts per revolution for encoder (defualt is 256 for the grayhills we use)
    
    double[] dataPts;
    int length;
    int i = 0;
    public double avg = 0;
    
    public RateEncoder(int aChannel, int bChannel, boolean reverseDirection, EncodingType encodingType, int ticksPerRev, int length) {
            super(aChannel, bChannel, reverseDirection, encodingType);
            super.start();
            this.length = length;
            this.cpr = ticksPerRev;
            this.dataPts = new double[length];
    }
    public RateEncoder(int aChannel, int bChannel, boolean reverseDirection, EncodingType encodingType , int length) {
            super(aChannel, bChannel, reverseDirection, encodingType);
            super.start();
            this.length = length;
    }
    
    public double getRPS() {
        double now = Timer.getFPGATimestamp();
        int count = super.get();

        super.reset();
        double actualWheelSpeed = (60.0 / cpr) * count / (now - prevWheelTime); //RPM!
        prevWheelTime = now;
        return actualWheelSpeed/60; //RPS
    }
    
    public double getRPM() {
        double now = Timer.getFPGATimestamp();
        int count = super.get();

        super.reset();
        //NOTE:  60 seconds per minute;   256 counts per rotation
        double actualWheelSpeed = (60.0 / cpr) * count / (now - prevWheelTime); //RPM!
        prevWheelTime = now;
        return actualWheelSpeed; //RPM
    } 
  
    
    public double getAvgRPS() {
            return avg;
    }

    public void run() {

        while (true) {           

            dataPts[i] = getRPS();
            i++;
            if(i >= length) {
                i = 0;
            }

            //get average
            avg = 0;
            for(int j = 0; j < length; j++) {
                avg += dataPts[j];
            }
            avg /= length;

            //SmartDashboard.putNumber("Avg Shooter Enc In Thread", avg);
            try {
            Thread.sleep(10);
            } catch(InterruptedException e){}
        }
    }
}

