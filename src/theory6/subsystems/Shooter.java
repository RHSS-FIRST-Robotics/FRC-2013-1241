/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import theory6.utilities.ToggleBoolean;
import theory6.utilities.Victor884;
import theory6.main.ElectricalConstants;
import theory6.utilities.Constants;
import theory6.utilities.RateEncoder;

/**
 *
 * @author Sagar 
 */
public class Shooter {
    
    static Shooter inst = null;
   
    Victor884 shooterMotors; //using 888s
        
    public DoubleSolenoid frisbeeLoaderPiston;
    public DoubleSolenoid shooterAnglePiston;
    public DoubleSolenoid wiperPiston;
    
    Timer frisbeeLoaderTimer;
   
    ToggleBoolean frisbeeLoader = new ToggleBoolean();
    ToggleBoolean frisbeeLoaderCounter = new ToggleBoolean();
    ToggleBoolean shooterAngleToggle = new ToggleBoolean();
    ToggleBoolean frisbeeLoaderFeeder = new ToggleBoolean();
    ToggleBoolean backPyramidToggle = new ToggleBoolean();
    ToggleBoolean fullCourtToggle = new ToggleBoolean();
    ToggleBoolean feederAngleToggle = new ToggleBoolean();
    boolean shooterAngleState = false;

    RateEncoder shooterEnc;
    
    double shooterWheelSpeed;
    double currentShooterRPS;
   
    public boolean shooterReady = false;
    int readyToShootCounter = 0;
    
    ToggleBoolean incrementSpeed = new ToggleBoolean(); //Y-Button while testing
    ToggleBoolean decrementSpeed = new ToggleBoolean(); //A-Button while testing
    
    ToggleBoolean wiperPistonToggle = new ToggleBoolean();
    boolean wiperPistonState = false;
    
    double shooterSpeed = 0;
    
    public int frisbeesShot = 0;
    boolean frisbeeLoaderState = false;
    public boolean shootingDone = false;
    public double bangBangPWM = 0;
    public double setpoint = 0;
    public static final int SHOOT = 0;
    public static final int RETRACT = 1;
    public static final int LOADER_IN = 2;
    int fire = RETRACT;
    
    Timer wiperPistonTimer;
    public static final int IN = 0;
    public static final int OUT = 1;
    int wiperPos = IN;
    
    
    public Shooter()
    {
        shooterMotors = new Victor884 (ElectricalConstants.SHOOTER_MOTOR_1_PWM);
        
        frisbeeLoaderPiston = new DoubleSolenoid (ElectricalConstants.FRISBEE_LOADING_EXTEND, ElectricalConstants.FRISBEE_LOADING_RETRACT);
        shooterAnglePiston = new DoubleSolenoid (ElectricalConstants.SHOOTER_ANGLE_EXTEND, ElectricalConstants.SHOOTER_ANGLE_RETRACT);
        wiperPiston = new DoubleSolenoid(2, ElectricalConstants.WIPER_PISTON_EXTEND, ElectricalConstants.WIPER_PISTON_RETRACT);
        
        shooterEnc = new RateEncoder(ElectricalConstants.SHOOTER_ENC_A, ElectricalConstants.SHOOTER_ENC_B, false, Encoder.EncodingType.k1X, 256, 5);
        //new Thread(shooterEnc).start();
        
        frisbeeLoaderTimer = new Timer();
        frisbeeLoaderTimer.start();
        
        wiperPistonTimer = new Timer();
        wiperPistonTimer.start();
    }
    
    public static Shooter getInstance() {
        if(inst == null) {
            inst = new Shooter();
        }
        return inst;
    }
    
    public void setShooterPWM(double speed) {
        shooterMotors.setNonLinear(speed);
    }
    
    public double getEncRPS() {
        return shooterEnc.getRPS();
    }
    
    public double getAvgEncRPS(){
        return shooterEnc.avg;
    }
        
    public void loadFrisbee(boolean loadState, double frisbeeLoadDelay, boolean feederPreset, boolean shooterOnPreset){
        frisbeeLoaderFeeder.set(feederPreset);
        

        frisbeeLoader.set(loadState);
        
        if((frisbeeLoaderFeeder.get() && !loadState)) {
            fire = LOADER_IN;
        }
        else if (shooterOnPreset) {
            fire = RETRACT;
        }
        else if(frisbeeLoader.get() && frisbeeLoaderTimer.get() > frisbeeLoadDelay*2) {
            frisbeeLoaderTimer.reset();
            fire = SHOOT;
        }     

        switch(fire){
            case SHOOT:
                frisbeeLoaderPiston.set(DoubleSolenoid.Value.kForward);
                frisbeeLoaderState = true;
                if (frisbeeLoaderTimer.get() > frisbeeLoadDelay){
                    fire = RETRACT;
                }
                break;
            case RETRACT:
                frisbeeLoaderPiston.set(DoubleSolenoid.Value.kReverse);
                frisbeeLoaderState = false;
                break;
            
            case LOADER_IN:
                frisbeeLoaderPiston.set(DoubleSolenoid.Value.kForward);
            }
    }
    
    public boolean getFrisbeeLoaderPiston() {
        return frisbeeLoaderState;
    }
    
    public void setShooterAngle (boolean shooterAngleToggleButton, boolean backPyramidToggleButton, 
                                 boolean fullCourtToggleButton, boolean feederPreset)
    {
        backPyramidToggle.set(backPyramidToggleButton);
        fullCourtToggle.set(fullCourtToggleButton);
        shooterAngleToggle.set(shooterAngleToggleButton);
        //feederAngleToggle.set(feederPreset);
        
        if(backPyramidToggle.get()) {
            shooterAngleState = false;
        }
        else if (fullCourtToggle.get()) {
            shooterAngleState = true;
        }
        else if (feederPreset) {
            shooterAngleState = true;
        }
        else if(shooterAngleToggle.get()) {
            shooterAngleState = !shooterAngleState;
        }
        
        if(shooterAngleState) {
        shooterAnglePiston.set(DoubleSolenoid.Value.kForward);
        }
        else if(!shooterAngleState) {
        shooterAnglePiston.set(DoubleSolenoid.Value.kReverse);
        }
    } 
    
    public void setWiperTogglePos (boolean wiperPistonToggleButton)
    {
        wiperPistonToggle.set(wiperPistonToggleButton);
        
        if(wiperPistonToggle.get()) {
            wiperPistonState = !wiperPistonState;
        }
        
        if(wiperPistonState) {
            wiperPiston.set(DoubleSolenoid.Value.kForward);
        }
        else if(!wiperPistonState) {
            wiperPiston.set(DoubleSolenoid.Value.kReverse);
        }
    }
    
    public void setAutomaticWiper(boolean wiperPosState, double wiperPosDelay)
    {        
        wiperPistonToggle.set(wiperPosState);
        
        if(wiperPistonToggle.get() && wiperPistonTimer.get() > wiperPosDelay*2) {
            wiperPistonTimer.reset();
            wiperPos = OUT;
        }     

        switch(wiperPos){
            case OUT:
                wiperPiston.set(DoubleSolenoid.Value.kForward);
                wiperPistonState = true;
                if (wiperPistonTimer.get() > wiperPosDelay){
                    wiperPos = IN;
                }
                break;
            case IN:
                wiperPiston.set(DoubleSolenoid.Value.kReverse);
                wiperPistonState = false;
                break;
            }
    }
    
    public boolean getShooterAngle() {
        return shooterAngleState;
    }
    
    public void shooterSpeedTesterButtons(boolean preset, boolean incrementer, boolean decrementer, boolean setZero) {
        incrementSpeed.set(incrementer);
        decrementSpeed.set(decrementer);
        
        if (preset) {
            shooterSpeed = 0.65;
        }
        else if (incrementSpeed.get()){
            if (shooterSpeed >= 1) {
                shooterSpeed = 1;
            }
            else{
                shooterSpeed += 0.05;
            }
        }
        else if(decrementSpeed.get()){
            if (shooterSpeed <= -1) {
                shooterSpeed = -1;
            }
            else{
                shooterSpeed -= 0.05;
            }
        }
        else if (setZero){
            shooterSpeed = 0;
        }
        
        //SmartDashboard.putNumber("Shooter Speed Tester", shooterSpeed);
        
        setShooterPWM(shooterSpeed);
    }
    
    public boolean readyToShoot(double setPoint, double currentRPS, double RPSTolerance, int sampleSize) {
        if (currentRPS > (setPoint * (RPSTolerance + 1)) || currentRPS < (setPoint * (1 - RPSTolerance))) {
            readyToShootCounter = 0;
        }   
        else {
            readyToShootCounter++;
        }
        
        if (readyToShootCounter > sampleSize) {
            shooterReady = true;
        }
        
        else {
            shooterReady = false;
        }

        return shooterReady;  
    }
    
    public void shootFrisbeesAuton(double numOfFrisbees, double setpoint) {
 
        if(setpoint == 0) {
            bangBangPWM = 0;
        }
        
        if(getEncRPS() >= setpoint){
            bangBangPWM = 0;
        }
        else {
            bangBangPWM = 1;
        }

        setShooterPWM(bangBangPWM);

        shooterReady = readyToShoot(setpoint, getEncRPS(), 0.2, 1); //0.2
        
        frisbeeLoaderCounter.set(frisbeeLoaderState);
        
        if (shooterReady && frisbeesShot <= numOfFrisbees) {
            loadFrisbee(true, 0.2, false,false); //0.2
        }
        
        else {
            loadFrisbee(false, 0.2, false, false ); //0.2
        }
        
        if (frisbeeLoaderCounter.get()) {         
            frisbeesShot++;
        }  
 
        if (frisbeesShot >= numOfFrisbees) {           
            frisbeesShot = 0;
            setpoint = 0;
            shootingDone = true;
        }
        else {
            shootingDone = false;
        }
    }
    
    public void maintainShooterSpeed(double setpoint) {
         if(setpoint == 0) {
            bangBangPWM = 0;
        }
        
        if(getEncRPS() >= setpoint){
            bangBangPWM = 0;
        }
        else {
            bangBangPWM = 1;
        }

        setShooterPWM(bangBangPWM);
    }
    
    public boolean getShootingDone() {
        return shootingDone;
    }
    
    public void shootFrisbeesTeleop (double presetOne, double presetThree, 
            boolean presetOneBool, boolean presetThreeBool, 
            boolean loadFrisbeeButton, boolean incrementer, boolean decrementer, boolean setZero, 
            boolean frisbeeLoaderManualButton, boolean shooterOnPreset) {

        if (presetOneBool) {
            setpoint = presetOne;
        }
        else if (presetThreeBool){
            setpoint = presetThree;
        }
        
        incrementSpeed.set(incrementer);
        decrementSpeed.set(decrementer);
        
        if (incrementSpeed.get()) {
            if (setpoint >= 115) {
                setpoint = 115;
            }
            else {
                setpoint += 1;
            }
        }
        else if (decrementSpeed.get()) {
            if (setpoint <= 0) {
                setpoint = 0;
            }
            else{
                setpoint -= 1;
            }
        }
        
        if(setZero) {
            setpoint = 0;
        }
        
        if(getEncRPS() >= setpoint){
            bangBangPWM = 0;
        }
        else {
            bangBangPWM = 1;
        }

        if (setpoint == 0) {
            bangBangPWM = 0;
        }
        
        setShooterPWM(bangBangPWM);
        
        Constants.getInstance();
        
        shooterReady = readyToShoot(setpoint, getEncRPS(), Constants.getDouble("shooterTolerance"), 
                                                              (int)Constants.getDouble("shooterSampleCounter")); //getEncRPS 0.2, 1 <- 1.5 seconds
                                                                      //getAvgEncRps, 0.3, 1
        
        if (loadFrisbeeButton && shooterReady) {
            loadFrisbee(true, Constants.getDouble("teleopLoadDelay"), frisbeeLoaderManualButton , shooterOnPreset);
        }    
        else {
            loadFrisbee(false, Constants.getDouble("teleopLoadDelay"), frisbeeLoaderManualButton, shooterOnPreset);
        }   
    }
}
