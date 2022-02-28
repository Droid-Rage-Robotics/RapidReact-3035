// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.Intake.IntakeCommand;
import frc.robot.commands.Intake.IntakeCommand.IntakeType;
import frc.robot.commands.Autos.NormalAuto;
import frc.robot.commands.Autos.StraightLineTest;
import frc.robot.commands.Climber.ClimberCommand;
import frc.robot.commands.Climber.ClimberCommand.ClimberMotionType;

import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.*;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem drivetrain = new DriveSubsystem();
  private final ShooterSubsystem shooter = new ShooterSubsystem();
  private final IndexerSubsystem indexer = new IndexerSubsystem(); 
  private final IntakeSubsystem intake = new IntakeSubsystem(); 
  private final ClimberNoEncoderSubsystem climber = new ClimberNoEncoderSubsystem(); 
  

  private static Joystick driverStick = new Joystick(0);
  private static Joystick operatorStick = new Joystick(1);


  private Trigger driverOuttake = new Trigger(() -> operatorStick.getRawAxis(2) > 0.2);
  private Trigger driverIntake = new Trigger(() -> operatorStick.getRawAxis(3) < -0.2);


  private JoystickButton opClimberRetractButton = new JoystickButton(operatorStick, 4); //Y Button
  private JoystickButton opClimberExtendButton = new JoystickButton(operatorStick, 2);  //B Button

  private JoystickButton opShoot = new JoystickButton(driverStick, 5); //LB
  private JoystickButton opShootStop = new JoystickButton(driverStick, 6); //TB

  private Trigger opIndexerUp = new Trigger(() -> operatorStick.getRawAxis(3) < -0.2);  //Right Trigger
  private Trigger opIndexerDown = new Trigger(() -> operatorStick.getRawAxis(2) < -0.2);  //Left Trigger
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    initDefaultCommands();
  }

  public void initDefaultCommands() {
    drivetrain.initDefaultCommands(driverStick);
}

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //Intake and outtake
    driverIntake.whenActive(intake::intakeBalls).whenInactive(intake::disable);    //Intake
    driverOuttake.whenActive(intake::outtakeBalls).whenInactive(intake::disable);   //Outtake

    //Extend and Contract Climber
    opClimberExtendButton.whileHeld(climber::extend).whenInactive(climber::disable);
    opClimberRetractButton.whileHeld(climber::retract).whenInactive(climber::disable);

    opShoot.whileHeld(shooter::shootLow);
    opShootStop.whileHeld(shooter::disable);

    

    //Indexer Up and Down
    //opIndexerUp.whenActive();    //Indexer Up
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