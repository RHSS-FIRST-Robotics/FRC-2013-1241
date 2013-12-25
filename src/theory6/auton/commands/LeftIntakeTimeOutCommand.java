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
public class LeftIntakeTimeOutCommand implements AutonCommand{
    Timer t = new Timer();
    Vacjume vacjume;
    double pwmVal;
    double timeOutInSecs;
    
    public LeftIntakeTimeOutCommand(double pwm, double timeOut){
        this.pwmVal = pwm;
        this.timeOutInSecs = timeOut;
        vacjume = Vacjume.getInstance();
    }
        
    public void init() {
        t.reset();
        t.start();
    }

    public boolean run() {
        vacjume.setLeftRollerSpeed(pwmVal);
        
        return t.get() > timeOutInSecs;
    }

    public void done() {
        vacjume.setLeftRollerSpeed(0);
    }  
}

