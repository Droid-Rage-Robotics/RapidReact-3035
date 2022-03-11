package frc.robot.commands.Autos;

// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.util.Units;
// import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.commands.Shooter.ShootingSequence;
import frc.robot.subsystems.*;
// import frc.robot.utils.*;

// import java.util.List;

public class BackwardAndShootHigh extends SequentialCommandGroup {

    public BackwardAndShootHigh(Drive drivetrain, Shooter shooter, Indexer indexer){
        new DriveDistance(4, -7, drivetrain);
        new ShootingSequence(shooter, indexer, shooter::shootHighAuto);
        new DriveDistance(4, 7, drivetrain);
    }
}