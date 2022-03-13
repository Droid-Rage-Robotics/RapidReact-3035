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

    public ShootOneBall(Drive drive, Shooter shooter, Indexer indexer, Intake intake){
    addCommands(
        new DriveByTime(4, -0.3, drive), // moves drivtrain back for 4 seoconds
        new ShootForSeconds(shooter, 2), // shoots for 4 seconds
        new InstantCommand(indexer::outtakeBothIndexer), // starts indexer
        new WaitCommand(3),
        new InstantCommand(indexer::stopBothIndexer),
        new DriveByTime(4, -0.3, drive)  // moves back
    );
        

    }
}