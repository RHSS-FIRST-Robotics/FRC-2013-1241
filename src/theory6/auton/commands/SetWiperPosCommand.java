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
public class SetWiperPosCommand implements AutonCommand{
    Shooter shooter;
    Timer t = new Timer();
  
    public SetWiperPosCommand(){
        shooter = Shooter.getInstance();
    }
        
    public void init() {
        t.reset();
        t.start();       
        shooter.setAutomaticWiper(false, 0.3);       
    }

    public boolean run() {
        shooter.setAutomaticWiper(true, 0.3);
        
        return t.get() > 0.6;
    }

    public void done() {

    }  
}


