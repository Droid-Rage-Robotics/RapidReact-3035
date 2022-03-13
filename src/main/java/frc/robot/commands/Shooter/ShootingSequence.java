package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class ShootingSequence extends SequentialCommandGroup {
    public ShootingSequence(Shooter shooter, Indexer indexer, Runnable shootCommand) {
        addCommands(new SequentialCommandGroup(
            // new InstantCommand(indexer::outtakeBothIndexer),
            // new WaitCommand(0.2),
            // new InstantCommand(indexer::stopBothIndexer),
            // new InstantCommand(shootCommand),
            // new WaitUntilCommand(shooter::isGreaterThanRPM),
            // new InstantCommand(indexer::intakeBothIndexer)
        ));
        addRequirements(shooter, indexer);
    }
}
