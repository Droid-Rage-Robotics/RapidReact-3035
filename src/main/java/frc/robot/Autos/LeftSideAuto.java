package frc.robot.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Drive.GryoCommands.GyroDriveCommand2;
import frc.robot.commands.Drive.GryoCommands.GyroTurnCommand2;
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
    
            new GyroDriveCommand2(20, 0.2, drive),
            
            new InstantCommand(shooter::shootHighFarAuto),
            new WaitCommand(2),

            new InstantCommand(indexer::intakeBothIndexer), // starts indexer
            new WaitCommand(3),
            new InstantCommand(indexer::stopBothIndexer)

            

            // new GyroTurnCommand2(-125, 0.2, drive),          //Turns to secodn ball
            // new GyroDriveCommand2( 20, 0.3, drive),
            
            // new InstantCommand(shooter::shootHighFarAuto),  //Starts Shooter
            // new  GyroTurnCommand2(35, 0.2, drive),       //Faces HUb

            // new InstantCommand(indexer::intakeBothIndexer),
            // new InstantCommand(indexer::stopBothIndexer),

            // new GyroDriveCommand2(40, 0.4, drive)   //Goes to ball to kick to Player
            

        );
        addRequirements(drive);
    }
}
