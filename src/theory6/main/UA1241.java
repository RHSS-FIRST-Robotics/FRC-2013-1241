/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package theory6.main;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import theory6.subsystems.DriveTrain;
import theory6.subsystems.Shooter;
import theory6.subsystems.Vacjume;
import theory6.subsystems.Hanger;
import theory6.subsystems.Lights;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import theory6.utilities.ToggleBoolean;
import theory6.utilities.Constants;
import theory6.utilities.CSVLogger;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import theory6.utilities.SendableChooser; //modified version of WPIlib SendableChooser
                                          //that uses integers
import edu.wpi.first.wpilibj.Timer;

import theory6.auton.AutonController;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class UA1241 extends IterativeRobot {

    DriveTrain driveTrain;
    Shooter shooter;
    Vacjume vacjume;
    Hanger hanger;
    Lights robotLights;
    Compressor compressor;
    
    Joystick drivePad;
    Joystick toolPad;
    
    Timer vacIntakeTimer;
    DriverStationLCD dsLCD;
    int lcdUpdateCycle = 0;
    
    int smartDSUpdateCycle = 0;
    
    AutonController ac = new AutonController();
    AutonSequences autonSeq = new AutonSequences();
    SendableChooser autonSwitcher;
    
    ToggleBoolean autoVacToggle = new ToggleBoolean();
    int autoVacState = 0;
    
    public static final int SHOOTER_DOWN = 0;
    public static final int VACJUME_UP = 1;
    public static final int INTAKE_RIGHT_DISK = 2;
    public static final int INTAKE_LEFT_DISK = 3;
    public static final int SWIPE = 4;
    public static final int DONE = 5;
    
/************************Constants File Teleop Variables***********************/
    double frisbeeLoadDelay;
    
    double vacUpPos;
    double vacDownPos;
    
    double vacUpP;
    double vacUpI;
    double vacUpD;
    
    double vacDownP;
    double vacDownI;
    double vacDownD;
    
    double backPyramidPreset;
    double frontPyramidPreset;
    double fullCourtPreset;
    
    double teleopIntakeSpeed;
    
/************************Constants File Tele-op Variables***********************/
    
    public void robotInit() {

        driveTrain = DriveTrain.getInstance();
        shooter = Shooter.getInstance();
        vacjume = Vacjume.getInstance();
        compressor = new Compressor(ElectricalConstants.COMPRESSOR_PRESSURE_SENSOR,
                                    ElectricalConstants.COMPRESSOR_RELAY);
        robotLights = Lights.getInstance();
        hanger = Hanger.getInstance();
        
        drivePad = new Joystick(GamepadConstants.DRIVE_USB_PORT);
        toolPad = new Joystick(GamepadConstants.TOOL_USB_PORT);
        
        dsLCD = DriverStationLCD.getInstance();        
        
        autonSwitcher = new SendableChooser();
        
        vacIntakeTimer = new Timer();

        Constants.getInstance();
        Constants.load();
    }
 
    
    
    
    
   
    
    
    
    /**
     * This function is called periodically when robot is disabled
     */
    public void disabledPeriodic() {
        
        ac.clear();
        
        driveTrain.setGear(true); //high gear
                
        dsLCD.println(DriverStationLCD.Line.kUser1, 1, "Left Encoder: "
                + driveTrain.getLeftEncoderDist() + "      ");

        dsLCD.println(DriverStationLCD.Line.kUser2, 1, "Right Encoder: "
                + driveTrain.getRightEncoderDist() + "    ");

        dsLCD.println(DriverStationLCD.Line.kUser3, 1, "Gyro: "
                + driveTrain.getGyroAngle() + "      ");

        dsLCD.println(DriverStationLCD.Line.kUser4, 1, "Vacjume Pot: "
                + vacjume.getVacjumePot() + "    ");
        
        dsLCD.println(DriverStationLCD.Line.kUser5, 1, "Shooter RPS: "
                + shooter.getEncRPS() + "    ");

        dsLCD.println(DriverStationLCD.Line.kUser6, 1, "L: "
                + vacjume.leftDiskDetected() + " " + "R: " 
                + vacjume.rightDiskDetected() + "    ");
                
        compressor.stop();
        
        if ((lcdUpdateCycle % 50) == 0) {
            dsLCD.updateLCD();
        } 
        else {
            lcdUpdateCycle++;
        } 
        
        robotLights.setBlueLights(true);
        
        if(toolPad.getRawButton(GamepadConstants.A_BUTTON)) {
            driveTrain.resetGyro();
        }
        
        if(toolPad.getRawButton(GamepadConstants.B_BUTTON)) {
            driveTrain.resetEncoders();
        }
        
        if(toolPad.getRawButton(GamepadConstants.X_BUTTON)) {
            Constants.load();
        }
        /**************************SmartDashBoard******************************/
        
        if((smartDSUpdateCycle % 50 == 0)) {
            SmartDashboard.putNumber("Left Analog Y", drivePad.getRawAxis(GamepadConstants.LEFT_ANALOG_Y));
            SmartDashboard.putNumber("Right Analog Y", drivePad.getRawAxis(GamepadConstants.RIGHT_ANALOG_Y));
            SmartDashboard.putNumber("Left Analog X", drivePad.getRawAxis(GamepadConstants.LEFT_ANALOG_X));
            SmartDashboard.putNumber("Right Analog X", drivePad.getRawAxis(GamepadConstants.RIGHT_ANALOG_X));
            SmartDashboard.putNumber("ShooterEnc RPS", shooter.getEncRPS());
            SmartDashboard.putNumber("Bang Bang PWM", shooter.bangBangPWM);
            SmartDashboard.putBoolean("Shooter Ready", shooter.shooterReady);
            SmartDashboard.putNumber("Shooter Setpoint", shooter.setpoint);
            SmartDashboard.putBoolean("Left Intake Limit", vacjume.leftDiskDetected());
            SmartDashboard.putBoolean("Right Intake Limit", vacjume.rightDiskDetected());   
            SmartDashboard.putBoolean("Gear Shift", driveTrain.gearShifterState);
            SmartDashboard.putNumber("Left Encoder", driveTrain.getLeftEncoderDist());
            SmartDashboard.putNumber("Right Encoder", driveTrain.getRightEncoderDist());
            SmartDashboard.putBoolean("Hanger Position", hanger.hangerUp);
            SmartDashboard.putNumber("Avg Drive Enc", (driveTrain.getLeftEncoderDist() + driveTrain.getRightEncoderDist()) / 2);
            SmartDashboard.putNumber("Avg Shooter Enc", shooter.getAvgEncRPS());
        }
        else {
            smartDSUpdateCycle++;
        }
        
        autonSwitcher.addDefault("Anywhere-Shoot 3 Disks", 0);
        autonSwitcher.addInteger("Anywhere-Shoot 3 & Drive Back", 1);
        autonSwitcher.addInteger("Center-Shoot 3 & Drive Left", 2);
        autonSwitcher.addInteger("Center-Shoot 3 & Drive Right", 3);
        
        autonSwitcher.addInteger("Center-5 Disk Under Pyramid Straight", 4);
        autonSwitcher.addInteger("Center-5 Disk Center Line Old", 5);
        autonSwitcher.addInteger("Center-5 Disk Center Line Optimized", 6);
        autonSwitcher.addInteger("Right-5 Disk Center Line", 7); //turn right, turn left
        autonSwitcher.addInteger("Left-5 Disk Center Line", 8);    

        autonSwitcher.addInteger("Center-5 Disk Under Pyramid Left", 9);
        autonSwitcher.addInteger("Center-5 Disk Under Pyramid Right", 10);        
        autonSwitcher.addInteger("Center-5 Disk Under Pyramid Nothing", 11);        
        autonSwitcher.addInteger("Right-5 Disk Center Line Turn Left", 12);
        autonSwitcher.addInteger("Right-5 Disk Center Line Turn Right", 13);       
        autonSwitcher.addInteger("Left-5 Disk Center Line Turn Left", 14);
        autonSwitcher.addInteger("Left-5 Disk Center Line Turn Right", 15);
        autonSwitcher.addInteger("No Auton", 16);

        autonSwitcher.addInteger("Test-Drive V1", 17); 
        autonSwitcher.addInteger("Test-Drive V2", 18);
        autonSwitcher.addInteger("Test-DriveV2 With Vacjume", 19);
        autonSwitcher.addInteger("Test-DriveV2 And Pick Up", 20);
        autonSwitcher.addInteger("Test-Pivot Turn Then Drive", 21);
        autonSwitcher.addInteger("Test-Turn", 22);
        autonSwitcher.addInteger("Test-Turn with Vacjume", 23);
        autonSwitcher.addInteger("Test-Vacjume Up and Feed 2 Commands", 24);
        autonSwitcher.addInteger("Test-Vacjume Up and Feed 1 Command", 25);
        autonSwitcher.addInteger("Test-Vacjume To Pos", 26);
        autonSwitcher.addInteger("Center-7 Disk Hershey", 27);
        SmartDashboard.putData("Auton Selecter", autonSwitcher);        
    }

    public void autonomousInit() {
        ac.clear();

        driveTrain.resetGyro();
        driveTrain.resetEncoders();
        driveTrain.resetXY();
        
        compressor.stop();
        robotLights.setBlueLights(true);
        driveTrain.shifters.set(DoubleSolenoid.Value.kForward); //high gear
        hanger.hangerPistons.set(DoubleSolenoid.Value.kForward); //hanger down
        
        switch(autonSwitcher.getSelected()){
            case 0: 
                ac = autonSeq.startAnyWhere3Disk();
                break;
            case 1:
                ac = autonSeq.startAnywhere3DiskDriveStraightBack();
                break;
            case 2: 
                ac = autonSeq.centerPyramid3DiskDriveLeftField();
                break;
            case 3:
                ac = autonSeq.centerPyramid3DiskDriveRightField();
                break;
            case 4:
                ac = autonSeq.centerPyramid5DiskUnderPyramidDriveBack();
                break;
            case 5:
                ac = autonSeq.centerPyramid5DiskCenterLineOld();
                break;
            case 6:
                ac = autonSeq.centerPyramid5DiskCenterLineOptimized();
                break;
            case 7:
                ac = autonSeq.rightPyr5DiskCenterLineFinal();
                break;                         
            case 8:
                ac = autonSeq.leftPyr5DiskCenterLineFinal();
                break;   
            case 9:
                ac = autonSeq.centerPyramid5DiskUnderPyramidDriveLeft();
                break; 
            case 10:
                ac = autonSeq.centerPyramid5DiskUnderPyramidDriveRight();
                break;
            case 11:
                ac = autonSeq.centerPyramid5DiskUnderPyramidNoDriveBack(); //doesnt drive back in the end
                break; 
            case 12:
                ac = autonSeq.rightPyr5DiskCenterLineFinalDriveLeft(); 
                break; 
            case 13:
                ac = autonSeq.rightPyr5DiskCenterLineFinalDriveRight(); 
                break; 
            case 14:
                ac = autonSeq.leftPyr5DiskCenterLineFinalDriveLeft(); 
                break; 
            case 15:
                ac = autonSeq.leftPyr5DiskCenterLineFinalDriveRight(); 
                break; 
            case 16:
                ac = autonSeq.noAuton(); 
                break; 
            case 17:
                ac = autonSeq.testAutonDriveV1();
                break;
            case 18:
                ac = autonSeq.testAutonDriveV2();
                break;
            case 19:
                ac = autonSeq.testAutonDriveWithVacjume();
                break;
            case 20:
                ac = autonSeq.testAutonDriveAndPickUp();
                break;
            case 21:
                ac = autonSeq.testAutonPivotThenDrive();
                break;
            case 22:
                ac = autonSeq.testAutonTurn();
                break;
            case 23:
                ac = autonSeq.testAutonTurnWithVacjume();
                break;
            case 24:
                ac = autonSeq.testVacjumeUpAndFeedSeperate();
                break;
            case 25:
                ac = autonSeq.testVacjumeUpAndFeedOneCommand();
                break;
            case 26:
                ac = autonSeq.testVacjume();
                break;
            
            case 27:
                ac = autonSeq.centerPyramid7DiskHersheyAuton();
                break;
        }
        
    }
 
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

        ac.executeCommands();
        
        dsLCD.println(DriverStationLCD.Line.kUser1, 1, "Left Encoder: "
                + driveTrain.getLeftEncoderDist() + "      ");

        dsLCD.println(DriverStationLCD.Line.kUser2, 1, "Right Encoder: "
                + driveTrain.getRightEncoderDist() + "    ");

        dsLCD.println(DriverStationLCD.Line.kUser3, 1, "Gyro: "
                + driveTrain.getGyroAngle() + "      ");

        dsLCD.println(DriverStationLCD.Line.kUser4, 1, "Vacjume Pot: "
                + vacjume.getVacjumePot() + "    ");
        
        dsLCD.println(DriverStationLCD.Line.kUser5, 1, "Shooter RPS: "
                + shooter.getEncRPS() + "    ");

        dsLCD.println(DriverStationLCD.Line.kUser6, 1, "L: "
                + vacjume.leftDiskDetected() + " " + "R: " 
                + vacjume.rightDiskDetected() + "    ");
        
        if((smartDSUpdateCycle % 50 == 0)) {
            SmartDashboard.putNumber("Left Analog Y", drivePad.getRawAxis(GamepadConstants.LEFT_ANALOG_Y));
            SmartDashboard.putNumber("Right Analog Y", drivePad.getRawAxis(GamepadConstants.RIGHT_ANALOG_Y));
            SmartDashboard.putNumber("Left Analog X", drivePad.getRawAxis(GamepadConstants.LEFT_ANALOG_X));
            SmartDashboard.putNumber("Right Analog X", drivePad.getRawAxis(GamepadConstants.RIGHT_ANALOG_X));
            SmartDashboard.putNumber("ShooterEnc RPS", shooter.getEncRPS());
            SmartDashboard.putNumber("Bang Bang PWM", shooter.bangBangPWM);
            SmartDashboard.putBoolean("Shooter Ready", shooter.shooterReady);
            SmartDashboard.putNumber("Shooter Setpoint", shooter.setpoint);
            SmartDashboard.putBoolean("Left Intake Limit", vacjume.leftDiskDetected());
            SmartDashboard.putBoolean("Right Intake Limit", vacjume.rightDiskDetected());   
            SmartDashboard.putBoolean("Gear Shift", driveTrain.gearShifterState);
            SmartDashboard.putNumber("Left Encoder", driveTrain.getLeftEncoderDist());
            SmartDashboard.putNumber("Right Encoder", driveTrain.getRightEncoderDist());
            SmartDashboard.putBoolean("Hanger Position", hanger.hangerUp);
            SmartDashboard.putNumber("Avg Drive Enc", (driveTrain.getLeftEncoderDist() + driveTrain.getRightEncoderDist()) / 2);
            SmartDashboard.putNumber("Avg Shooter Enc RPS", shooter.getAvgEncRPS());
        }
        else {
            smartDSUpdateCycle++;
        }
        
        if ((lcdUpdateCycle % 50) == 0) {
            dsLCD.updateLCD();
        } 
        else {
            lcdUpdateCycle++;
        }
        
        robotLights.setBlueLights(true);
    }

    public void teleopInit() {
        ac.clear();
        driveTrain.setLeftSpeed(0);
        driveTrain.setRightSpeed(0);
        vacjume.setLeftRollerSpeed(0);
        vacjume.setRightRollerSpeed(0);
        vacjume.setSpeed(0);
        shooter.setShooterPWM(0);
        
        
        compressor.start();
        
        driveTrain.shifters.set(DoubleSolenoid.Value.kReverse); //low gear
        
        vacUpP = Constants.getDouble("vacUpP");
        vacUpI = Constants.getDouble("vacUpI");
        vacUpD = Constants.getDouble("vacUpD");
        
        vacDownP = Constants.getDouble("vacDownP");
        vacDownI = Constants.getDouble("vacDownI");
        vacDownD = Constants.getDouble("vacDownD");
       
        vacUpPos = Constants.getDouble("vacUpPosTele");
        vacDownPos = Constants.getDouble("vacDownPosTele");
        frisbeeLoadDelay = Constants.getDouble("frisLoadDelay");
        
        backPyramidPreset = Constants.getDouble("backPyramidPreset");
        frontPyramidPreset = Constants.getDouble("frontPyramidPreset");
        fullCourtPreset = Constants.getDouble("fullCourtPreset");    
        
        teleopIntakeSpeed = Constants.getDouble("teleopIntakeSpeed");
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
            
//*****************************DrivePad*****************************************
        double leftAnalogY = drivePad.getRawAxis(GamepadConstants.LEFT_ANALOG_Y);
        double rightAnalogY = drivePad.getRawAxis(GamepadConstants.RIGHT_ANALOG_Y);
//        double leftAnalogX = drivePad.getRawAxis(GamepadConstants.LEFT_ANALOG_X);
//        double rightAnalogX = drivePad.getRawAxis(GamepadConstants.RIGHT_ANALOG_X);

//        if (drivePad.getRawButton(GamepadConstants.LEFT_TRIGGER)) {
//            driveTrain.tankDrive(rightAnalogY, leftAnalogY, 3);
//        }
//        else if (drivePad.getRawButton(GamepadConstants.RIGHT_TRIGGER)) {
//            driveTrain.tankDrive(-rightAnalogY/2, -leftAnalogY/2, 1);
//        }
//        else {
            driveTrain.tankDrive(-leftAnalogY, -rightAnalogY, 3);
//        }
          
        driveTrain.setGear(drivePad.getRawButton(GamepadConstants.RIGHT_BUMPER));
        
        hanger.toggleHangerPos(drivePad.getRawButton(GamepadConstants.LEFT_BUMPER), toolPad.getRawButton(GamepadConstants.BACK_BUTTON), toolPad.getRawButton(GamepadConstants.X_BUTTON));
        
        if (drivePad.getRawAxis(GamepadConstants.DPAD_Y) == 1) {
            robotLights.setBlueLights(false);
        }
        else {
            robotLights.setBlueLights(true);
        }
        
/**********************************Tool Pad************************************/
        
        autoVacToggle.set(toolPad.getRawButton(GamepadConstants.Y_BUTTON));
        
        if (autoVacToggle.get()) {
            autoVacState = SHOOTER_DOWN;
        }
        
        if (toolPad.getRawButton(GamepadConstants.Y_BUTTON)){
            
            switch(autoVacState) {
                case SHOOTER_DOWN: //shooter angle down
                    shooter.shooterAnglePiston.set(DoubleSolenoid.Value.kForward);
                    autoVacState = VACJUME_UP;
                    break;
                case VACJUME_UP: //vacjume up
                    vacjume.setVacjumePID(vacUpP, vacUpI, vacUpD);
                    vacjume.setVacjumePos(vacUpPos);
                    if (Math.abs(vacjume.getVacjumePot() - vacUpPos) < 10) { //5
                        if (vacjume.rightDiskDetected() && vacjume.leftDiskDetected()){
                          autoVacState = INTAKE_RIGHT_DISK;  
                        }
                        else if (vacjume.rightDiskDetected() && !vacjume.leftDiskDetected()){
                          autoVacState = INTAKE_RIGHT_DISK;  
                        }
                        else if (!vacjume.rightDiskDetected() && vacjume.leftDiskDetected()) {
                            autoVacState = INTAKE_LEFT_DISK;
                        }
                        else if (!vacjume.rightDiskDetected() && !vacjume.leftDiskDetected()) {
                            autoVacState = DONE;
                        }
                        vacIntakeTimer.reset();
                        vacIntakeTimer.start();
                    }
                    break;
                case INTAKE_RIGHT_DISK: //right intake disk
                    vacjume.setRightRollerSpeed(1);
                    if (vacIntakeTimer.get() > 0.75) {
                        if (vacjume.leftDiskDetected()) {
                        autoVacState = INTAKE_LEFT_DISK;
                        }
                        else {
                            autoVacState = SWIPE;
                        }
                        vacIntakeTimer.reset();
                        vacIntakeTimer.start();
                    }
                    break;
                case INTAKE_LEFT_DISK: //left intake disk
                    vacjume.setLeftRollerSpeed(1);
                    if (vacIntakeTimer.get() > 0.75) {
                        autoVacState = SWIPE;
                    }    
                    break;
                case SWIPE: //swipe
                    shooter.setAutomaticWiper(true, 0.4);
                    break;
                case DONE:
                    autoVacState = SHOOTER_DOWN;
                    break;
            }
        }
        
        else {
            if (toolPad.getRawButton(GamepadConstants.LEFT_TRIGGER)) {
                vacjume.setVacjumePID(vacDownP, vacDownI, vacDownD);
                vacjume.setVacjumePos(vacDownPos);
            }
            else if (toolPad.getRawButton(GamepadConstants.LEFT_BUMPER)) {
                vacjume.setVacjumePID(vacUpP, vacUpI, vacUpD);
                vacjume.setVacjumePos(vacUpPos);
            }
            else if (toolPad.getRawAxis(GamepadConstants.DPAD_X) == -1){
                vacjume.setSpeed(-0.3);
            }
            else {
                vacjume.setSpeed(0);
            }
            
            if (vacjume.getVacjumePot() > (vacUpPos - 100)) { // potVal < (vacUpPos + 100)
                    if (toolPad.getRawAxis(GamepadConstants.RIGHT_ANALOG_Y) < -0.5) {
                        vacjume.setRightRollerSpeed(1);
                    }
                    else if (toolPad.getRawAxis(GamepadConstants.RIGHT_ANALOG_Y) > 0.5){
                        vacjume.setRightRollerSpeed(-0.7);
                    }
                    else {
                        vacjume.setRightRollerSpeed(0);
                    }

                    if (toolPad.getRawAxis(GamepadConstants.LEFT_ANALOG_Y) < -0.5) {
                        vacjume.setLeftRollerSpeed(1);
                    }
                    else if (toolPad.getRawAxis(GamepadConstants.LEFT_ANALOG_Y) > 0.5){
                        vacjume.setLeftRollerSpeed(-0.7);
                    }
                    else {
                        vacjume.setLeftRollerSpeed(0);
                    }
                }
            else {
                if (toolPad.getRawAxis(GamepadConstants.RIGHT_ANALOG_Y) > 0.5) {
                    vacjume.setRightRollerSpeed(-0.7);
                }
                else if(!(toolPad.getRawAxis(GamepadConstants.RIGHT_ANALOG_Y) < -0.5) 
                                                || vacjume.rightDiskDetected()) {
                    vacjume.setRightRollerSpeed(Constants.getDouble("IntakeRollerBrakePWM"));
                }
                else if (toolPad.getRawAxis(GamepadConstants.RIGHT_ANALOG_Y) < -0.5) {
                    vacjume.setRightRollerSpeed(teleopIntakeSpeed);
                }

                if (toolPad.getRawAxis(GamepadConstants.LEFT_ANALOG_Y) > 0.5) {
                    vacjume.setLeftRollerSpeed(-0.7);
                }
                else if (!(toolPad.getRawAxis(GamepadConstants.LEFT_ANALOG_Y) < -0.5) 
                                                    || vacjume.leftDiskDetected()) {
                    vacjume.setLeftRollerSpeed(Constants.getDouble("IntakeRollerBrakePWM"));
                }
                else if (toolPad.getRawAxis(GamepadConstants.LEFT_ANALOG_Y) < -0.5) {
                    vacjume.setLeftRollerSpeed(teleopIntakeSpeed);
                }
            }
            
            shooter.setShooterAngle(toolPad.getRawButton(GamepadConstants.START_BUTTON),
            toolPad.getRawButton(GamepadConstants.X_BUTTON), 
            toolPad.getRawButton(GamepadConstants.B_BUTTON),
            toolPad.getRawButton(GamepadConstants.BACK_BUTTON));
                
            shooter.setAutomaticWiper(toolPad.getRawButton(GamepadConstants.RIGHT_TRIGGER), 0.4);
        }
        
        shooter.shootFrisbeesTeleop(backPyramidPreset, fullCourtPreset, 
                toolPad.getRawButton(GamepadConstants.X_BUTTON), //BackPyramidPreset 
                toolPad.getRawButton(GamepadConstants.B_BUTTON), //FullCourtShot
                toolPad.getRawButton(GamepadConstants.RIGHT_BUMPER), //Load Frisbee
                toolPad.getRawAxis(GamepadConstants.DPAD_Y) == -1, //Increment setpoint
                toolPad.getRawAxis(GamepadConstants.DPAD_Y) == 1,  //Decrement setpoint
                toolPad.getRawButton(GamepadConstants.A_BUTTON), //turn shooter off
                toolPad.getRawButton(GamepadConstants.BACK_BUTTON),
                toolPad.getRawButton(GamepadConstants.X_BUTTON)); 
        
//        shooter.shooterSpeedTesterButtons (toolPad.getRawButton(GamepadConstants.X_BUTTON), 
//                toolPad.getRawButton(GamepadConstants.Y_BUTTON) , 
//                toolPad.getRawButton(GamepadConstants.B_BUTTON),  
//                toolPad.getRawButton(GamepadConstants.A_BUTTON));
        
        //shooter.loadFrisbee(toolPad.getRawButton(GamepadConstants.RIGHT_BUMPER), frisbeeLoadDelay, false, false);

        
            
/**************************DriverStation LCD***********************************/
        dsLCD.println(DriverStationLCD.Line.kUser1, 1, "Left Encoder: "
                + driveTrain.getLeftEncoderDist() + "      ");

        dsLCD.println(DriverStationLCD.Line.kUser2, 1, "Right Encoder: "
                + driveTrain.getRightEncoderDist() + "    ");

        dsLCD.println(DriverStationLCD.Line.kUser3, 1, "Gyro: "
                + driveTrain.getGyroAngle() + "    ");
        
        dsLCD.println(DriverStationLCD.Line.kUser4, 1, "Vacjume Pot: "
                + vacjume.getVacjumePot() + "    ");
        
        dsLCD.println(DriverStationLCD.Line.kUser5, 1, "Shooter RPS: "
                + shooter.getEncRPS() + "    ");
        
        dsLCD.println(DriverStationLCD.Line.kUser6, 1, "L: "
                + vacjume.leftDiskDetected() + " " + "R: " 
                + vacjume.rightDiskDetected() + "    ");
                

        if ((lcdUpdateCycle % 50) == 0) {
            dsLCD.updateLCD();
        } else {
            lcdUpdateCycle++;
        }
        
        /**************************DriverStation LCD****************************
        /*--------------------------------------------------------------------*/
        
        /**************************SmartDashBoard******************************/
        if((smartDSUpdateCycle % 50 == 0)) {
            SmartDashboard.putNumber("Left Analog Y", drivePad.getRawAxis(GamepadConstants.LEFT_ANALOG_Y));
            SmartDashboard.putNumber("Right Analog Y", drivePad.getRawAxis(GamepadConstants.RIGHT_ANALOG_Y));
            SmartDashboard.putNumber("Left Analog X", drivePad.getRawAxis(GamepadConstants.LEFT_ANALOG_X));
            SmartDashboard.putNumber("Right Analog X", drivePad.getRawAxis(GamepadConstants.RIGHT_ANALOG_X));
            SmartDashboard.putNumber("ShooterEnc RPS", shooter.getEncRPS());
            SmartDashboard.putNumber("Bang Bang PWM", shooter.bangBangPWM);
            SmartDashboard.putBoolean("Shooter Ready", shooter.shooterReady);
            SmartDashboard.putNumber("Shooter Setpoint", shooter.setpoint);
            SmartDashboard.putBoolean("Left Intake Limit", vacjume.leftDiskDetected());
            SmartDashboard.putBoolean("Right Intake Limit", vacjume.rightDiskDetected());   
            SmartDashboard.putBoolean("Gear Shift", driveTrain.gearShifterState);
            SmartDashboard.putNumber("Left Encoder", driveTrain.getLeftEncoderDist());
            SmartDashboard.putNumber("Right Encoder", driveTrain.getRightEncoderDist());
            SmartDashboard.putBoolean("Hanger Position", hanger.hangerUp);
            SmartDashboard.putNumber("Avg Drive Enc", (driveTrain.getLeftEncoderDist() + driveTrain.getRightEncoderDist()) / 2);
            SmartDashboard.putNumber("Avg Shooter Enc RPS", shooter.getAvgEncRPS());
        }
        else {
            smartDSUpdateCycle++;
        }

        /**************************SmartDashBoard*******************************
        /*--------------------------------------------------------------------*/
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    }
}
