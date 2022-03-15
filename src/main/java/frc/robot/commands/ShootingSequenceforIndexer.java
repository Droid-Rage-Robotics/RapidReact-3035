package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Indexer;

public class ShootingSequenceforIndexer extends SequentialCommandGroup{
    public ShootingSequenceforIndexer(Indexer indexer) {
        
        addCommands(new SequentialCommandGroup(
            new InstantCommand(indexer::outtakeBothIndexer),
            new WaitCommand(0.03),
            new InstantCommand(indexer::intakeBothIndexer),
            new WaitCommand(4),
            new InstantCommand(indexer::stopBothIndexer)
        ));
        addRequirements(indexer);
    }
}
