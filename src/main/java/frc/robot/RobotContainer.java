// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Controllers.Controls.XboxButton.*;
import static frc.robot.Controllers.Controls.XboxDPAD.*;
import static frc.robot.Controllers.Controls.XboxTrigger.*;

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
import frc.robot.commands.Autos.HighShots2;
import frc.robot.commands.Autos.HighShots2WithEncoders;
import frc.robot.commands.Autos.StraightLineTest;
import frc.robot.commands.Shooter.IndexSequence;
import frc.robot.commands.Shooter.ShootingSequence;

import frc.robot.subsystems.ClimberNoEncoder;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import io.github.oblarg.oblog.Logger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // Defined Robot Subsystems
    private final Drive drive = new Drive();
    private final Shooter shooter = new Shooter();
    private final Indexer indexer = new Indexer();
    private final Intake intake = new Intake();
    private final ClimberNoEncoder climber = new ClimberNoEncoder();

    private XboxController driverController = new XboxController(0);
    private XboxController operatorController = new XboxController(1);

    private Controllers controllers = new Controllers(
            driverController,
            operatorController);

    

    /*
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        // configureButtonBindings();
        // initTeleopCommands();
        
    }

    public void initTeleopCommands() {
        drive.initDefaultCommands(
                () -> driverController.getLeftY(),
                () -> driverController.getRightX());
        CameraServer.startAutomaticCapture();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
     * it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */

    // Add Commands to Buttons
    public void configureButtonBindings() {
        controllers

                /**
                 * CONTROLLER 0
                 */

            .addCommandsToControllerPort(0)
                .add("intake", RT) // Intake and Indexer
                    .whenActive(intake::lift, intake)
                    .whenActive(intake::intake, intake)
                // .whenActive(indexer::intakeFrontIndexer, indexer)

                .whenInactive(intake::stopIntake)
                // .whenInactive(indexer::disableFrontIndexer, indexer)

                .add("outtake", LT) // Outtake
                    .whenActive(intake::lift, intake)
                    .whenActive(indexer::outtakeBothIndexer, indexer)
                    .whenActive(intake::outtake, intake)

                    .whenInactive(indexer::stopBothIndexer, indexer)
                    .whenInactive(intake::stopIntake, intake)

                .add("intakeDrop", DPAD_DOWN) // Intake Down
                    .whenActive(intake::drop, intake)

                .add("intakeLift", DPAD_UP) // Intake Up
                    .whenActive(intake::lift, intake)

                .add("slowMode", RB) // Slow Mode
                    .whenActive(drive::slowDrive, drive)
                    .whenInactive(drive::normalDrive, drive)

                .add("turboMode", LB) // Turbo Mode
                    .whenActive(drive::turboDrive, drive)
                    .whenInactive(drive::normalDrive, drive)

                //     .add("shootLow", A) // Low
                //     .whenActive(shooter::shootLow, shooter)
                //     // .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::shootLow), true)
                    

                // .add("CLOSE HIGH", B) // High
                //     .whenActive(shooter::shootCloseHigh, shooter)
                //     // .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::shootFarHigh), true)
                    

                // .add("FAR HIGH", Y) // (No PID COntrol, Percent Output instead)
                //     .whenActive(shooter::shootFarHigh, shooter)
                //     // .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::shootCloseHigh), true)
                .add("invert", A)
                    .whenActive(drive::setRightSideInverted)
                    // .whenActive(drive::setRightSideInverted, drive)

                .add("forward", B)
                    .whenActive(drive::setRightSideForward)
                    // .whenActive(drive::setRightSideForward, drive)

                .add("addRPM", DPAD_RIGHT)
                    .whenActive(shooter::addRPM, shooter)
                .add("subtractRPM", DPAD_LEFT)
                    .whenInactive(shooter::subtractRPM, shooter)
                .finish()






            /**
              * CONTROLLER 1
            */

            .addCommandsToControllerPort(1)
                .add("addRPM", DPAD_RIGHT)
                    .whenActive(shooter::addRPM, shooter)

                .add("subtractRPM", DPAD_LEFT)
                    .whenInactive(shooter::subtractRPM, shooter)

                .add("shootLow", A) // Low
                    .whenActive(shooter::shootLow, shooter)
                    // .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::shootLow), true)
                    

                .add("CLOSE HIGH", B) // High
                    .whenActive(shooter::shootCloseHigh, shooter)
                    // .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::shootFarHigh), true)
                    

                .add("FAR HIGH", Y) // (No PID COntrol, Percent Output instead)
                    .whenActive(shooter::shootFarHigh, shooter)
                    // .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::shootCloseHigh), true)
                .add("Index sequence", LB)
                    .whenActive(new IndexSequence(indexer), true)
                    .whenInactive(indexer::stopBothIndexer, indexer)
                    
                .add("stop", LT)
                    .whenActive(shooter::stop, shooter)
                    .whenActive(indexer::stopBothIndexer, indexer)

                .add("climberExtend", DPAD_UP) // Climber Up
                    .whenActive(climber::extend)

                    .whenInactive(climber::stop)

                .add("climberRetract", DPAD_DOWN) // Climber Down
                    .whenActive(climber::retract)

                    .whenInactive(climber::stop)

                .add("indexerUp", RB) // Indexer Up
                    .whenActive(indexer::intakeFrontIndexer, indexer)

                    .whenInactive(indexer::stopBothIndexer, indexer)

                .add("indexerUpBoth", X) // Indexer Up Both
                    .whenActive(indexer::intakeBothIndexer, indexer)

                    .whenInactive(indexer::stopBothIndexer, indexer)

                .add("indexerDown", RT) // Indexer Down
                    .whenActive(indexer::outtakeBothIndexer, indexer)

                    .whenInactive(indexer::stopBothIndexer, indexer)

                // .add("shootIndexer", RT)
                //     .whenActive(new ShootingSequenceforIndexer(indexer))

                    // .whenInactive(indexer::stopBothIndexer, indexer)

                    // .add("shootIn", LB) //Indexer Down
                    // .whenActive(indexer::outtakeBothIndexer, indexer)

                    // .whenInactive(indexer::stopBothIndexer, indexer)
                .finish();
    }

    public void getAutoCommands(SendableChooser<Command> autoChooser) {
        autoChooser.setDefaultOption("Good Shoot", new GoodShoot(drive, shooter, indexer, intake));
        autoChooser.addOption("Intake and Shoot", new IntakeAndShoot(drive, shooter, indexer, intake));
        autoChooser.addOption("Normal Auton", new NormalAuto(drive));
        autoChooser.addOption("Nothing Auto", new InstantCommand(() -> drive.tankDriveVolts(0, 0)));
        autoChooser.addOption("Straight Line Test", new StraightLineTest(drive));
        autoChooser.addOption("Forward ANd Shoot Low", new ForwardAndShootLow(drive, shooter, indexer, intake));
        autoChooser.addOption("2 shots", new HighShots2(drive, shooter, indexer, intake));
        autoChooser.addOption("2 shots with encoder", new HighShots2WithEncoders(drive, shooter, indexer, intake));
    }
}