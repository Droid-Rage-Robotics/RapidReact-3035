package frc.robot.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Drive.DriveByTime;
import frc.robot.commands.Shooter.ShootForSeconds;
import frc.robot.subsystems.Drive2;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class GoodShoot extends SequentialCommandGroup {

    public GoodShoot(Drive2 drive, Shooter shooter, Indexer indexer, Intake intake){
    addCommands(
        new DriveByTime(4, -0.3, drive), // moves drivtrain back for 4 seoconds
        new ShootForSeconds(shooter, 2), // shoots for 4 seconds
        new InstantCommand(indexer::outtakeBothIndexer), // starts indexer
        new WaitCommand(3),
        new InstantCommand(indexer::stopBothIndexer),
        new DriveByTime(4, -0.3, drive)  // moves bac
    );
}
}