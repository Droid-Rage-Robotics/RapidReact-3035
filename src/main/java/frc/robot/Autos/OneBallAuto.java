package frc.robot.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Drive.GryoCommands.EncoderDriveCommand2;
import frc.robot.commands.Drive.GryoCommands.EncoderTurnCommand2;
import frc.robot.subsystems.Drive2;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;

public class OneBallAuto extends SequentialCommandGroup {
    public OneBallAuto(Drive2 drive, Shooter shooter, Indexer indexer, Intake intake){
        addCommands(
            new InstantCommand(shooter::shootHighCloseAuto),
            new WaitCommand(1),
    
            new InstantCommand(indexer::intakeBothIndexer), // starts indexer
            new WaitCommand(0.85),
            new InstantCommand(indexer::stopBothIndexer)
        );
        addRequirements(drive, shooter, indexer, intake);
    }
}
