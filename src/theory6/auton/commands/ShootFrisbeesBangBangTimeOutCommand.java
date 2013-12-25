/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.auton.commands;

import edu.wpi.first.wpilibj.Timer;
import theory6.auton.AutonCommand;
import theory6.subsystems.Shooter;

/**
 *
 * @author Sagar
 */
public class ShootFrisbeesBangBangTimeOutCommand implements AutonCommand{
    Timer t = new Timer();
    Shooter shooter;
    double shooterSetPoint;
    double timeOutInSecs;
    
    double frisbeeCount = 0;
    
    int toggle = 0;
    
    public ShootFrisbeesBangBangTimeOutCommand(double setPoint, double numOfFrisbees, double timeOut){
        this.shooterSetPoint = setPoint;
        this.timeOutInSecs = timeOut;
        this.frisbeeCount = numOfFrisbees;
        shooter = Shooter.getInstance();
    }
        
    public void init() {
        shooter.frisbeesShot = 0;
        shooter.shootingDone = false;
        shooter.loadFrisbee(false, 0.1, false, false);
        t.reset();
        t.start();
        
    }

    public boolean run() {
        shooter.shootFrisbeesAuton(frisbeeCount, shooterSetPoint);
        
        return t.get() > timeOutInSecs || shooter.getShootingDone();
    }

    public void done() {
        shooter.setShooterPWM(0);
        shooterSetPoint = 0;
    }  
    
}
