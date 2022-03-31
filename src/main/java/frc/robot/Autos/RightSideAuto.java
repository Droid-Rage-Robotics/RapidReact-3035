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

public class RightSideAuto extends SequentialCommandGroup {
    public RightSideAuto(Drive2 drive, Shooter shooter, Indexer indexer, Intake intake){
        addCommands(
            new InstantCommand(intake::lift),
        
            new InstantCommand(shooter::shootHighCloseAuto),
            new WaitCommand(2),
    
            new InstantCommand(indexer::intakeBothIndexer), // starts indexer
            new WaitCommand(1),
            new InstantCommand(indexer::stopBothIndexer),
    
            new InstantCommand(intake::intake),
            new InstantCommand(indexer::intakeFrontIndexer),// starts indexer
    
            new EncoderDriveCommand2(20, 0.2, drive),
            
            // new InstantCommand(shooter::shootHighFarAuto),
            // new WaitCommand(2),
            
            // new WaitCommand(3),
            // new InstantCommand(indexer::stopBothIndexer),

            new EncoderTurnCommand2(-125, 0.2, drive),          //Turns to second ball
            new InstantCommand(indexer::stopFrontIndexer),
            new EncoderDriveCommand2(20, 0.3, drive),
            
            new InstantCommand(shooter::shootHighFarAuto),  //Starts Shooter
            new EncoderTurnCommand2(35, 0.2, drive),       //Faces Hub
            new InstantCommand(indexer::intakeBothIndexer),

            new WaitCommand(2),
            new InstantCommand(indexer::stopBothIndexer),

            new EncoderTurnCommand2(-55, 0.3, drive),
            new EncoderDriveCommand2(5, 0.5, drive),

            new EncoderTurnCommand2(180, 0.5, drive),  //Turns shooter to Hanger
            new InstantCommand(shooter::shootLowAuto),
            new InstantCommand(indexer::intakeBothIndexer),

            new EncoderTurnCommand2(-80, 0.4, drive),
            
            new InstantCommand(indexer::stopBothIndexer),
            new EncoderDriveCommand2(40, 0.4, drive)   //Goes to ball to kick to Player
        );
        addRequirements(drive, shooter, indexer, intake);
    }
}
