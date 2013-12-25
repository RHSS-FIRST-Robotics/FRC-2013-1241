/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import theory6.utilities.RelativeGyro;
import theory6.main.ElectricalConstants;
import theory6.utilities.ToggleBoolean;
import theory6.utilities.CSVLogger;
import edu.wpi.first.wpilibj.Timer;
import theory6.utilities.Victor884;
import theory6.utilities.JoystickScaler;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 *
 * @author Sagar
 */
public class DriveTrain {

    static DriveTrain inst = null;
    
    Victor884 leftDrive;
    //Victor888 leftBackVictor;
    Victor884 rightDrive;
    //Victor888 rightBackVictor;
    
    public DoubleSolenoid shifters;
    
    Encoder leftDriveEncoder;
    //double prevWheelTime = 0;
    Encoder rightDriveEncoder;
    
    RelativeGyro driveGyro;
    
    CSVLogger logLinearizingData;
    Timer linearizerDataTimer = new Timer();
    
    JoystickScaler leftAnalogScaler = new JoystickScaler();
    JoystickScaler rightAnalogScaler = new JoystickScaler();
    
    ToggleBoolean gearShifter = new ToggleBoolean();
    public boolean gearShifterState = false;
   
    
    //used in SimpleCheesyDrive*************************************************
    double cdSensitivity = 1;
    double turnOnSpotThres = 0.15;
    double overPwr = 0;
    //used in SimpleCheesyDrive*************************************************
    
    //used in robotPositionXY***************************************************
    double xPosition = 0;
    double yPosition = 0;
    double lastDistance; 
    //used in robotPositionXY***************************************************
    
    //used in victorRamp********************************************************
    double rampSpeed = 0;
    //used in victorRamp********************************************************
    
    double leftJoyScaled;
    double rightJoyScaled;

    public DriveTrain() {
        leftDrive = new Victor884(ElectricalConstants.FRONT_LEFT_DRIVE_PWM);
        //leftBackVictor = new Victor888(ElectricalConstants.BACK_LEFT_DRIVE_PWM);
        rightDrive = new Victor884(ElectricalConstants.FRONT_RIGHT_DRIVE_PWM);
        //rightBackVictor = new Victor888(ElectricalConstants.BACK_RIGHT_DRIVE_PWM);
        
        leftDriveEncoder = new Encoder(ElectricalConstants.LEFT_DRIVE_ENC_A, 
                                       ElectricalConstants.LEFT_DRIVE_ENC_B, 
                                       ElectricalConstants.leftDriveTrainEncoderReverse, 
                                       Encoder.EncodingType.k1X);
        leftDriveEncoder.setDistancePerPulse(ElectricalConstants.driveEncoderDistPerTick);  
        leftDriveEncoder.start();
        
        rightDriveEncoder = new Encoder(ElectricalConstants.RIGHT_DRIVE_ENC_A, 
                                        ElectricalConstants.RIGHT_DRIVE_ENC_B, 
                                        ElectricalConstants.rightDriveTrainEncoderReverse, 
                                        Encoder.EncodingType.k1X);        
        rightDriveEncoder.setDistancePerPulse(ElectricalConstants.driveEncoderDistPerTick); 
        rightDriveEncoder.start();
        
        driveGyro = new RelativeGyro(ElectricalConstants.DRIVE_GYRO_PORT);
        
        shifters = new DoubleSolenoid(ElectricalConstants.SHIFTER_EXTEND,
                                      ElectricalConstants.SHIFTER_RETRACT);
        
        logLinearizingData = new CSVLogger("file:///linearizeLog.csv");
        logLinearizingData.createEncoderGyroDataheadings("RampSpeed", "LeftEnc", "RightEnc", "Time");
        linearizerDataTimer.start();
    }
    
    public static DriveTrain getInstance() {
        if(inst == null) {
            inst = new DriveTrain();
        }
        return inst;
    }

    public void setLeftSpeed(double speed) {
        
        if (Math.abs(speed) < 0.05 ) {
            speed = 0.0;
        }
        leftDrive.setNonLinear(speed);
    }

    public void setRightSpeed(double speed) 
    {
        if (Math.abs(speed) < 0.05 ) {
            speed = 0.0;
        }
         rightDrive.setNonLinear(-speed);
    }
    
    public void setGear(boolean gearState) {
        gearShifterState = gearState;
        if (gearState) {
            shifters.set(DoubleSolenoid.Value.kForward);
        }
        else {
            shifters.set(DoubleSolenoid.Value.kReverse);
        }
    }
    
    public void setGearAuton(boolean gearState) {
        gearShifter.set(gearState);
        
        if(gearShifter.get()){
            gearShifterState = !gearShifterState;
        }
        
        if (gearShifterState) {
            shifters.set(DoubleSolenoid.Value.kForward);
        }
        else if (!gearShifterState){
            shifters.set(DoubleSolenoid.Value.kReverse);
        }
    }
    
    public void setCheesyDriveSensitivity(boolean sens) {
        if (sens) {
            cdSensitivity = 1;
        } 
        else {
            cdSensitivity = 1;
        }
    }

    public void simpleCheesyDrive(double throttle, double wheel, int scaledPower) {

        double angularPwr;
        double leftPwr;
        double rightPwr;
        
        boolean turnOnSpot = false;
        throttle = leftAnalogScaler.scaleJoystick(throttle, scaledPower);
        wheel = rightAnalogScaler.scaleJoystick(wheel, scaledPower);
        if (Math.abs(throttle) < turnOnSpotThres) {
            turnOnSpot = true;
        }

        if (turnOnSpot) {
            overPwr = 1.0;
            angularPwr = wheel;
        } 
        else {
            overPwr = 0.0;
            angularPwr = Math.abs(throttle) * wheel * cdSensitivity;
        }
        
        rightPwr = leftPwr = throttle;
        
        if (throttle > 0) {
            leftPwr += angularPwr;
            rightPwr -= angularPwr;
        }
        else if(throttle <= 0) {
            leftPwr -= angularPwr;
            rightPwr += angularPwr;           
        }
        
        if (leftPwr > 1.0) {
            rightPwr -= overPwr * (leftPwr - 1.0);
            leftPwr = 1.0;
        } else if (rightPwr > 1.0) {
            leftPwr -= overPwr * (rightPwr - 1.0);
            rightPwr = 1.0;
        } else if (leftPwr < -1.0) {
            rightPwr += overPwr * (-1.0 - leftPwr);
            leftPwr = -1.0;
        } else if (rightPwr < -1.0) {
            leftPwr += overPwr * (-1.0 - rightPwr);
            rightPwr = -1.0;
        }

        setLeftSpeed(leftPwr);
        setRightSpeed(rightPwr);
    }
    
    public void tankDrive (double leftJoy, double rightJoy, int scaledPower) {
        setLeftSpeed(leftAnalogScaler.scaleJoystick(leftJoy, scaledPower));
        setRightSpeed(rightAnalogScaler.scaleJoystick(rightJoy, scaledPower));
    }
    
    public void victorRamp() { 
        //USE TO GET DATA; DO NOT USE THIS FUNCTION DURING 
        //MATCHES IT WILL STOP THE CODE
        if (rampSpeed >= 1) {
            setLeftSpeed(0);
            setRightSpeed(0);
        }
        
        while (rampSpeed < 1.1)
        {   
            logLinearizingData.writeEncoderGyroData(rampSpeed,
            getLeftEncoderRate(),getRightEncoderRate(),linearizerDataTimer.get());
            
            rampSpeed += 0.001;
            setLeftSpeed(rampSpeed);
            setLeftSpeed(rampSpeed);
            
            if (rampSpeed >= 1.1) {
                break;
            }
        }
    }
/***************************Encoder Functions**********************************/    
    public void setEncoderDistPerPulse(double leftDistPerPulse, double rightDistPerPulse) {
        leftDriveEncoder.setDistancePerPulse(leftDistPerPulse); 
        rightDriveEncoder.setDistancePerPulse(rightDistPerPulse); 
    }

    public void stopEncoders() {
        leftDriveEncoder.stop();
        rightDriveEncoder.stop();
    }
    
    public double getLeftEncoderDist() {
        return leftDriveEncoder.getDistance();
    }
    
    public double getRightEncoderDist() {
        return rightDriveEncoder.getDistance();
    }
    
    public double getLeftEncoderRaw() {
        return leftDriveEncoder.getRaw();
    }
    
    public double getRightEncoderRaw() {
        return rightDriveEncoder.getRaw();
    }
    
    public double getLeftEncoderRate() {
        return leftDriveEncoder.getRate();
    }
    
    public double getRightEncoderRate() {
        return rightDriveEncoder.getRate(); 
    }
/***************************Encoder Functions**********************************/
/*----------------------------------------------------------------------------*/
      
/****************************Gyro Functions************************************/
    public double getGyroAngle()
    {
        return (driveGyro.getAngle() / 168.2)*180.0;
        
        //return (driveGyro.getAngle() / 175.9)*180.0;
    }
    
    public void resetEncoders()
    {
        leftDriveEncoder.reset();
        rightDriveEncoder.reset();
    }
    
    public void resetGyro()
    {
        driveGyro.reset();
    }
    
/****************************Gyro Functions************************************/
/*----------------------------------------------------------------------------*/
    
/****************************Robot Position Functions**************************/
    public void calcRobotPositionXY(double leftEnc, double rightEnc, double gyroVal)
    {
        double encAverage = (leftEnc + rightEnc) / 2;
        double currentDistance = encAverage;
        
        double distThisCycle = currentDistance - lastDistance;
        
        xPosition += distThisCycle*Math.sin(gyroVal);
        yPosition += distThisCycle*Math.cos(gyroVal);
        
        lastDistance = currentDistance;
    }
    
    public double getDriveXPos() {
        return xPosition;
    }
    
    public double getDriveYPos() {
        return yPosition;
    }
    
    public void resetXY() {
        xPosition = 0;
        yPosition = 0;
    }
/****************************Robot Position Functions**************************/
/*----------------------------------------------------------------------------*/
      
}
