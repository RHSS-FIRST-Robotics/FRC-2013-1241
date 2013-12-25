
package theory6.auton.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import theory6.pid.PIDController;
import theory6.subsystems.DriveTrain;
import theory6.utilities.Constants;
import theory6.utilities.MathLogic;
import theory6.auton.AutonCommand;
import theory6.subsystems.Vacjume;

/**
 *
 * @author Sagar
 */
public class TurnDegreesVacDownTimeOutCommand implements AutonCommand {
    
    double angleGoal = 0;
    double maxPWM = 0;
    double power = 0;
    double timeout = 0.0;
    double lastError = 0;
    final double speedDeadband = .1;
    double angleDeadband = 0.75;
    double settleTime = .1;
    double maxRatePWM = 1;
    boolean almostDone = false;
    double leftStiction = 0;
    double rightStiction = 0;
    double PIDOut = 0;
    
    DriveTrain driveTrain;
    PIDController gyroPID;
    Vacjume vacjume;
    Timer timeOutTimer = new Timer();
    Timer timeSinceAlmost = new Timer();
    
    public TurnDegreesVacDownTimeOutCommand(double angle, 
                                     double timeOutInSecs) {
        Constants.getInstance();
        this.angleGoal = angle;
        this.timeout = timeOutInSecs;
        vacjume = Vacjume.getInstance();

        gyroPID = new PIDController(Constants.getDouble("turnPMovingVac"),
                                    Constants.getDouble("turnIMovingVac"),
                                    Constants.getDouble("turnDMovingVac")); 
       
        driveTrain = DriveTrain.getInstance();
    }
    
    public void init() {
        driveTrain.resetEncoders();
        driveTrain.resetGyro();
        
        timeOutTimer.reset();
        timeOutTimer.start();
       
        gyroPID.resetIntegral();
        gyroPID.resetDerivative();
    }
    
    public boolean run() {
        double error = angleGoal - driveTrain.getGyroAngle();
        double speed = maxRatePWM * gyroPID.calcPID(angleGoal, driveTrain.getGyroAngle());
        
        if(Math.abs(error - lastError) < speedDeadband && Math.abs(error) < angleDeadband) {
            
            if(!almostDone) {
                timeSinceAlmost.start();
            }
            almostDone = true;
        }
        
        else {
            almostDone = false;
            timeSinceAlmost.stop();
            timeSinceAlmost.reset();
        }
        
        lastError = error;
        
        driveTrain.setLeftSpeed(speed);
        driveTrain.setRightSpeed(-speed);
        
        return timeSinceAlmost.get() > settleTime || timeOutTimer.get() > timeout;
    }
    
    public void done() {
        driveTrain.setLeftSpeed(0);
        driveTrain.setRightSpeed(0);
        
        timeSinceAlmost.stop();
        timeSinceAlmost.reset();
        
        gyroPID.resetIntegral();
        gyroPID.resetDerivative();
    }
    
}
