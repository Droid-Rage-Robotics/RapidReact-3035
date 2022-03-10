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
      // private final Intake intake = new Intake(); 
      private final ClimberNoEncoder climber = new ClimberNoEncoder(); 

      private String hi;
      
      private Controllers controllers = new Controllers.ControllerBuilder()
        .driver()
          .add("outtake", XboxTrigger.LT)
          .add("intake", XboxTrigger.RT)
          .add("intakeLift", XboxButton.RB)
        .operator()
          .add("extendClimber", XboxDPAD.UP)
          .add("retractClimber", XboxDPAD.DOWN)
          .add("shootLow", XboxButton.LB)
          .add("shootStop", XboxButton.RB)
        .build();

  // Driver and Operaator Joystick
      // private static Joystick driverStick = new Joystick(0);
      // private static Joystick operatorStick = new Joystick(1);


  // Indexer Up and Down - Operator
      // private Trigger opIndexerUp = new Trigger(() -> operatorStick.getRawAxis(3) < -0.2);      //Right Trigger
      // private Trigger opIndexerDown = new Trigger(() -> operatorStick.getRawAxis(2) < -0.2);    //Left Trigger
      //() -> - lamda


  /* The container for the robot. Contains subsystems, OI devices, and commands.*/
      public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
        initDefaultCommands();
      }

      public void initDefaultCommands() {
        drivetrain.initDefaultCommands(
            () -> controllers.getDriver().getLeftY(), 
            () -> controllers.getDriver().getRightX()
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
        //Intake and outtake
        // controllers.get("intake").whenActive(command).whenInactive(command)
            // controllers.get("intake").whenActive(intake::intakeBalls).whenInactive(intake::disableIntake);    //Intake
            // controllers.get("outtake").whenActive(intake::outtakeBalls).whenInactive(intake::disableIntake);   //Outtake
        
        //In and Out Intake
          

        //Extend and Contract Climber
            controllers.get("extendClimber").whenActive(climber::extend).whenInactive(climber::disable);      //Extend
            controllers.get("retractClimber").whenActive(climber::extend).whenInactive(climber::retract);    //Retract
        
        //Shooter On and Off
            controllers.get("shootLow").whenActive(shooter::shootLow);    //Low
            // controllers.get("shootHigh").whenActive(shooter::shootHigh);  //High
            // controllers.get("shootFullSpeed").whenActive(shooter::fullSpeed);  //Low
            controllers.get("shootStop").whenActive(shooter::disable);   //Off

        //Indexer Up and Down
            //opIndexerUp.whenActive();     //Indexer Up
            //opIndexerDown.whenActive();   //Indexer Down
        
        
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