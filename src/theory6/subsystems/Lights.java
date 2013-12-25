/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.subsystems;

import edu.wpi.first.wpilibj.Relay;
import theory6.main.ElectricalConstants;

/**
 *
 * @author Sagar
 */
public class Lights {
    static Lights inst = null;
    Relay blueLEDs;
    
    public Lights() {
        blueLEDs = new Relay(ElectricalConstants.BLUE_LED_RELAY);
    }
    
    public static Lights getInstance() {
        if(inst == null) {
            inst = new Lights();
        }
        return inst;
    }
    
    public void setBlueLights (boolean set) {
        if (set) {
            blueLEDs.set(Relay.Value.kForward);
        }
        else if(!set) {
            blueLEDs.set(Relay.Value.kReverse);
        }
    }
    
}
