package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Drive.DriveByTime;
import frc.robot.commands.Shooter.ShootingSequence;
import frc.robot.subsystems.*;


public class ForwardAndShootLow extends SequentialCommandGroup {

    public ForwardAndShootLow(Drive2 drive, Shooter shooter, Indexer indexer, Intake intake){
        addCommands(
            new InstantCommand(intake::lift, intake),
            new DriveByTime(5, 0.2, drive),
            new ShootingSequence(shooter, indexer, shooter::shootFarHigh),
            new DriveByTime(5, -0.2, drive)
        );
        
    }
}