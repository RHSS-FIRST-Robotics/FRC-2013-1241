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
public class VacjumeUpAndFeedTimeoutCommand implements AutonCommand {
    
    double timeOutInSecs;
    double setPoint;
    double rightFeedTime;
    double leftFeedTime;
    
    Timer t = new Timer();
    Timer feedRightTime = new Timer();
    Timer feedLeftTime = new Timer();
    Vacjume vacjume;
    
    int state = 0;
    boolean done = false;
    
    public static final int VACJUME_UP = 0;
    public static final int FEED_RIGHT = 1;
    public static final int FEED_LEFT = 2;
    public static final int DONE_SEQUENCE = 3;
    
    public VacjumeUpAndFeedTimeoutCommand(){
        this.setPoint = Constants.getDouble("Misc-VacFeedPos");
        this.leftFeedTime = Constants.getDouble("7V1-leftIntakeFeedTime");
        this.rightFeedTime = Constants.getDouble("7V1-rightIntakeFeedTime");
        
        this.timeOutInSecs = Constants.getDouble("Misc-VacUpFeedDisksTimeout"); 
        vacjume = Vacjume.getInstance();
    }

    public void init() {
        
        vacjume.setVacjumePID(Constants.getDouble("vacUpP"), Constants.getDouble("vacUpI"), Constants.getDouble("vacUpD"));

        vacjume.resetPID(); //reset accumulated error from previous cycles
        
        feedRightTime.reset();
        feedRightTime.stop();
        
        feedLeftTime.reset();
        feedLeftTime.stop();
        
        t.reset();
        t.start();
    }

    public boolean run() {
        
        switch(state) {
            case VACJUME_UP:
                vacjume.setVacjumePos(setPoint);
                
                if (Math.abs(vacjume.getVacjumePot() - setPoint) < 10) {
                    feedRightTime.reset();
                    feedRightTime.start();
                    vacjume.setSpeed(0);
                    state = FEED_RIGHT;
                }
                break;
                
            case FEED_RIGHT:
                vacjume.setRightRollerSpeed(1);
                
                if (feedRightTime.get() > rightFeedTime) {
                    feedLeftTime.reset();
                    feedLeftTime.start();
                    vacjume.setRightRollerSpeed(0);
                    state = FEED_LEFT;
                }
                
                break;
                
            case FEED_LEFT:
                vacjume.setLeftRollerSpeed(1);
                
                if (feedLeftTime.get() > leftFeedTime) {
                    vacjume.setLeftRollerSpeed(0);
                    state = DONE_SEQUENCE;
                }
                
                break;
                
            case DONE_SEQUENCE:
                done = true;
                break;
        }
          
        return done == true || t.get() > timeOutInSecs;
    }

    public void done() {
        vacjume.setSpeed(0);
        vacjume.setRightRollerSpeed(0);
        vacjume.setLeftRollerSpeed(0);
        feedRightTime.reset();
        feedRightTime.stop();
        feedLeftTime.reset();
        feedLeftTime.stop();
        t.reset();
        t.stop();
    }  
    
}
