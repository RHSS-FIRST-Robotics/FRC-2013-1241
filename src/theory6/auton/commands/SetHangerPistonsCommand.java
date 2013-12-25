/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.auton.commands;

import edu.wpi.first.wpilibj.Timer;
import theory6.subsystems.Hanger;
import theory6.auton.AutonCommand;
/**
 *
 * @author Sagar
 */
public class SetHangerPistonsCommand implements AutonCommand{
    Hanger hanger;
    
    public SetHangerPistonsCommand(){
        hanger = Hanger.getInstance();
    }
        
    public void init() {
       hanger.toggleHangerPos(false, false, false);
    }

    public boolean run() {
        hanger.toggleHangerPos(true, false, false);       
        return true;
    }

    public void done() {
    }  
}

