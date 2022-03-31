package frc.robot.Autos.UnusedAutos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Drive.DriveByEncoders;
import frc.robot.subsystems.Drive2;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class HighShots2WithEncoders extends SequentialCommandGroup {

    public HighShots2WithEncoders(Drive2 drive, Shooter shooter, Indexer indexer, Intake intake){
    addCommands(
        // new DriveByTime(4, -0.3, drive), // moves drivtrain back for 4 seoconds
        // new ShootForSeconds(shooter, 2), // shoots for 4 seconds
        new InstantCommand(shooter::shootCloseHigh),
        new WaitCommand(1),

        new InstantCommand(indexer::intakeBothIndexer), // starts indexer
        new WaitCommand(1),
        new InstantCommand(indexer::stopBothIndexer),

        new InstantCommand(intake::lift),
        new InstantCommand(intake::intake),

        // new DriveByTime(3, 0.4, drive), // moves drivtrain back for 4 seoconds
        new DriveByEncoders(1, 1, drive), 
        
        new InstantCommand(shooter::shootFarHigh),
        new WaitCommand(2),
        new InstantCommand(intake::stopIntake),
        
        new InstantCommand(indexer::intakeBothIndexer), // starts indexer
        new WaitCommand(3),
        new InstantCommand(indexer::stopBothIndexer)


        // new DriveByTime(4, -0.3, drive)  // moves back

    );
}
}