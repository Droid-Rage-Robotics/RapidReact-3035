// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Controllers.Controls.XboxButton.A;
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

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Autos.FRAuto;
import frc.robot.Autos.ForwardAndShootLow;
import frc.robot.Autos.GoodShoot;
import frc.robot.Autos.GyroDrive2Test;
import frc.robot.Autos.HighShots2;
import frc.robot.Autos.HighShots2Hanger;
import frc.robot.Autos.IntakeAndShoot;
import frc.robot.Autos.LeftSideAuto;
import frc.robot.Autos.OneBallAuto;
import frc.robot.Autos.RightSideAuto;
import frc.robot.Controllers.Controllers;
import frc.robot.commands.Drive.DriverControl;
import frc.robot.commands.Shooter.IndexSequence;
import frc.robot.subsystems.ClimberNoEncoder;
import frc.robot.subsystems.Drive2;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

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
    // private final Drive drive = new Drive();
    private final Drive2 drive = new Drive2();
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
        drive.setDefaultCommand(new DriverControl(
            drive,
            () -> driverController.getLeftY(),
            () -> driverController.getRightX()
        ));
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

                .add("armExtend", DPAD_RIGHT) //
                    .whenActive(climber::extendTraverse)

                    .whenInactive(climber::stopTraverse)

                .add("armRetract", DPAD_LEFT) //
                    .whenActive(climber::retractTraverse)

                    .whenInactive(climber::stopTraverse)

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
                    .whenActive(drive::setRightSideInverted, drive)

                .add("forward", B)
                    .whenActive(drive::setRightSideForward)
                    .whenActive(drive::setRightSideForward, drive)

                .add("addRPM", DPAD_RIGHT)
                    .whenActive(shooter::addRPM, shooter)
                .add("subtractRPM", DPAD_LEFT)
                    .whenInactive(shooter::subtractRPM, shooter)

                .add("shootingSequence", X)
                    .whenActive(new IndexSequence(indexer), true)
                    
                    .whenInactive(indexer::stopBothIndexer, indexer)
                .finish()






            /**
              * CONTROLLER 1
            ***/

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
                    

                .add("FAR HIGH", Y) // (No PID Control, Percent Output instead)
                    .whenActive(shooter::shootFarHigh, shooter)
                    // .whileActiveContinuous(new ShootingSequence(shooter, indexer, shooter::shootCloseHigh), true)

                .add("indexerUpBoth", X) // Indexer Up Both
                    .whenActive(indexer::intakeBothIndexer, indexer)

                    .whenInactive(indexer::stopBothIndexer, indexer)


                .add("Index sequence", LB)
                    .whenActive(new IndexSequence(indexer), true)
                    .whenInactive(indexer::stopBothIndexer, indexer)
                    
                .add("indexerUp", RB) // Indexer Up
                    .whenActive(indexer::intakeFrontIndexer, indexer)

                    .whenInactive(indexer::stopBothIndexer, indexer)


                .add("climberExtend", DPAD_UP) // Climber Up
                    .whenActive(climber::extendClimber)

                    .whenInactive(climber::stopArm)

                .add("climberRetract", DPAD_DOWN) // Climber Down
                    .whenActive(climber::retractClimber)

                    .whenInactive(climber::stopArm)


                

                .add("stop", LT)
                    .whenActive(shooter::stop, shooter)
                    .whenActive(indexer::stopBothIndexer, indexer)

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
        autoChooser.setDefaultOption("3310 Right Side FR", new FRAuto(drive, shooter, indexer, intake));
        autoChooser.addOption("One Ball Auto", new OneBallAuto(drive, shooter, indexer, intake));

        autoChooser.addOption("Left Side (Hanger)", new LeftSideAuto(drive, shooter, indexer, intake));
        autoChooser.addOption("Right Side (Human Player)", new RightSideAuto(drive, shooter, indexer, intake));


        autoChooser.addOption("Good Shoot", new GoodShoot(drive, shooter, indexer, intake));
        autoChooser.addOption("Gyrodrive", new GyroDrive2Test(drive));
        
        autoChooser.addOption("Intake and Shoot", new IntakeAndShoot(drive, shooter, indexer, intake));
        autoChooser.addOption("Forward And Shoot Low", new ForwardAndShootLow(drive, shooter, indexer, intake));

        autoChooser.addOption("2 Good High Shots", new HighShots2(drive, shooter, indexer, intake));
        autoChooser.addOption("2 Good High Shots Hanger", new HighShots2Hanger(drive, shooter, indexer, intake));


        
    }
}