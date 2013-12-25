/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.auton.commands;

import theory6.auton.AutonCommand;
import theory6.subsystems.Vacjume;
import edu.wpi.first.wpilibj.Timer;
import theory6.utilities.Constants;

/**
 *
 * @author Sagar
 */
public class VacjumePosTimeOutCommand implements AutonCommand {
    
    double timeOutInSecs;
    double setPoint;
    Timer t = new Timer();
    Vacjume vacjume;
    
    public VacjumePosTimeOutCommand(double potVal, double timeOut){
        this.setPoint = potVal;
        this.timeOutInSecs = timeOut;
        vacjume = Vacjume.getInstance();
    }

    public void init() {
        if (setPoint - vacjume.getVacjumePot() > 0) { //vacjume Up
            vacjume.setVacjumePID(Constants.getDouble("vacUpP"), Constants.getDouble("vacUpI"), Constants.getDouble("vacUpD"));
        }
        else if (setPoint - vacjume.getVacjumePot() < 0) { //vacjume Down
            vacjume.setVacjumePID(Constants.getDouble("vacDownP"), Constants.getDouble("vacDownI"), Constants.getDouble("vacDownD"));
        }     
        
        vacjume.resetPID(); //reset accumulated error from previous cycles
        
        t.reset();
        t.start();
    }

    public boolean run() {
        vacjume.setVacjumePos(setPoint);
        return (Math.abs(vacjume.getVacjumePot() - setPoint) < 5) || t.get() > timeOutInSecs;
    }

    public void done() {
        vacjume.setSpeed(0);
    }  
    
}
