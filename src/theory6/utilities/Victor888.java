/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.utilities;

import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author Sagar
 */
public class Victor888 extends Victor {
    
    public Victor888(int port) {
        super(port);
    }

    public Victor888(int slot, int port) {
        super(slot, port);
    }

    public void setLinear(double pwm) {
        super.set(pwm);
    }
    
    public void setNonLinear(double goalPWM) {
        super.set(goalPWM);
    }
    
    public void set(double goalPWM) {
        super.set(goalPWM);
    }
    
}
