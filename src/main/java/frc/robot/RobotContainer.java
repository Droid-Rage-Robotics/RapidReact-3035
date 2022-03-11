// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import javax.naming.ldap.Control;

// import frc.robot.commands.Intake.IntakeCommand;
// import frc.robot.commands.Intake.IntakeCommand.IntakeType;
// import frc.robot.commands.Climber.ClimberCommand;
// import frc.robot.commands.Climber.ClimberCommand.ClimberMotionType;
// import frc.robot.subsystems.*;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ControllerConstants;
import frc.robot.Controls.XboxButton;
import frc.robot.Controls.XboxDPAD;
import frc.robot.Controls.XboxTrigger;
import frc.robot.commands.Autos.NormalAuto;
import frc.robot.commands.Autos.StraightLineTest;

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
      private XboxController operatorController = new XboxController(0);

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
        drivetrain.initDefaultCommands(
            () -> driverController.getLeftY(), 
            () -> driverController.getRightX()
          );
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
          .addCommandsToController(0)
            .add("intake", RT)
          
              .whenActive(intake::lift, intake) // TODO: get this working
              .whenActive(intake::intakeBalls, intake)
              .whenActive(indexer::intakeFrontIndexer, indexer)

              .whenInactive(intake::disableIntake, intake)
              .whenInactive(indexer::disableFrontIndexer, indexer)
                       
            .add("outtake", LT)
              .whenActive(intake::lift, intake)
              .whenActive(indexer::outtakeBothIndexer, indexer)
              .whenActive(intake::outtakeBalls, intake)
              
              .whenInactive(indexer::disableBothIndexer, indexer)
              .whenInactive(intake::disableIntake, intake)
              
            .add("intakeDrop", LB)
              .whenActive(intake::drop, intake)
            .add("intakeLift", RB)
              .whenActive(intake::lift, intake)
                    .finish()


        //controller 1  
        .addCommandsToController(1)
          .add("shoot", A)
            .whileActiveContinuous(shooter::shootLow, shooter)
            .whenInactive(shooter::disable, shooter)
          .add("shoot", B)
            .whileActiveContinuous(shooter::shootHigh, shooter)
            .whenInactive(shooter::disable, shooter)
          .add("shoot", X)
            .whileActiveContinuous(shooter::sendIt, shooter)
            .whenInactive(shooter::disable, shooter)
        
            // TODO: sequential commandgroup
            //one button "revs up shooter-> shooter check if correct rpm -> both indexer motors feed into the shooter for 5 sec"
            //there needs to be 2 of these for low and high
            

            .add("climberExtend", DPAD_UP)
              .whenActive(climber::extend)

              .whenInactive(climber::disable)

            .add("climberRetract", DPAD_DOWN)
              .whenActive(climber::retract)

              .whenInactive(climber::disable)
          .finish();
      }

  
      
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public NormalAuto getNormalauto() {
    return new NormalAuto(drivetrain);
  }

  public Command getNothingAuto(){
    return new InstantCommand(() -> drivetrain.tankDriveVolts(0,0));
  }

  public Command getStraightLineAuto(){
    return new StraightLineTest(drivetrain);
  }
}