/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import theory6.main.ElectricalConstants;
import theory6.utilities.ToggleBoolean;

/**
 *
 * @author Sagar
 */
public class Hanger {
    
    static Hanger inst = null;
    public DoubleSolenoid hangerPistons;
    ToggleBoolean hangerToggle = new ToggleBoolean();
    
    public boolean hangerUp = true; //hanger starts down
    
    public Hanger() {
        hangerPistons = new DoubleSolenoid(ElectricalConstants.HANGER_EXTEND,
                                           ElectricalConstants.HANGER_RETRACT);
    }
    
    public static Hanger getInstance() {
        if(inst == null) {
            inst = new Hanger();
        }
        return inst;
    }
    
    public void toggleHangerPos (boolean hangerToggleBool, boolean feederPreset,boolean shooterOnPreset) {
       hangerToggle.set(hangerToggleBool);
       
       if (feederPreset) {
           hangerUp = false;
       }
       else if (shooterOnPreset) {
           hangerUp = true;
       }
       else if (hangerToggle.get()) {
           hangerUp = !hangerUp;
       }
       
       if (hangerUp) {
           hangerPistons.set(DoubleSolenoid.Value.kForward);
       }
       else if(!hangerUp) {
           hangerPistons.set(DoubleSolenoid.Value.kReverse);
       }
    }
}
    
