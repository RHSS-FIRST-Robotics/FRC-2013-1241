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
public class ParallelBangBangTimeOutCommand implements AutonCommand{
    Timer t = new Timer();
    Shooter shooter;
    double shooterSetPoint;
    double timeOutInSecs;

    public ParallelBangBangTimeOutCommand(double setPoint, double timeOut){
        this.shooterSetPoint = setPoint;
        this.timeOutInSecs = timeOut;
        shooter = Shooter.getInstance();
    }
        
    public void init() {
        //shooter.loadFrisbee(false, 0.1, false, false);
        t.reset();
        t.start();     
    }

    public boolean run() {
        shooter.maintainShooterSpeed(shooterSetPoint);
        
        return t.get() > timeOutInSecs;
    }

    public void done() {
       // shooter.maintainShooterSpeed(shooterSetPoint);
        shooter.setShooterPWM(0);
        shooterSetPoint = 0;
    }  
    
}
