package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.util.Units;
// import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Drive.DriveByTime;
import frc.robot.commands.Shooter.ShootingSequence;
import frc.robot.subsystems.*;
// import frc.robot.utils.*;

// import java.util.List;

public class BackwardAndShootHigh extends SequentialCommandGroup {

    public BackwardAndShootHigh(Drive drivetrain, Shooter shooter, Indexer indexer, Intake intake){
    addCommands(
        new InstantCommand(intake::lift, intake),
        new DriveByTime(5, -0.2, drivetrain),
        new ShootingSequence(shooter, indexer, shooter::shootHigh),
        new DriveByTime(5, 0.2, drivetrain)
    );
    }
}