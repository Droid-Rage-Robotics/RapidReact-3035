package frc.robot.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Drive.DriveByTime;
import frc.robot.subsystems.Drive2;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class HighShots2Hanger extends SequentialCommandGroup {

    public HighShots2Hanger(Drive2 drive, Shooter shooter, Indexer indexer, Intake intake){
    addCommands(
        new InstantCommand(shooter::shootHighCloseAuto),
        new WaitCommand(2),

        new InstantCommand(indexer::intakeBothIndexer), // starts indexer
        new WaitCommand(1),
        new InstantCommand(indexer::stopBothIndexer),

        new InstantCommand(intake::lift),
        new InstantCommand(intake::intake),

        new DriveByTime(
            0.4, // left power
            0.4, // right power
            2,   // seconds
            drive), // moves drivtrain back for 4 seoconds
        
        new InstantCommand(shooter::shootHighFarAutoHanger),
        new WaitCommand(2),
        
        
        new InstantCommand(indexer::intakeBothIndexer), // starts indexer
        new WaitCommand(3),
        new InstantCommand(intake::stopIntake),
        new InstantCommand(indexer::stopBothIndexer)
    );
}
}