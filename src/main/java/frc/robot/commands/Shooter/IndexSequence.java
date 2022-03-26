package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Indexer;

public class IndexSequence extends SequentialCommandGroup {
    public IndexSequence(Indexer indexer) {
        addCommands(new SequentialCommandGroup(
            new InstantCommand(indexer::intakeBothIndexer),
            new WaitCommand(0.1),
            new InstantCommand(indexer::stopBothIndexer),
            new WaitCommand(0.5),
            new InstantCommand(indexer::intakeBothIndexer)
        ));
        addRequirements(indexer);
    }
}