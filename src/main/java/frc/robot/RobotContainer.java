// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// import javax.naming.ldap.Control;

import edu.wpi.first.cameraserver.CameraServer;

// import frc.robot.commands.Intake.IntakeCommand;
// import frc.robot.commands.Intake.IntakeCommand.IntakeType;
// import frc.robot.commands.Climber.ClimberCommand;
// import frc.robot.commands.Climber.ClimberCommand.ClimberMotionType;
// import frc.robot.subsystems.*;

import edu.wpi.first.wpilibj.GenericHID;
// import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.Autos.ForwardAndShootLow;
// import edu.wpi.first.wpilibj2.command.button.JoystickButton;
// import edu.wpi.first.wpilibj2.command.button.Trigger;
// import frc.robot.Constants.ControllerConstants;
// import frc.robot.Controls.XboxButton;
// import frc.robot.Controls.XboxDPAD;
// import frc.robot.Controls.XboxTrigger;
import frc.robot.commands.Autos.NormalAuto;
import frc.robot.commands.Autos.ShootOneBall;
import frc.robot.commands.Autos.StraightLineTest;
import frc.robot.commands.Shooter.ShootingSequence;
import frc.robot.subsystems.ClimberNoEncoder;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;

// import frc.robot.subsystems;
import static frc.robot.Controls.XboxButton.*;
import static frc.robot.Controls.XboxDPAD.*;
import static frc.robot.Controls.XboxTrigger.*;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  //Defined Robot Subsystems
      private final Drive drivetrain = new Drive();
      private final Shooter shooter = new Shooter();
      private final Indexer indexer = new Indexer(); 
      private final Intake intake = new Intake(); 
      private final ClimberNoEncoder climber = new ClimberNoEncoder(); 

      private XboxController driverController = new XboxController(0);
      private XboxController operatorController = new XboxController(1);

      private Controllers controllers = new Controllers(
        driverController,
        operatorController
      );



  /* The container for the robot. Contains subsystems, OI devices, and commands.*/
      public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
        initDefaultCommands();
      }

      public void initDefaultCommands() {
        // drivetrain.initDefaultCommands(
        //     () -> driverController.getLeftY(), 
        //     () -> driverController.getRightX()
        // );
        CameraServer.startAutomaticCapture();
      }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */

  //Add Commands to Buttons
      private void configureButtonBindings() {
        controllers

        //controller 0
        .addCommandsToControllerPort(0)
          .add("intake", RT)    //Intake and Indexer
            .whenActive(intake::lift, intake)
            .whenActive(intake::intake, intake)
            .whenActive(indexer::intakeFrontIndexer, indexer)

            .whenInactive(intake::stopIntake, intake)
            .whenInactive(indexer::disableFrontIndexer, indexer)
                      
          .add("outtake", LT)   //Outtake 
            .whenActive(intake::lift, intake)
            .whenActive(indexer::outtakeBothIndexer, indexer)
            .whenActive(intake::outtake, intake)
            
            .whenInactive(indexer::stopBothIndexer, indexer)
            .whenInactive(intake::stopIntake, intake)
            
          .add("intakeDrop", DPAD_DOWN)   //Intake Down
            .whenActive(intake::drop, intake)
            
          .add("intakeLift", DPAD_UP)   //Intake Up
            .whenActive(intake::lift, intake)

          .add("slowMode", RB)    //Slow Mode
            .whenActive(drivetrain::slowDrive, drivetrain)
            .whenInactive(drivetrain::normalDrive, drivetrain)

          .add("turboMode", LB)   //Turbo Mode
            .whenActive(drivetrain::turboDrive, drivetrain)
            .whenInactive(drivetrain::normalDrive, drivetrain)


          
            .add("shootLow", X)   //Low
            .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::shootLow), true)

            .whenInactive(shooter::stop, shooter)

            .whenInactive(indexer::stopBothIndexer, indexer)
          .add("shootHigh", B)    //High
            .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::shootHigh), true)

            .whenInactive(shooter::stop, shooter)
            .whenInactive(indexer::stopBothIndexer, indexer)

          .add("sendIt", Y)   //(No PID COntrol, Percent Output instead)
            .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::sendIt), true)
            
            .whenInactive(shooter::stop, shooter)
            .whenInactive(indexer::stopBothIndexer, indexer)


          .add("addRPM", DPAD_RIGHT)
            .whenActive(shooter::addRPM, shooter)
          .add("subtractRPM", DPAD_LEFT)
            .whenInactive(shooter::subtractRPM, shooter)
          .finish()



        //controller 1  
        .addCommandsToControllerPort(1)
          .add("shootLow", X)   //Low
            .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::shootLow), true)

            .whenInactive(shooter::stop, shooter)

            .whenInactive(indexer::stopBothIndexer, indexer)
          .add("shootHigh", B)    //High
            .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::shootHigh), true)

            .whenInactive(shooter::stop, shooter)
            .whenInactive(indexer::stopBothIndexer, indexer)

          .add("sendIt", Y)   //(No PID COntrol, Percent Output instead)
            .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::sendIt), true)
            
            .whenInactive(shooter::stop, shooter)
            .whenInactive(indexer::stopBothIndexer, indexer)

          .add("climberExtend", DPAD_UP)    //Climber Up
            .whenActive(climber::extend)

            .whenInactive(climber::disable)

          .add("climberRetract", DPAD_DOWN)   //Climber Down
            .whenActive(climber::retract)

            .whenInactive(climber::disable)

          .add("indexerUp", RB)   //Indexer Up
            .whenActive(indexer::intakeFrontIndexer, indexer)

            .whenInactive(indexer::stopBothIndexer, indexer)

          .add("indexerDown", LB)   //Indexer Down
            .whenActive(indexer::outtakeBackIndexer, indexer)

            .whenActive(indexer::stopBothIndexer, indexer)
          .finish();
      }

  
      
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getNormalauto() {
    return new NormalAuto(drivetrain);

  }

  public Command getNothingAuto(){
    return new InstantCommand(() -> drivetrain.tankDriveVolts(0,0));
  }

  public Command getStraightLineAuto(){
    return new StraightLineTest(drivetrain);
  }

  public Command getShootAndThatsItCommand() {
    return new ShootingSequence(shooter, indexer, shooter::shootHigh);
  }

  public Command getForwardAndShootLowCommand() {
    return new ForwardAndShootLow(drivetrain, shooter, indexer);
  }

  public Command getShootOneBallCommand() {
    return new ShootOneBall(drivetrain, shooter, indexer, intake);
  }
}