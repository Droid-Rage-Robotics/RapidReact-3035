package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.commands.Shooter.ShootingSequence;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Drive;


public class ForwardAndShootLow extends SequentialCommandGroup {

    public ForwardAndShootLow(Drive drivetrain, Shooter shooter, Indexer indexer){
        addCommands(
            new DriveDistance(4, 0.8, drivetrain),
            new InstantCommand(() -> drivetrain.SimpleDriveForward(10)),
            new ShootingSequence(shooter, indexer, shooter::shootLowAuto),
            new DriveDistance(4, -0.8   , drivetrain)
        );
        
    }
}