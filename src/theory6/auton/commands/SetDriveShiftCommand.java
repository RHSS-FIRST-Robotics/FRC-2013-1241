/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.auton.commands;

import edu.wpi.first.wpilibj.Timer;
import theory6.subsystems.Shooter;
import theory6.auton.AutonCommand;
import theory6.subsystems.DriveTrain;
/**
 *
 * @author Sagar
 */
public class SetDriveShiftCommand implements AutonCommand{
    DriveTrain driveTrain;
    
    public SetDriveShiftCommand(){
        driveTrain = DriveTrain.getInstance();
    }
        
    public void init() {
       driveTrain.setGearAuton(false);
    }

    public boolean run() {
        driveTrain.setGearAuton(true);     
        return true;
    }

    public void done() {
    }  
}

