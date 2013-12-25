/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theory6.main;

import theory6.subsystems.DriveTrain;
import theory6.auton.*;
import theory6.auton.commands.*;

import theory6.utilities.Constants;

                                                                                                                
/**
 *
 * @author Sagar
 */
public class AutonSequences {
        
    DriveTrain driveTrain = DriveTrain.getInstance();
    
   public AutonController startAnyWhere3Disk() { //finalized sequence
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();   

        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("centerAutonShooterSetpoint"), 
                                                              Constants.getDouble("centerAutonShooterFrisbeeCount"), 
                                                              Constants.getDouble("centerAutonShooterTimeout")));
        return ac;
    }
   
    public AutonController startAnywhere3DiskDriveStraightBack() { //finalized sequence
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();   

        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("centerAutonShooterSetpoint"), 
                                                              Constants.getDouble("centerAutonShooterFrisbeeCount"), 
                                                              Constants.getDouble("centerAutonShooterTimeout")));
        
        ac.addCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("startAnywhereDriveBackDist"), //new
                                                     0, //angle goal
                                                     Constants.getDouble("startAnywhereDriveBackDistTimeout"))); //new
        return ac;
    }  
    
    public AutonController centerPyramid3DiskDriveLeftField() { //finalized sequence
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();   

        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("centerAutonShooterSetpoint"), 
                                                              Constants.getDouble("centerAutonShooterFrisbeeCount"), 
                                                              Constants.getDouble("centerAutonShooterTimeout")));
        
        ac.addCommand(new PivotTurnThenDriveTimeoutCommand(Constants.getDouble("centerToLeftFieldAngle"), 
                                                           Constants.getDouble("centerToLeftFieldDist"),  
                                                           Constants.getDouble("centerToLeftFieldAngleTimeout")));
        return ac;
    }  
    
    public AutonController centerPyramid3DiskDriveRightField() { //finalized sequence
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();   

        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("centerAutonShooterSetpoint"), 
                                                              Constants.getDouble("centerAutonShooterFrisbeeCount"), 
                                                              Constants.getDouble("centerAutonShooterTimeout")));
        
        ac.addCommand(new PivotTurnThenDriveTimeoutCommand(Constants.getDouble("centerToRightFieldAngle"), 
                                                           Constants.getDouble("centerToRightFieldDist"),  
                                                           Constants.getDouble("centerToRightFieldAngleTimeout")));
        return ac;
    }  
    
    public AutonController centerPyramid5DiskCenterLineOld() {
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance(); 
        
        //low gear with hanger up
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("centerAutonShooterSetpoint"), 
                                                              Constants.getDouble("centerAutonShooterFrisbeeCount"), 
                                                              Constants.getDouble("centerAutonShooterTimeout")));
        ac.addCommand(new SetShooterAngleCommand());
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV1TimeOutCommand(Constants.getDouble("5DiskOldFirstBackDriveDist"),
               0,Constants.getDouble("5DiskOldFirstBackDriveDistTimeout")), 0.75, new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"),2)));
        
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV1TimeOutCommand(Constants.getDouble("5DiskOldIntakeDist"),
              0,Constants.getDouble("5DiskOldIntakeDistTimeout")), 0.0, new IntakeTimeOutCommand(0.7, 3)));
        
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV1TimeOutCommand(Constants.getDouble("5DiskOldForwardDist"),
                0,Constants.getDouble("5DiskOldForwardDistTimeout")), 0.1, new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacFeedPos"), 2)));
        
        ac.addCommand(new RightIntakeTimeOutCommand(1, Constants.getDouble("rightIntakeFeedTime")));
        ac.addCommand(new WaitCommand(0.2));
        ac.addCommand(new LeftIntakeTimeOutCommand(1, Constants.getDouble("leftIntakeFeedTime")));
        ac.addCommand(new SetShooterAngleCommand());
        ac.addCommand(new SetWiperPosCommand());
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("centerAutonShooterSetpoint"), 
                                                              Constants.getDouble("centerAutonShooterFrisbeeCount"), 
                                                              Constants.getDouble("centerAutonShooterTimeout")));

        return ac;
    }
    
    public AutonController centerPyramid5DiskCenterLineOptimized() {
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance(); 
        
        //high gear
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("centerAutonShooterSetpoint"), 
                                                              Constants.getDouble("centerAutonShooterFrisbeeCount"), 
                                                              Constants.getDouble("centerAutonShooterTimeout")));
        
        ac.addCommand(new SetShooterAngleCommand()); //low angle
        
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("5DiskNewFirstBackDriveDist"),
                                                    0,Constants.getDouble("5DiskNewFirstBackDriveDistTimeout")), 
                                                    0.75, 
                                                    new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"),2)));
        
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV1TimeOutCommand(Constants.getDouble("5DiskNewIntakeDist"),
                                                    0,Constants.getDouble("5DiskNewIntakeDistTimeout")), 
                                                    0.0, 
                                                    new IntakeTimeOutCommand(0.7, 3)));
        
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV1TimeOutCommand(Constants.getDouble("5DiskNewForwardDist"),
                0,Constants.getDouble("5DiskNewForwardDistTimeout")), 0.0, new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacFeedPos"),2)));
        
        ac.addCommand(new TwoParallelMotionsCommand (new RightIntakeTimeOutCommand(1, Constants.getDouble("rightIntakeFeedTime")), 
                                                     0.4, //delay between Right Intake and Left Intake
                                                     new LeftIntakeTimeOutCommand(1, Constants.getDouble("leftIntakeFeedTime"))));
        
        ac.addCommand(new SetWiperPosCommand());
        
        ac.addCommand(new TwoParallelMotionsCommand(new SetShooterAngleCommand(), 0, new SetWiperPosCommand()));
        
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("centerAutonShooterSetpoint"), 
                                                              Constants.getDouble("centerAutonShooterFrisbeeCount"), 
                                                              Constants.getDouble("centerAutonShooterTimeout")));
        
        return ac;
    }
    
    public AutonController centerPyramid5DiskUnderPyramidDriveBack() { 
        AutonController ac = new AutonController();
        ac.clear();
        
        //high gear with hanger down (back plate taken off)
        Constants.getInstance();
        
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("centerAutonShooterSetpoint"), 
                                                              Constants.getDouble("centerAutonShooterFrisbeeCount"), 
                                                              Constants.getDouble("centerAutonShooterTimeout")));
        
        ac.addCommand(new SetShooterAngleCommand()); //angle down
        
        ac.addCommand(new TwoParallelMotionsCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("5DiskPyr180Angle"), 
                                                                                  Constants.getDouble("5DiskPyr180AngleTimeout")), 
                                                                                  0, //delay time between commands 
                                                    new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 1)));
        
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV1TimeOutCommand(Constants.getDouble("5DiskPyrIntakeDist"),
                                                                                 0,
                                                                                 Constants.getDouble("5DiskPyrIntakeDistTimeout")), 
                                                                                 0, 
                                                                                 new IntakeTimeOutCommand(0.7, 3)));
        
        ac.addCommand(new TwoParallelMotionsCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("5DiskPyrTurnBack180Angle"), 
                                                                                  Constants.getDouble("5DiskPyrTurnBack180AngleTimeout")), 
                                                                                  0, //delay time between commands 
                                                    new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacFeedPos"), 1)));        
        
        ac.addCommand (new TurnShooterOnCommand(0.7));
        
        ac.addCommand(new TwoParallelMotionsCommand (new RightIntakeTimeOutCommand(1, Constants.getDouble("rightIntakeFeedTime")), 
                                                     0.4, //delay between Right Intake and Left Intake
                                                     new LeftIntakeTimeOutCommand(1, Constants.getDouble("leftIntakeFeedTime"))));
        
        ac.addCommand(new SetWiperPosCommand());
        ac.addCommand(new SetWiperPosCommand());
        
        ac.addCommand(new WaitCommand(0.2));
        ac.addCommand(new SetShooterAngleCommand());
        
        ac.addCommand(new WaitCommand(0.2));
        
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("5DiskInPyramidSetpoint"), 
                                                              Constants.getDouble("5DiskInPyramidFrisbeeCount"), 
                                                              Constants.getDouble("5DiskInPyramidTimeout")));
        
        ac.addCommand(new DriveToPosV1TimeOutCommand(Constants.getDouble("5DiskPyrDriveBackDist"),
                                                     Constants.getDouble("5DiskPyrDriveBackDistAngle"), //drive angle goal
                                                     Constants.getDouble("5DiskPyrDriveBackDistTimeout")));  
        
        return ac;
       }
    
    public AutonController centerPyramid5DiskUnderPyramidDriveLeft() { 
        AutonController ac = new AutonController();
        ac.clear();
        
        //high gear with hanger down (back plate taken off)
        Constants.getInstance();
        
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("centerAutonShooterSetpoint"), 
                                                              Constants.getDouble("centerAutonShooterFrisbeeCount"), 
                                                              Constants.getDouble("centerAutonShooterTimeout")));
        
        ac.addCommand(new SetShooterAngleCommand()); //angle down
        
        ac.addCommand(new TwoParallelMotionsCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("5DiskPyr180Angle"), 
                                                                                  Constants.getDouble("5DiskPyr180AngleTimeout")), 
                                                                                  0, //delay time between commands 
                                                    new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 1)));
        
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV1TimeOutCommand(Constants.getDouble("5DiskPyrIntakeDist"),
                                                                                 0,
                                                                                 Constants.getDouble("5DiskPyrIntakeDistTimeout")), 
                                                                                 0, 
                                                                                 new IntakeTimeOutCommand(0.7, 3)));
        
        ac.addCommand(new TwoParallelMotionsCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("5DiskPyrTurnBack180Angle"), 
                                                                                  Constants.getDouble("5DiskPyrTurnBack180AngleTimeout")), 
                                                                                  0, //delay time between commands 
                                                    new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacFeedPos"), 1)));        
        
        ac.addCommand (new TurnShooterOnCommand(0.7));
        
        ac.addCommand(new TwoParallelMotionsCommand (new RightIntakeTimeOutCommand(1, Constants.getDouble("rightIntakeFeedTime")), 
                                                     0.4, //delay between Right Intake and Left Intake
                                                     new LeftIntakeTimeOutCommand(1, Constants.getDouble("leftIntakeFeedTime"))));
        
        ac.addCommand(new SetWiperPosCommand());
        ac.addCommand(new SetWiperPosCommand());
        
        ac.addCommand(new WaitCommand(0.2));
        ac.addCommand(new SetShooterAngleCommand());
        
        ac.addCommand(new WaitCommand(0.2));
        
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("5DiskInPyramidSetpoint"), 
                                                              Constants.getDouble("5DiskInPyramidFrisbeeCount"), 
                                                              Constants.getDouble("5DiskInPyramidTimeout")));
        
        ac.addCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("5DiskPyrTurnLeft50Angle"), 
                                                                                  Constants.getDouble("5DiskPyrTurnLeft50AngleTimeout")));
        
        ac.addCommand(new DriveToPosV1TimeOutCommand(Constants.getDouble("5DiskPyrDriveBackLeftDist"),
                                                     Constants.getDouble("5DiskPyrDriveBackLeftDistAngle"), //drive angle goal
                                                     Constants.getDouble("5DiskPyrDriveBackLeftDistTimeout")));  
        
        return ac;
       }
    
    public AutonController centerPyramid5DiskUnderPyramidDriveRight() { 
        AutonController ac = new AutonController();
        ac.clear();
        
        //high gear with hanger down (back plate taken off)
        Constants.getInstance();
        
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("centerAutonShooterSetpoint"), 
                                                              Constants.getDouble("centerAutonShooterFrisbeeCount"), 
                                                              Constants.getDouble("centerAutonShooterTimeout")));
        
        ac.addCommand(new SetShooterAngleCommand()); //angle down
        
        ac.addCommand(new TwoParallelMotionsCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("5DiskPyr180Angle"), 
                                                                                  Constants.getDouble("5DiskPyr180AngleTimeout")), 
                                                                                  0, //delay time between commands 
                                                    new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 1)));
        
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV1TimeOutCommand(Constants.getDouble("5DiskPyrIntakeDist"),
                                                                                 0,
                                                                                 Constants.getDouble("5DiskPyrIntakeDistTimeout")), 
                                                                                 0, 
                                                                                 new IntakeTimeOutCommand(0.7, 3)));
        
        ac.addCommand(new TwoParallelMotionsCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("5DiskPyrTurnBack180Angle"), 
                                                                                  Constants.getDouble("5DiskPyrTurnBack180AngleTimeout")), 
                                                                                  0, //delay time between commands 
                                                    new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacFeedPos"), 1)));        
        
        ac.addCommand (new TurnShooterOnCommand(0.7));
        
        ac.addCommand(new TwoParallelMotionsCommand (new RightIntakeTimeOutCommand(1, Constants.getDouble("rightIntakeFeedTime")), 
                                                     0.4, //delay between Right Intake and Left Intake
                                                     new LeftIntakeTimeOutCommand(1, Constants.getDouble("leftIntakeFeedTime"))));
        
        ac.addCommand(new SetWiperPosCommand());
        ac.addCommand(new SetWiperPosCommand());
        
        ac.addCommand(new WaitCommand(0.2));
        ac.addCommand(new SetShooterAngleCommand());
        
        ac.addCommand(new WaitCommand(0.2));
        
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("5DiskInPyramidSetpoint"), 
                                                              Constants.getDouble("5DiskInPyramidFrisbeeCount"), 
                                                              Constants.getDouble("5DiskInPyramidTimeout")));
        
        ac.addCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("5DiskPyrTurnRight50Angle"), 
                                                                                  Constants.getDouble("5DiskPyrTurnRight50AngleTimeout")));
        
        ac.addCommand(new DriveToPosV1TimeOutCommand(Constants.getDouble("5DiskPyrDriveBackRightDist"),
                                                     Constants.getDouble("5DiskPyrDriveBackRightDistAngle"), //drive angle goal
                                                     Constants.getDouble("5DiskPyrDriveBackRightDistTimeout")));  
        
        return ac;
       }
    
      
    public AutonController centerPyramid5DiskUnderPyramidNoDriveBack() { 
        AutonController ac = new AutonController();
        ac.clear();
        
        //high gear with hanger down (back plate taken off)
        Constants.getInstance();
        
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("centerAutonShooterSetpoint"), 
                                                              Constants.getDouble("centerAutonShooterFrisbeeCount"), 
                                                              Constants.getDouble("centerAutonShooterTimeout")));
        
        ac.addCommand(new SetShooterAngleCommand()); //angle down
        
        ac.addCommand(new TwoParallelMotionsCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("5DiskPyr180Angle"), 
                                                                                  Constants.getDouble("5DiskPyr180AngleTimeout")), 
                                                                                  0, //delay time between commands 
                                                    new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 1)));
        
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV1TimeOutCommand(Constants.getDouble("5DiskPyrIntakeDist"),
                                                                                 0,
                                                                                 Constants.getDouble("5DiskPyrIntakeDistTimeout")), 
                                                                                 0, 
                                                                                 new IntakeTimeOutCommand(0.7, 3)));
        
        ac.addCommand(new TwoParallelMotionsCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("5DiskPyrTurnBack180Angle"), 
                                                                                  Constants.getDouble("5DiskPyrTurnBack180AngleTimeout")), 
                                                                                  0, //delay time between commands 
                                                    new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacFeedPos"), 1)));        
        
        ac.addCommand (new TurnShooterOnCommand(0.7));
        
        ac.addCommand(new TwoParallelMotionsCommand (new RightIntakeTimeOutCommand(1, Constants.getDouble("rightIntakeFeedTime")), 
                                                     0.4, //delay between Right Intake and Left Intake
                                                     new LeftIntakeTimeOutCommand(1, Constants.getDouble("leftIntakeFeedTime"))));
        
        ac.addCommand(new SetWiperPosCommand());
        ac.addCommand(new SetWiperPosCommand());
        
        ac.addCommand(new WaitCommand(0.2));
        ac.addCommand(new SetShooterAngleCommand());
        
        ac.addCommand(new WaitCommand(0.2));
        
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("5DiskInPyramidSetpoint"), 
                                                              Constants.getDouble("5DiskInPyramidFrisbeeCount"), 
                                                              Constants.getDouble("5DiskInPyramidTimeout")));
                
        return ac;
       }
    public AutonController noAuton() {
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();   
        
        return ac;
        
    }
    public AutonController testAutonTurn() { 
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();   
        
        ac.addCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("testAngleGoal"), 
                                                    Constants.getDouble("testAngleTimeout"))); 
        return ac;
    }  
       
    public AutonController testAutonDriveV1() { //NEW CONSTANTS UPDATE!
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();   

    /*    ac.addCommand(new DriveToPosV1TimeOutCommand(Constants.getDouble("testDriveDistV1"), 
                                                     Constants.getDouble("testDriveAngleV1"), //angle goal
                                                     Constants.getDouble("testDriveDistTimeoutV1"))); */
        
        
        ac.addCommand(new DriveToPosOneEncoderTimeOutCommand(Constants.getDouble("testDriveDistV1"), 
                                                     Constants.getDouble("testDriveAngleV1"), //angle goal
                                                     Constants.getDouble("testDriveDistTimeoutV1"))); 
        return ac;
    }
    
    public AutonController testAutonDriveV2() { 
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();   

        ac.addCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("testDriveDistV2"), //new
                                                     Constants.getDouble("testDriveAngleV2"), //angle goal
                                                     Constants.getDouble("testDriveDistTimeoutV2"))); //new
                
        return ac;
    }
    
    public AutonController testAutonDriveAndPickUp() { 
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();   

        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("testDriveAndPickupDist"), //new
                                                                                   Constants.getDouble("testDriveAndPickupAngle"), //angle goal
                                                                                   Constants.getDouble("testDriveAndPickupTimeout")), 
                                                                                   0, 
                                                                                   new IntakeTimeOutCommand(0.7, 3))); //new
                
        return ac;
    }
            
    public AutonController testAutonTurnWithVacjume() { 
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();   
        
//        
//         ac.addCommand(new TwoParallelMotionsCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("leftPyr5CenterAngle"), 
//                                                                    Constants.getDouble("leftPyr5CenterAngleTimeout")), 0, new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 
//                                                                                 Constants.getDouble("Misc-VacTimeout"))));
         
//        ac.addCommand(new TwoParallelMotionsCommand(new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 
//                                                                                 Constants.getDouble("Misc-VacTimeout")), 0,  new TurnDegreesTimeOutCommand(Constants.getDouble("leftPyr5CenterAngle"), 
//                                                    Constants.getDouble("leftPyr5CenterAngleTimeout"))));

        ac.addCommand(new TwoParallelMotionsCommand(new TurnDegreesVacDownTimeOutCommand(Constants.getDouble("testTurnWithVacAngle"), 
                                                                                  Constants.getDouble("testTurnWithVacTimeout")), 
                                                                                  0, //delay between commands
                                                    new VacjumePosTimeOutCommand(Constants.getDouble("testTurnVacPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout"))));
        return ac;
    }
     
    public AutonController testAutonDriveWithVacjume() { 
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();   

        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("testDriveWithVacDist"), 
                                                                                   Constants.getDouble("testDriveWithVacAngle"),
                                                                                   Constants.getDouble("testDriveWithVacTimeout")), 
                                                                                  0, //delay between commands
                                                    new VacjumePosTimeOutCommand(Constants.getDouble("testDriveWithVacPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout"))));
        return ac;
    }
     
    public AutonController testAutonPivotThenDrive () {
        AutonController ac = new AutonController();
        ac.clear();
        
        ac.addCommand(new PivotTurnThenDriveTimeoutCommand(Constants.getDouble("testPivThenDriveAng"), 
                                                           Constants.getDouble("testPivThenDriveBackDist"),  
                                                           Constants.getDouble("testPivThenDriveDistAngTimeout")));
                
        Constants.getInstance(); 
        
        
        
        return ac;
    }
    
    public AutonController testVacjume() { 
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();   

        ac.addCommand(new VacjumePosTimeOutCommand(Constants.getDouble("testVacPos"), 
                                                   Constants.getDouble("Misc-VacTimeout")));
        return ac;
    }
    
    public AutonController testVacjumeUpAndFeedSeperate() { 
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();   

        ac.addCommand(new VacjumePosTimeOutCommand(Constants.getDouble("testVacUp"), 
                                                   Constants.getDouble("Misc-VacTimeout")));
        
        ac.addCommand(new TwoParallelMotionsCommand (new RightIntakeTimeOutCommand(1, Constants.getDouble("rightIntakeFeedTime")), 
                                                     0.4, //delay between Right Intake and Left Intake
                                                     new LeftIntakeTimeOutCommand(1, Constants.getDouble("leftIntakeFeedTime"))));
        
        
        return ac;
    }
     
    public AutonController testVacjumeUpAndFeedOneCommand() { 
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();   

        ac.addCommand(new VacjumeUpAndFeedTimeoutCommand());
        
        return ac;
    }
     
    public AutonController rightPyr5DiskCenterLine() { 
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();
                        
        ac.addCommand(new PivotTurnThenDriveTimeoutCommand(Constants.getDouble("rightPyr5PivAng"), 
                                                           Constants.getDouble("rightPyr5LongBackDist"),  
                                                           Constants.getDouble("rightPyr5LongDistAngTimeout")));
        
        //bring vac down as driving back, leave shooter on
        ac.addCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("rightPyr5CenterAngle"), 
                                                    Constants.getDouble("rightPyr5CenterAngleTimeout")));
        ac.addCommand(new WaitCommand(1)); //shoot 1 disk
        //low angle
        ac.addCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("rightPyr5PickupDist"), 
                                                     Constants.getDouble("rightPyr5PickupAngle"), 
                                                     Constants.getDouble("rightPyr5PickupDistTimeout"))); //and intake disks
        
        ac.addCommand(new WaitCommand(1)); //shoot 1 disk
        
        ac.addCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("rightPyr5ForwardDist"), 
                                                     Constants.getDouble("rightPyr5ForwardAngle"), 
                                                     Constants.getDouble("rightPyr5ForwardDistTimeout"))); //drive back and bring vac up
        
//        ac.addCommand(new PivotTurnThenDriveTimeoutCommand(Constants.getDouble("rightPyr5FinalPivAng"), 
//                                                           Constants.getDouble("rightPyr5FinalLongBackDist"),  
//                                                           Constants.getDouble("rightPyr5FinalLongDistAngTimeout")));
        
        ac.addCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("rightPyr5FinalPivAng"), 2));
        
        ac.addCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("rightPyr5FinalLongBackDist"), 
                                                     0, 
                                                     Constants.getDouble("rightPyr5FinalLongDistAngTimeout")));
        //feed both disks
        //angle up
        //fire 4 disks

        return ac;
    }
    
    public AutonController leftPyr5DiskCenterLine() { 
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();
                        
        ac.addCommand(new PivotTurnThenDriveTimeoutCommand(Constants.getDouble("leftPyr5PivAng"), 
                                                           Constants.getDouble("leftPyr5LongBackDist"),  
                                                           Constants.getDouble("leftPyr5LongDistAngTimeout")));
        //bring vac down as driving back, leave shooter on
        ac.addCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("leftPyr5CenterAngle"), 
                                                    Constants.getDouble("leftPyr5CenterAngleTimeout")));
        ac.addCommand(new WaitCommand(1)); //shoot 1 disk
        //low angle
        ac.addCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("leftPyr5PickupDist"), 
                                                     Constants.getDouble("leftPyr5PickupAngle"), 
                                                     Constants.getDouble("leftPyr5PickupDistTimeout"))); //and intake disks
        
        ac.addCommand(new WaitCommand(1)); //shoot 1 disk
        
        ac.addCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("leftPyr5ForwardDist"), 
                                                     Constants.getDouble("leftPyr5ForwardAngle"), 
                                                     Constants.getDouble("leftPyr5ForwardDistTimeout"))); //drive back and bring vac up
        
        ac.addCommand(new PivotTurnThenDriveTimeoutCommand(Constants.getDouble("leftPyr5FinalPivAng"), 
                                                           Constants.getDouble("leftPyr5FinalLongBackDist"),  
                                                           Constants.getDouble("leftPyr5FinalLongDistAngTimeout")));
        //feed both disks
        //angle up
        //fire 4 disks

        return ac;
    }
        
    public AutonController rightPyr5DiskCenterLineFinal() { 
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();
       ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("rightPyr5LongBackDist"), 
                                                     0, 
                                                     Constants.getDouble("rightPyr5LongDistAngTimeout")),0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), 
                                                                                                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout"))));
        
        
        ac.addCommand(new TwoParallelMotionsCommand(new TurnDegreesVacDownTimeOutCommand(Constants.getDouble("rightPyr5CenterAngle"), 
                                                                    Constants.getDouble("rightPyr5CenterAngleTimeout")), 0, new TwoParallelMotionsCommand(new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout")), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), 
                                                                                                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout")))));

        
        ac.addCommand(new TwoParallelMotionsCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), 
                                                              Constants.getDouble("cornerPyr5FirstFrisCount"), 
                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout")), 0, new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout")))); //shoot 1 disk
         
        
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("rightPyr5PickupDist"), 
                                                     Constants.getDouble("rightPyr5PickupAngle"), 
                                                     Constants.getDouble("rightPyr5PickupDistTimeout")), 
                                                     0, //delay between drive and intake
                                                     new TwoParallelMotionsCommand(new IntakeTimeOutCommand(Constants.getDouble("autonIntakeSpeed"), 3), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout"))))); //and intake disks
        ac.addCommand(new TwoParallelMotionsCommand(new SetShooterAngleCommand(), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout"))));//low angle

        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("rightPyr5ForwardDist"), 
                                                    Constants.getDouble("rightPyr5ForwardAngle"), 
                                                    Constants.getDouble("rightPyr5ForwardDistTimeout")), 
                                                    0,
                                                    new TwoParallelMotionsCommand(new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacFeedPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout")), 0, new TwoParallelMotionsCommand(new SetHangerPistonsCommand(), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout")))))); //drive back and bring vac up

        ac.addCommand(new TwoParallelMotionsCommand(new TwoParallelMotionsCommand(new RightIntakeTimeOutCommand(1, Constants.getDouble("rightIntakeFeedTime")), 
                                                     0.4, //delay between Right Intake and Left Intake
                                                     new LeftIntakeTimeOutCommand(1, Constants.getDouble("leftIntakeFeedTime"))), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), Constants.getDouble("cornerPyr5SecondFrisCountTimeout"))));
        
        ac.addCommand(new TwoParallelMotionsCommand (new WaitCommand(Constants.getDouble("rightPyr5ShotDelay")), 0, new TwoParallelMotionsCommand(new SetShooterAngleCommand(), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout")))));
         
        ac.addCommand(new TwoParallelMotionsCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                              Constants.getDouble("cornerPyr5SecondFrisCount"), 
                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout")), 0, new TwoParallelMotionsCommand(new SetWiperPosCommand(), 0.6, new SetWiperPosCommand()))); //shoot 4 disks
        return ac;
    }
    
    public AutonController rightPyr5DiskCenterLineFinalDriveRight() { 
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("rightPyr5LongBackDist"), 
                                                     0, 
                                                     Constants.getDouble("rightPyr5LongDistAngTimeout")),0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), 
                                                                                                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout"))));
        
        
        ac.addCommand(new TwoParallelMotionsCommand(new TurnDegreesVacDownTimeOutCommand(Constants.getDouble("rightPyr5CenterAngle"), 
                                                                    Constants.getDouble("rightPyr5CenterAngleTimeout")), 0, new TwoParallelMotionsCommand(new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout")), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), 
                                                                                                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout")))));

        
        ac.addCommand(new TwoParallelMotionsCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), 
                                                              Constants.getDouble("cornerPyr5FirstFrisCount"), 
                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout")), 0, new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout")))); //shoot 1 disk
         
        
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("rightPyr5PickupDist"), 
                                                     Constants.getDouble("rightPyr5PickupAngle"), 
                                                     Constants.getDouble("rightPyr5PickupDistTimeout")), 
                                                     0, //delay between drive and intake
                                                     new TwoParallelMotionsCommand(new IntakeTimeOutCommand(Constants.getDouble("autonIntakeSpeed"), 3), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout"))))); //and intake disks
        ac.addCommand(new TwoParallelMotionsCommand(new SetShooterAngleCommand(), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout"))));//low angle

        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("rightPyr5ForwardDist"), 
                                                    Constants.getDouble("rightPyr5ForwardAngle"), 
                                                    Constants.getDouble("rightPyr5ForwardDistTimeout")), 
                                                    0,
                                                    new TwoParallelMotionsCommand(new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacFeedPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout")), 0, new TwoParallelMotionsCommand(new SetHangerPistonsCommand(), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout")))))); //drive back and bring vac up

        ac.addCommand(new TwoParallelMotionsCommand(new TwoParallelMotionsCommand(new RightIntakeTimeOutCommand(1, Constants.getDouble("rightIntakeFeedTime")), 
                                                     0.4, //delay between Right Intake and Left Intake
                                                     new LeftIntakeTimeOutCommand(1, Constants.getDouble("leftIntakeFeedTime"))), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), Constants.getDouble("cornerPyr5SecondFrisCountTimeout"))));
        
        ac.addCommand(new TwoParallelMotionsCommand (new WaitCommand(Constants.getDouble("rightPyr5ShotDelay")), 0, new TwoParallelMotionsCommand(new SetShooterAngleCommand(), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout")))));
         
        ac.addCommand(new TwoParallelMotionsCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                              Constants.getDouble("cornerPyr5SecondFrisCount"), 
                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout")), 0, new TwoParallelMotionsCommand(new SetWiperPosCommand(), 0.6, new SetWiperPosCommand()))); //shoot 4 disks
         ac.addCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("rightPyr5FinalPivAng"), Constants.getDouble("rightPyr5FinalPivAngTimeOut")));
        
        ac.addCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("rightPyr5FinalLongBackDist"), 
                                                     0, 
                                                     Constants.getDouble("rightPyr5FinalLongDistAngTimeout")));

        return ac;
    }
    
    public AutonController rightPyr5DiskCenterLineFinalDriveLeft() { 
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();
         ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("rightPyr5LongBackDist"), 
                                                     0, 
                                                     Constants.getDouble("rightPyr5LongDistAngTimeout")),0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), 
                                                                                                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout"))));
        
        
        ac.addCommand(new TwoParallelMotionsCommand(new TurnDegreesVacDownTimeOutCommand(Constants.getDouble("rightPyr5CenterAngle"), 
                                                                    Constants.getDouble("rightPyr5CenterAngleTimeout")), 0, new TwoParallelMotionsCommand(new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout")), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), 
                                                                                                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout")))));

        
        ac.addCommand(new TwoParallelMotionsCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), 
                                                              Constants.getDouble("cornerPyr5FirstFrisCount"), 
                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout")), 0, new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout")))); //shoot 1 disk
         
        
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("rightPyr5PickupDist"), 
                                                     Constants.getDouble("rightPyr5PickupAngle"), 
                                                     Constants.getDouble("rightPyr5PickupDistTimeout")), 
                                                     0, //delay between drive and intake
                                                     new TwoParallelMotionsCommand(new IntakeTimeOutCommand(Constants.getDouble("autonIntakeSpeed"), 3), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout"))))); //and intake disks
        ac.addCommand(new TwoParallelMotionsCommand(new SetShooterAngleCommand(), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout"))));//low angle

        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("rightPyr5ForwardDist"), 
                                                    Constants.getDouble("rightPyr5ForwardAngle"), 
                                                    Constants.getDouble("rightPyr5ForwardDistTimeout")), 
                                                    0,
                                                    new TwoParallelMotionsCommand(new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacFeedPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout")), 0, new TwoParallelMotionsCommand(new SetHangerPistonsCommand(), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout")))))); //drive back and bring vac up

        ac.addCommand(new TwoParallelMotionsCommand(new TwoParallelMotionsCommand(new RightIntakeTimeOutCommand(1, Constants.getDouble("rightIntakeFeedTime")), 
                                                     0.4, //delay between Right Intake and Left Intake
                                                     new LeftIntakeTimeOutCommand(1, Constants.getDouble("leftIntakeFeedTime"))), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), Constants.getDouble("cornerPyr5SecondFrisCountTimeout"))));
        
        ac.addCommand(new TwoParallelMotionsCommand (new WaitCommand(Constants.getDouble("rightPyr5ShotDelay")), 0, new TwoParallelMotionsCommand(new SetShooterAngleCommand(), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout")))));
         
        ac.addCommand(new TwoParallelMotionsCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                              Constants.getDouble("cornerPyr5SecondFrisCount"), 
                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout")), 0, new TwoParallelMotionsCommand(new SetWiperPosCommand(), 0.6, new SetWiperPosCommand()))); //shoot 4 disks
         ac.addCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("leftPyr5FinalPivAng"), Constants.getDouble("leftPyr5FinalPivAngTimeOut")));
        
        ac.addCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("leftPyr5FinalLongBackDist"), 
                                                     0, 
                                                     Constants.getDouble("leftPyr5FinalLongDistAngTimeout")));

        return ac;
    }
    
    public AutonController leftPyr5DiskCenterLineFinal() { 
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("leftPyr5LongBackDist"), 
                                                     0, 
                                                     Constants.getDouble("leftPyr5LongDistAngTimeout")),0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), 
                                                                                                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout"))));
        
        
        ac.addCommand(new TwoParallelMotionsCommand(new TurnDegreesVacDownTimeOutCommand(Constants.getDouble("leftPyr5CenterAngle"), 
                                                                    Constants.getDouble("leftPyr5CenterAngleTimeout")), 0, new TwoParallelMotionsCommand(new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout")), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), 
                                                                                                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout")))));

        
        ac.addCommand(new TwoParallelMotionsCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), 
                                                              Constants.getDouble("cornerPyr5FirstFrisCount"), 
                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout")), 0, new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout")))); //shoot 1 disk
         
        
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("leftPyr5PickupDist"), 
                                                     Constants.getDouble("leftPyr5PickupAngle"), 
                                                     Constants.getDouble("leftPyr5PickupDistTimeout")), 
                                                     0, //delay between drive and intake
                                                     new TwoParallelMotionsCommand(new IntakeTimeOutCommand(Constants.getDouble("autonIntakeSpeed"), 3), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout"))))); //and intake disks
        ac.addCommand(new TwoParallelMotionsCommand(new SetShooterAngleCommand(), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout"))));//low angle

        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("leftPyr5ForwardDist"), 
                                                    Constants.getDouble("leftPyr5ForwardAngle"), 
                                                    Constants.getDouble("leftPyr5ForwardDistTimeout")), 
                                                    0,
                                                    new TwoParallelMotionsCommand(new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacFeedPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout")), 0, new TwoParallelMotionsCommand(new SetHangerPistonsCommand(), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout")))))); //drive back and bring vac up

        ac.addCommand(new TwoParallelMotionsCommand(new TwoParallelMotionsCommand(new RightIntakeTimeOutCommand(1, Constants.getDouble("rightIntakeFeedTime")), 
                                                     0.4, //delay between Right Intake and Left Intake
                                                     new LeftIntakeTimeOutCommand(1, Constants.getDouble("leftIntakeFeedTime"))), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), Constants.getDouble("cornerPyr5SecondFrisCountTimeout"))));
        
        ac.addCommand(new TwoParallelMotionsCommand (new WaitCommand(Constants.getDouble("leftPyr5ShotDelay")), 0, new TwoParallelMotionsCommand(new SetShooterAngleCommand(), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout")))));
         
        ac.addCommand(new TwoParallelMotionsCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                              Constants.getDouble("cornerPyr5SecondFrisCount"), 
                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout")), 0, new TwoParallelMotionsCommand(new SetWiperPosCommand(), 0.6, new SetWiperPosCommand()))); //shoot 4 disks


        return ac;
    }
    
    public AutonController leftPyr5DiskCenterLineFinalDriveLeft() { 
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();
               
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("leftPyr5LongBackDist"), 
                                                     0, 
                                                     Constants.getDouble("leftPyr5LongDistAngTimeout")),0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), 
                                                                                                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout"))));
        
        
        ac.addCommand(new TwoParallelMotionsCommand(new TurnDegreesVacDownTimeOutCommand(Constants.getDouble("leftPyr5CenterAngle"), 
                                                                    Constants.getDouble("leftPyr5CenterAngleTimeout")), 0, new TwoParallelMotionsCommand(new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout")), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), 
                                                                                                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout")))));

        
        ac.addCommand(new TwoParallelMotionsCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), 
                                                              Constants.getDouble("cornerPyr5FirstFrisCount"), 
                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout")), 0, new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout")))); //shoot 1 disk
         
        
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("leftPyr5PickupDist"), 
                                                     Constants.getDouble("leftPyr5PickupAngle"), 
                                                     Constants.getDouble("leftPyr5PickupDistTimeout")), 
                                                     0, //delay between drive and intake
                                                     new TwoParallelMotionsCommand(new IntakeTimeOutCommand(Constants.getDouble("autonIntakeSpeed"), 3), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout"))))); //and intake disks
        ac.addCommand(new TwoParallelMotionsCommand(new SetShooterAngleCommand(), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout"))));//low angle

        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("leftPyr5ForwardDist"), 
                                                    Constants.getDouble("leftPyr5ForwardAngle"), 
                                                    Constants.getDouble("leftPyr5ForwardDistTimeout")), 
                                                    0,
                                                    new TwoParallelMotionsCommand(new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacFeedPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout")), 0, new TwoParallelMotionsCommand(new SetHangerPistonsCommand(), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout")))))); //drive back and bring vac up

        ac.addCommand(new TwoParallelMotionsCommand(new TwoParallelMotionsCommand(new RightIntakeTimeOutCommand(1, Constants.getDouble("rightIntakeFeedTime")), 
                                                     0.4, //delay between Right Intake and Left Intake
                                                     new LeftIntakeTimeOutCommand(1, Constants.getDouble("leftIntakeFeedTime"))), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), Constants.getDouble("cornerPyr5SecondFrisCountTimeout"))));
        
        ac.addCommand(new TwoParallelMotionsCommand (new WaitCommand(Constants.getDouble("leftPyr5ShotDelay")), 0, new TwoParallelMotionsCommand(new SetShooterAngleCommand(), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout")))));
         
        ac.addCommand(new TwoParallelMotionsCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                              Constants.getDouble("cornerPyr5SecondFrisCount"), 
                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout")), 0, new TwoParallelMotionsCommand(new SetWiperPosCommand(), 0.6, new SetWiperPosCommand()))); //shoot 4 disks
        ac.addCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("leftPyr5FinalPivAng"), Constants.getDouble("leftPyr5FinalPivAngTimeOut")));
        
        ac.addCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("leftPyr5FinalLongBackDist"), 
                                                     0, 
                                                     Constants.getDouble("leftPyr5FinalLongDistAngTimeout")));

        return ac;
    }
    
    public AutonController leftPyr5DiskCenterLineFinalDriveRight() { 
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();

        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("leftPyr5LongBackDist"), 
                                                     0, 
                                                     Constants.getDouble("leftPyr5LongDistAngTimeout")),0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), 
                                                                                                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout"))));
        
        
        ac.addCommand(new TwoParallelMotionsCommand(new TurnDegreesVacDownTimeOutCommand(Constants.getDouble("leftPyr5CenterAngle"), 
                                                                    Constants.getDouble("leftPyr5CenterAngleTimeout")), 0, new TwoParallelMotionsCommand(new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout")), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), 
                                                                                                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout")))));

        
        ac.addCommand(new TwoParallelMotionsCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), 
                                                              Constants.getDouble("cornerPyr5FirstFrisCount"), 
                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout")), 0, new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout")))); //shoot 1 disk
         
        
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("leftPyr5PickupDist"), 
                                                     Constants.getDouble("leftPyr5PickupAngle"), 
                                                     Constants.getDouble("leftPyr5PickupDistTimeout")), 
                                                     0, //delay between drive and intake
                                                     new TwoParallelMotionsCommand(new IntakeTimeOutCommand(Constants.getDouble("autonIntakeSpeed"), 3), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5FirstFrisCountTimeout"))))); //and intake disks
        ac.addCommand(new TwoParallelMotionsCommand(new SetShooterAngleCommand(), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout"))));//low angle

        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("leftPyr5ForwardDist"), 
                                                    Constants.getDouble("leftPyr5ForwardAngle"), 
                                                    Constants.getDouble("leftPyr5ForwardDistTimeout")), 
                                                    0,
                                                    new TwoParallelMotionsCommand(new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacFeedPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout")), 0, new TwoParallelMotionsCommand(new SetHangerPistonsCommand(), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout")))))); //drive back and bring vac up

        ac.addCommand(new TwoParallelMotionsCommand(new TwoParallelMotionsCommand(new RightIntakeTimeOutCommand(1, Constants.getDouble("rightIntakeFeedTime")), 
                                                     0.4, //delay between Right Intake and Left Intake
                                                     new LeftIntakeTimeOutCommand(1, Constants.getDouble("leftIntakeFeedTime"))), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpoint"), Constants.getDouble("cornerPyr5SecondFrisCountTimeout"))));
        
        ac.addCommand(new TwoParallelMotionsCommand (new WaitCommand(Constants.getDouble("leftPyr5ShotDelay")), 0, new TwoParallelMotionsCommand(new SetShooterAngleCommand(), 0, new ParallelBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                                                                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout")))));
         
        ac.addCommand(new TwoParallelMotionsCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("cornerPyr5ShooterSetpointFinal"), 
                                                              Constants.getDouble("cornerPyr5SecondFrisCount"), 
                                                              Constants.getDouble("cornerPyr5SecondFrisCountTimeout")), 0, new TwoParallelMotionsCommand(new SetWiperPosCommand(), 0.6, new SetWiperPosCommand()))); //shoot 4 disks

         ac.addCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("rightPyr5FinalPivAng"), Constants.getDouble("rightPyr5FinalPivAngTimeOut")));
        
        ac.addCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("rightPyr5FinalLongBackDist"), 
                                                     0, 
                                                     Constants.getDouble("rightPyr5FinalLongDistAngTimeout")));

        return ac;
    }
    public AutonController testAuton() { 
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();
        
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(48,4,4));
        
        
        
        
        return ac;
    }
    
    public AutonController centerPyramid7DiskHersheyAuton() { //NEW CONSTANTS UPDATE!
        AutonController ac = new AutonController();
        ac.clear();
   
        Constants.getInstance();   
        
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("3-ShooterSetpoint"), 
                                                              Constants.getDouble("3-ShooterFrisbeeCount"), 
                                                              Constants.getDouble("3-ShooterTimeout")));
        
//                
        ac.addCommand(new TwoParallelMotionsCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("7V1-First180Turn"), 
                                                                                  Constants.getDouble("7V1-First180Timeout")), 
                                                                                  0, //delay between commands
                                                    new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 1)));
        
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("7V1-StartToCenterPDist"),
                                                    0,//angle goal
                                                    Constants.getDouble("7V1-StartToCenterPDistTimeout")), 
                                                    0, //delay between drive and intake
                                                    new TwoParallelMotionsCommand(new IntakeTimeOutCommand(Constants.getDouble("7V1-FirstIntakeSpeed"), 3), 0, new SetShooterAngleCommand())));
        
        ac.addCommand(new TwoParallelMotionsCommand(new DriveSetSpeedAndDistanceTimeOutCommand(Constants.getDouble("7V1-FirstDiskFeedPWM"), Constants.getDouble("7V1-FirstDiskFeedDist"), 
                                                                    Constants.getDouble("7V1-FirstDiskFeedTimeout")), 0,new VacjumeUpAndFeedTimeoutCommand()) );
        
        ac.addCommand(new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacGroundPos"), 1));
    
        ac.addCommand(new TwoParallelMotionsCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("7V1-SecondDisksPickUpDist1"),
                                                    0,//angle goal
                                                    Constants.getDouble("7V1-SecondDisksPickUpDist1Timeout")), 
                                                    0, //delay between drive and intake
                                                    new IntakeTimeOutCommand(Constants.getDouble("7V1-SecondIntakeSpeed"), 3)), 0, new SetWiperPosCommand()));
//        
        ac.addCommand(new TwoParallelMotionsCommand(new TurnDegreesTimeOutCommand(Constants.getDouble("7V1-Second180Turn"), 
                                                                                  Constants.getDouble("7V1-Second180Timeout")), 
                                                                                  0, //delay between commands
                                                    new VacjumePosTimeOutCommand(Constants.getDouble("Misc-VacFeedPos"), 
                                                                                 Constants.getDouble("Misc-VacTimeout"))));
//        
        ac.addCommand(new TurnShooterOnCommand(Constants.getDouble("7V1-SecondShotRevUp")));
//        
        ac.addCommand(new TwoParallelMotionsCommand(new DriveToPosV2TimeOutCommand(Constants.getDouble("7V1-FrontPToBackPDist"),
                                                    0,//angle goal
                                                    Constants.getDouble("7V1-FrontPToBackPDistTimeout")), 
                                                    0, //delay between drive and intake
                                                    new TwoParallelMotionsCommand (new VacjumeUpAndFeedTimeoutCommand(), 
                                                                                   1, new SetWiperPosCommand())));
        ac.addCommand(new SetShooterAngleCommand());
//       
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(Constants.getDouble("7V1-SecondShotSetpoint"), 
                                                              Constants.getDouble("7V1-SecondShotFrisbeeCount"), 
                                                              Constants.getDouble("7V1-SecondShotTimeout")));
        return ac;
    }  
     
    
    public AutonController firstTest()
    {
        AutonController ac = new AutonController();
        ac.clear();
        //Shoot three, back up, turn right 90*, drive back more, turn left 45*, drive back, pick up, drive forward, shoot.
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(48, 3, 4));
        ac.addCommand(new TurnDegreesTimeOutCommand(90, 4));
        ac.addCommand(new DriveToPosV2TimeOutCommand(-100, 0, 4));
        ac.addCommand(new TurnDegreesTimeOutCommand(-45, 4));
        ac.addCommand(new VacjumePosTimeOutCommand(400, 4));
        ac.addCommand(new IntakeTimeOutCommand(1, 3));
        ac.addCommand(new DriveToPosV2TimeOutCommand(-7, 0, 4));
        ac.addCommand(new VacjumeUpAndFeedTimeoutCommand());
        ac.addCommand(new SetShooterAngleCommand());
        ac.addCommand(new SetWiperPosCommand());
        ac.addCommand(new DriveToPosV2TimeOutCommand(107, 0, 4));
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(48, 2, 4));
        
        
        
        return ac;
    }
    public AutonController secondTest()
    {
        AutonController ac = new AutonController();
        ac.clear();
        //Shoot three, back up, turn right 90*, drive back more, turn left 45*, drive back, pick up, drive forward, shoot.
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(48, 3, 4));
        ac.addCommand(new PivotTurnThenDriveTimeoutCommand(90, -100, 6));
        ac.addCommand(new TurnDegreesTimeOutCommand(-45, 4));
        ac.addCommand(new VacjumePosTimeOutCommand(400, 4));
        ac.addCommand(new IntakeTimeOutCommand(1, 3));
        ac.addCommand(new DriveToPosV2TimeOutCommand(-7, 0, 4));
        ac.addCommand(new TwoParallelMotionsCommand(new VacjumeUpAndFeedTimeoutCommand(), 5, new SetShooterAngleCommand()));
        ac.addCommand(new TwoParallelMotionsCommand(new SetWiperPosCommand(), 5, new DriveToPosV2TimeOutCommand(107, 0, 4)));
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(48, 2, 4));
        
        
        
        return ac;
    }
    public AutonController thirdTest()
    {
        AutonController ac = new AutonController();
        ac.clear();
        
        ac.addCommand(new ShootFrisbeesBangBangTimeOutCommand(48, 3, 5));
        ac.addCommand(new PivotTurnThenDriveTimeoutCommand(45, -50, 5));
        ac.addCommand(new TurnDegreesTimeOutCommand(45, 5));
        
        return ac;
        
    }
}

