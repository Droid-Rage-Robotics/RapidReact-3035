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

public class FRAuto extends SequentialCommandGroup {
    public FRAuto(Drive2 drive, Shooter shooter, Indexer indexer, Intake intake){
        addCommands(
            new InstantCommand(shooter::shootHighCloseAuto),
            new WaitCommand(1),
    
            new InstantCommand(indexer::intakeBothIndexer), // starts indexer for preload
            new WaitCommand(0.85),
            new InstantCommand(indexer::stopBothIndexer),
    


            new InstantCommand(intake::lift),
            new InstantCommand(intake::intake),
    
            new EncoderDriveCommand2(44, 0.2, drive),   //Drive forward for intake23
            
            new InstantCommand(shooter::shootHighFarAuto),    //Start shooter
            new WaitCommand(1.5),

            new InstantCommand(indexer::intakeBothIndexer), // starts indexer for 2nd ball
            new WaitCommand(1.5),
            new InstantCommand(indexer::stopBothIndexer),

            //new EncoderDriveCommand2(-5, 0.15, drive),
            

            new EncoderTurnCommand2(72, 0.15, drive),  //Turn to 3rd
            new EncoderDriveCommand2(45, 0.3, drive),   //drive to 3rd

            new InstantCommand(shooter::shootHighThirdAuto), // rev shooter
            new InstantCommand (indexer::intakeBothIndexer),    //Start index for 3rd
            
            new EncoderTurnCommand2(5, 0.15, drive),  


            new WaitCommand(1),
            new InstantCommand(indexer::stopBothIndexer),
            new InstantCommand(shooter::stop)
        );
        addRequirements(drive, shooter, indexer, intake);
    }
}
