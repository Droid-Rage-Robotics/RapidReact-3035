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

public class LeftSideAuto extends SequentialCommandGroup {
    public LeftSideAuto(Drive2 drive, Shooter shooter, Indexer indexer, Intake intake){
        addCommands(
            new InstantCommand(shooter::shootHighCloseAuto),
            new WaitCommand(1),
    
            new InstantCommand(indexer::intakeBothIndexer), // starts indexer
            new WaitCommand(0.85),
            new InstantCommand(indexer::stopBothIndexer),
    
            new InstantCommand(intake::lift),
            new InstantCommand(intake::intake),
    
            new EncoderDriveCommand2(40, 0.2, drive),
            new EncoderTurnCommand2(5, 0.15, drive),
            
            new InstantCommand(shooter::shootHighFarAutoHanger),
            new WaitCommand(1.5),

            new InstantCommand(indexer::intakeBothIndexer), // starts indexer
            new WaitCommand(1.5),
            new InstantCommand(indexer::stopBothIndexer),

            new EncoderTurnCommand2(-80, 0.15, drive),
            new EncoderDriveCommand2(29, 0.3, drive),

            new InstantCommand(shooter::shootLowAuto), // rev shooter
            new WaitCommand(2),

            new EncoderTurnCommand2(-20, 0.3, drive),
            
            new InstantCommand (indexer::intakeBothIndexer),

            new WaitCommand(1),

            new InstantCommand(indexer::stopBothIndexer),
            new InstantCommand(shooter::stop),
            new InstantCommand(intake::stopIntake)
        );
        addRequirements(drive, shooter, indexer, intake);
    }
}
