package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.util.Units;
// import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Drive.DriveByTime;
import frc.robot.commands.Drive.TurnByTime;
import frc.robot.commands.Shooter.ShootingSequence;
import frc.robot.subsystems.*;
// import frc.robot.utils.*;

// import java.util.List;

public class ShootOneBall extends SequentialCommandGroup {

    public ShootOneBall(Drive drivetrain, Shooter shooter, Indexer indexer, Intake intake){
        new DriveByTime(5, -0.2, drivetrain);
        new ShootingSequence(shooter, indexer, shooter::shootHigh);
        new WaitCommand(0.5);
        new TurnByTime(3, 0.3, drivetrain);
        new DriveByTime(4, 0.4, drivetrain);
        new InstantCommand(intake::intake, intake);

    }
}