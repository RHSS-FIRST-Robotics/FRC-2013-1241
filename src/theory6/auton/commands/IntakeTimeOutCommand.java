/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.auton.commands;

import edu.wpi.first.wpilibj.Timer;
import theory6.subsystems.Vacjume;
import theory6.utilities.Constants;
import theory6.auton.AutonCommand;

/**
 *
 * @author Sagar
 */
public class IntakeTimeOutCommand implements AutonCommand{
    Timer t = new Timer();
    Vacjume vacjume;
    double pwmVal;
    double timeOutInSecs;
    
    public IntakeTimeOutCommand(double pwm, double timeOut){
        this.pwmVal = pwm;
        this.timeOutInSecs = timeOut;
        vacjume = Vacjume.getInstance();
    }
        
    public void init() {
        t.start();
    }

    public boolean run() {
        
        if (vacjume.leftDiskDetected()) {
            vacjume.setLeftRollerSpeed(0);
        }
        else {
            vacjume.setLeftRollerSpeed(pwmVal);
        }
        
        if (vacjume.rightDiskDetected()) {
            vacjume.setRightRollerSpeed(0);
        }
        else {
            vacjume.setRightRollerSpeed(pwmVal);
        }
        
        return (vacjume.leftDiskDetected() && vacjume.rightDiskDetected()) || t.get() > timeOutInSecs;
    }

    public void done() {
        vacjume.setLeftRollerSpeed(0);
        vacjume.setRightRollerSpeed(0);
    }  
     
}
