/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.auton.commands;

import edu.wpi.first.wpilibj.Timer;
import theory6.subsystems.Shooter;
import theory6.auton.AutonCommand;
/**
 *
 * @author Sagar
 */
public class SetShooterAngleCommand implements AutonCommand{
    Shooter shooter;
    
    public SetShooterAngleCommand(){
        shooter = Shooter.getInstance();
    }
        
    public void init() {
       shooter.setShooterAngle(false,false,false,false);
    }

    public boolean run() {
        shooter.setShooterAngle(true,false,false,false);
        
        return true;
    }

    public void done() {
    }  
}

