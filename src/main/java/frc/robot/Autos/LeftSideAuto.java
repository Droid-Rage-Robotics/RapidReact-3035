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
            new WaitCommand(2),
    
            new InstantCommand(indexer::intakeBothIndexer), // starts indexer
            new WaitCommand(1),
            new InstantCommand(indexer::stopBothIndexer),
    
            new InstantCommand(intake::lift),
            new InstantCommand(intake::intake),
    
            new EncoderDriveCommand2(20, 0.2, drive),
            
            new InstantCommand(shooter::shootHighFarAuto),
            new WaitCommand(2),

            new InstantCommand(indexer::intakeBothIndexer), // starts indexer
            new WaitCommand(3),
            new InstantCommand(indexer::stopBothIndexer),

            new EncoderTurnCommand2(-50, 0.3, drive),
            new EncoderDriveCommand2(15, 0.3, drive),

            new WaitCommand(1),

            new EncoderDriveCommand2(-10, 0.3, drive),
            new InstantCommand(shooter::shootLowAuto),
            new InstantCommand (indexer::intakeBothIndexer)
        );
        addRequirements(drive, shooter, indexer, intake);
    }
}
