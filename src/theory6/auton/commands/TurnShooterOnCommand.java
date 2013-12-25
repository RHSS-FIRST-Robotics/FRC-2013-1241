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
public class TurnShooterOnCommand implements AutonCommand {
    Shooter shooter;
    double pwm = 0;

    public TurnShooterOnCommand(double shooterPwm) {
        pwm = shooterPwm;
        shooter = Shooter.getInstance();
    }
    
    public void init() {
        shooter.setShooterPWM(pwm);
    }

    public boolean run() {
        shooter.setShooterPWM(pwm);
        return true;
    }

    public void done() {
        shooter.setShooterPWM(pwm);
    }
}