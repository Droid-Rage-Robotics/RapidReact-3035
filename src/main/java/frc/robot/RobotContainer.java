// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Controllers.Controls.XboxButton.B;
import static frc.robot.Controllers.Controls.XboxButton.LB;
import static frc.robot.Controllers.Controls.XboxButton.RB;
import static frc.robot.Controllers.Controls.XboxButton.X;
import static frc.robot.Controllers.Controls.XboxButton.Y;
import static frc.robot.Controllers.Controls.XboxDPAD.DPAD_DOWN;
import static frc.robot.Controllers.Controls.XboxDPAD.DPAD_LEFT;
import static frc.robot.Controllers.Controls.XboxDPAD.DPAD_RIGHT;
import static frc.robot.Controllers.Controls.XboxDPAD.DPAD_UP;
import static frc.robot.Controllers.Controls.XboxTrigger.LT;
import static frc.robot.Controllers.Controls.XboxTrigger.RT;

import edu.wpi.first.cameraserver.CameraServer;
// import javax.naming.ldap.Control;
// import frc.robot.commands.Intake.IntakeCommand;
// import frc.robot.commands.Intake.IntakeCommand.IntakeType;
// import frc.robot.commands.Climber.ClimberCommand;
// import frc.robot.commands.Climber.ClimberCommand.ClimberMotionType;
// import frc.robot.subsystems.*;

// import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj2.command.button.JoystickButton;
// import edu.wpi.first.wpilibj2.command.button.Trigger;

// import frc.robot.Constants.ControllerConstants;
// import frc.robot.Controls.XboxButton;
// import frc.robot.Controls.XboxDPAD;
// import frc.robot.Controls.XboxTrigger;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Controllers.Controllers;
import frc.robot.commands.Autos.ForwardAndShootLow;
import frc.robot.commands.Autos.IntakeAndShoot;
import frc.robot.commands.Autos.NormalAuto;
import frc.robot.commands.Autos.GoodShoot;
import frc.robot.commands.Autos.StraightLineTest;
import frc.robot.commands.Shooter.ShootingSequence;
import frc.robot.subsystems.ClimberNoEncoder;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;


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
        initTeleopCommands();
      }

      public void initTeleopCommands() {
        drivetrain.initDefaultCommands(
            () -> driverController.getLeftY(), 
            () -> driverController.getRightX()
        );
        CameraServer.startAutomaticCapture();
      }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */

  //Add Commands to Buttons
      public void configureButtonBindings() {
        controllers

        /** 
         *        CONTROLLER 0
         */

        .addCommandsToControllerPort(0)
          .add("intake", RT)    //Intake and Indexer
            .whenActive(intake::lift, intake)
            .whenActive(intake::intake, intake)
            // .whenActive(indexer::intakeFrontIndexer, indexer)

            .whenInactive(intake::stopIntake, intake)
            // .whenInactive(indexer::disableFrontIndexer, indexer)
                      
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
          .add("far", B)    //High
            .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::shootFarHigh), true)
            

            .whenInactive(shooter::stop, shooter)
            .whenInactive(indexer::stopBothIndexer, indexer)

          .add("close", Y)   //(No PID COntrol, Percent Output instead)
            .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::shootCloseHigh), true)
            
            .whenInactive(shooter::stop, shooter)
            .whenInactive(indexer::stopBothIndexer, indexer)


          .add("addRPM", DPAD_RIGHT)
            .whenActive(shooter::addRPM, shooter)
          .add("subtractRPM", DPAD_LEFT)
            .whenInactive(shooter::subtractRPM, shooter)
          .finish()



        /** 
         *        CONTROLLER 1 
         */

        .addCommandsToControllerPort(1)
          .add("addRPM", DPAD_RIGHT)
            .whenActive(shooter::addRPM, shooter)

          .add("subtractRPM", DPAD_LEFT)
            .whenInactive(shooter::subtractRPM, shooter)

          .add("shootLow", X)   //Low
            .whileActiveContinuous(shooter::shootLow)
            .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::shootLow), true)
            // .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::shootLow), true)

            .whenInactive(shooter::stop, shooter)

            .whenInactive(indexer::stopBothIndexer, indexer)

          .add("shootHigh", B)    //High
          .whileActiveContinuous(shooter::shootCloseHigh)
          .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::shootFarHigh), true)
            // .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::shootHigh), true)

            .whenInactive(shooter::stop, shooter)
            .whenInactive(indexer::stopBothIndexer, indexer)

          .add("sendIt", Y)   //(No PID COntrol, Percent Output instead)
            .whileActiveContinuous(shooter::shootFarHigh)
            .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::shootCloseHigh), true)
            
            .whenInactive(shooter::stop, shooter)
            .whenInactive(indexer::stopBothIndexer, indexer)

          .add("climberExtend", DPAD_UP)    //Climber Up
            .whenActive(climber::extend)

            .whenInactive(climber::stop)

          .add("climberRetract", DPAD_DOWN)   //Climber Down
            .whenActive(climber::retract)

            .whenInactive(climber::stop)

          .add("indexerUp", RB)   //Indexer Up
            .whenActive(indexer::intakeFrontIndexer, indexer)

            .whenInactive(indexer::stopBothIndexer, indexer)

          .add("indexerUpBoth", LB)   //Indexer Up Both
            .whenActive(indexer::intakeBothIndexer, indexer)

            .whenInactive(indexer::stopBothIndexer, indexer)


          .add("indexerDown", RT)   //Indexer Down
            .whenActive(indexer::outtakeBothIndexer, indexer)

            .whenInactive(indexer::stopBothIndexer, indexer)

          // .add("shootIndexer", RT)
          //   .whenActive(new ShootingSequenceforIndexer(indexer))



          // .whenInactive(indexer::stopBothIndexer, indexer)

          // .add("shootIn", LB)   //Indexer Down
          //   .whenActive(indexer::outtakeBothIndexer, indexer)

          //   .whenInactive(indexer::stopBothIndexer, indexer)
          .finish();
      }
  public void getAutoCommands(SendableChooser<Command> autoChooser) {
    autoChooser.setDefaultOption("Good Shoot", new GoodShoot(drivetrain, shooter, indexer, intake));
    autoChooser.addOption("Intake and Shoot", new IntakeAndShoot(drivetrain, shooter, indexer, intake));
    autoChooser.addOption("Normal Auton", new NormalAuto(drivetrain));
    autoChooser.addOption("Nothing Auto", new InstantCommand(() -> drivetrain.tankDriveVolts(0,0)));
    autoChooser.addOption("Straight Line Test", new StraightLineTest(drivetrain));
    autoChooser.addOption("Forward ANd Shoot Low", new ForwardAndShootLow(drivetrain, shooter, indexer, intake));
  }
}