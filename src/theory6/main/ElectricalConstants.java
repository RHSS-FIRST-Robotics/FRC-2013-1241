/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.main;

/**
 *
 * @author Sagar
 */
public class ElectricalConstants {
    
    //**************************************************************************
    //****************************** PWMs **************************************
    //**************************************************************************        
    
    public static final int HANGER_MOTOR_1_PWM                  = 1;
    public static final int HANGER_MOTOR_2_PWM                  = 2;
    public static final int FRONT_RIGHT_DRIVE_PWM               = 3; 
    public static final int BACK_RIGHT_DRIVE_PWM                = 3;
    public static final int SHOOTER_MOTOR_1_PWM                 = 4; //4 on real robot
    public static final int SHOOTER_MOTOR_2_PWM                 = 4; //4 on real robot
    public static final int DROP_DOWN_MOTOR_1_PWM               = 5; //5 on real robot
    public static final int DROP_DOWN_MOTOR_2_PWM               = 5; //5 on real robot
    public static final int FRONT_LEFT_DRIVE_PWM                = 6; 
    public static final int BACK_LEFT_DRIVE_PWM                 = 6;
    public static final int LEFT_INTAKE_PWM                     = 7; 
    public static final int RIGHT_INTAKE_PWM                    = 8;

    //**************************************************************************
    //***************************Analog Sensors*********************************
    //**************************************************************************

    public static final int DRIVE_GYRO_PORT                     = 1;
    public static final int DROP_DOWN_POT                       = 2;
    public static final int INTAKE_LEFT_SENSOR                  = 3;
    public static final int INTAKE_RIGHT_SENSOR                 = 4;
    
    //**************************************************************************
    //*************************** ENCODERS *************************************
    //**************************************************************************
    
    public static final int LEFT_DRIVE_ENC_A                    = 1; 
    public static final int LEFT_DRIVE_ENC_B                    = 2; 
    public static final int RIGHT_DRIVE_ENC_A                   = 4; 
    public static final int RIGHT_DRIVE_ENC_B                   = 3; 
    public static final int SHOOTER_ENC_A                       = 5; 
    public static final int SHOOTER_ENC_B                       = 6; 
    
    //**************************************************************************
    //*************************** Digital Sensors ******************************
    //**************************************************************************
    public static final int COMPRESSOR_PRESSURE_SENSOR          = 7;
    public static final int LEFT_ROLLER_LIMIT                   = 9;
    public static final int RIGHT_ROLLER_LIMIT                  = 10;
    
    //******************UPDATE THESE CONSTANTS WITH REAL ROBOT******************
    public static final int driveWheelRadius = 2;//wheel radius in inches
    public static final int pulsePerRotation = 256; //encoder pulse per rotation, all other black grayhills are 256
    public static final double gearRatio = 1/1; //ratio between wheel and encoder
    public static final double driveEncoderPulsePerRot = pulsePerRotation*gearRatio; //pulse per rotation * gear ratio
    public static final double driveEncoderDistPerTick =(Math.PI*2*driveWheelRadius)/driveEncoderPulsePerRot;
    public static final boolean rightDriveTrainEncoderReverse = false; //true on real
    public static final boolean leftDriveTrainEncoderReverse = false; //true on real

   //***************************************************************************
   //*************************** Pneumatics ************************************
   //***************************************************************************
    
    //Solenoid Breakout 1 (Module 3 on cRIO)
    public static final int SHIFTER_EXTEND                      = 1;
    public static final int SHIFTER_RETRACT                     = 2;
    public static final int FRISBEE_LOADING_EXTEND              = 3;
    public static final int FRISBEE_LOADING_RETRACT             = 4;
    public static final int SHOOTER_ANGLE_EXTEND                = 6;
    public static final int SHOOTER_ANGLE_RETRACT               = 5;
    public static final int HANGER_EXTEND                       = 7;
    public static final int HANGER_RETRACT                      = 8;
    
    //Solenoid Breakout 2 (Module 4 on cRIO)
    
    public static final int WIPER_PISTON_EXTEND                 = 1;
    public static final int WIPER_PISTON_RETRACT                = 2;
            
    
   //***************************************************************************
   //*************************** Relays ****************************************
   //***************************************************************************
    
    public static final int COMPRESSOR_RELAY                    = 1;
    public static final int BLUE_LED_RELAY                      = 2;
    public static final int RED_LED_RELAY                       = 3;
    public static final int GREEN_LED_RELAY                     = 4;
}