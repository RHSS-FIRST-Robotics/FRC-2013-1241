/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.subsystems;

import theory6.utilities.Victor884;
import edu.wpi.first.wpilibj.DigitalInput;
import theory6.main.ElectricalConstants;
import theory6.pid.PIDController;
import edu.wpi.first.wpilibj.AnalogChannel;
import theory6.utilities.ToggleBoolean;

/**
 *
 * @author Sagar
 */
public class Vacjume {
    
    static Vacjume inst = null;
   
    Victor884 vacjumeMotors; //y split
    
    Victor884 leftRollerVictor;
    Victor884 rightRollerVictor;
    
    PIDController vacjumePID;
    
    AnalogChannel leftRollerSensor; 
    AnalogChannel rightRollerSensor;
    
    DigitalInput leftRollerLimit;
    DigitalInput rightRollerLimit;
    
    AnalogChannel vacjumePot;
    
    ToggleBoolean incrementSpeed = new ToggleBoolean(); //Y-Button while testing
    ToggleBoolean decrementSpeed = new ToggleBoolean(); //A-Button while testing
    
    double vacjumeSpeed = 0;
    
    boolean leftLimit;
    boolean rightLimit;
    
    public Vacjume()
    {
        leftRollerVictor = new Victor884(ElectricalConstants.LEFT_INTAKE_PWM);
        rightRollerVictor = new Victor884 (ElectricalConstants.RIGHT_INTAKE_PWM);
        
        vacjumeMotors = new Victor884 (ElectricalConstants.DROP_DOWN_MOTOR_1_PWM);
        
        vacjumePID = new PIDController(0,0,0);
        
        leftRollerSensor = new AnalogChannel(ElectricalConstants.INTAKE_LEFT_SENSOR);
        rightRollerSensor = new AnalogChannel(ElectricalConstants.INTAKE_RIGHT_SENSOR); 
        leftRollerLimit = new DigitalInput(ElectricalConstants.LEFT_ROLLER_LIMIT);
        rightRollerLimit = new DigitalInput(ElectricalConstants.RIGHT_ROLLER_LIMIT);
        
        vacjumePot = new AnalogChannel(ElectricalConstants.DROP_DOWN_POT);
    }
      
    public static Vacjume getInstance() {
        if(inst == null) {
            inst = new Vacjume();
        }
        return inst;
    }
    
    public void setSpeed(double pwm) {
        vacjumeMotors.setNonLinear(-pwm);
    }
    
    public void setRightRollerSpeed(double pwm) {
        rightRollerVictor.setNonLinear(pwm);
    }
    
    public void setLeftRollerSpeed(double pwm) {
        leftRollerVictor.setNonLinear(pwm);
    }
    
    public boolean leftDiskDetected() {
        return !leftRollerLimit.get();
    }
    
    public boolean rightDiskDetected() {
        return !rightRollerLimit.get();
    }
        
    public double getVacjumePot() {
        return vacjumePot.getAverageValue();
    }
    
    public void setVacjumePID (double p, double i, double d) {
        vacjumePID.changePIDGains(p, i, d);
    }
    
    public void setVacjumePos(double potVal) {
        double currentPos = vacjumePot.getValue();
        
        double vacjumeOutput = vacjumePID.calcPID(potVal, currentPos);
        
        setSpeed(-vacjumeOutput);
    }
    
    public void resetPID() {
        vacjumePID.resetDerivative();
        vacjumePID.resetIntegral();
    }
    
    public void vacjumeSpeedTesterButtons(boolean incrementer, boolean decrementer, boolean setZero) {
        incrementSpeed.set(incrementer);
        decrementSpeed.set(decrementer);
        
        if (incrementSpeed.get()){
            if (vacjumeSpeed >= 1) {
                vacjumeSpeed = 1;
            }
            else{
                vacjumeSpeed += 0.1;
            }
        }
        else if(decrementSpeed.get()){
            if (vacjumeSpeed <= -1) {
                vacjumeSpeed = -1;
            }
            else{
                vacjumeSpeed -= 0.1;
            }
        }
        else if (setZero){
             vacjumeSpeed = 0;
        }
        
        setSpeed(vacjumeSpeed);
    }
}
