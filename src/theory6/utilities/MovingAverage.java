/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.utilities;

/**
 * @author Sagar
 */
public class MovingAverage extends Thread{ 
    
    double[] dataPts;
    int length;
    int i = 0;
    
    double globalInput;
    
    double avg;

    public MovingAverage(int length) {
        dataPts = new double[length];
        this.length = length;
    }
    
    public void setInput(double in) { 
        globalInput = in;
    }

    public void calculate() {
        dataPts[i] = globalInput;
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
    }
    
    public double getOutput() {
        return avg;
    }
    
    public void run() {
        
        while(true) {           
            calculate();
            getOutput();
        }
    }
}
