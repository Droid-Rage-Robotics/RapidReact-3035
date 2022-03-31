package frc.robot.Autos.UnusedAutos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.Drive.DriveByTime;
import frc.robot.commands.Drive.TurnByTime;
import frc.robot.commands.Shooter.ShootingSequence;
import frc.robot.subsystems.*;
// import frc.robot.utils.*;

// import java.util.List;

public class TestTrajectories extends SequentialCommandGroup {

    public TestTrajectories(Drive2 drive, Shooter shooter, Indexer indexer, Intake intake){
    addCommands(
        new InstantCommand(intake::lift, intake),
        new IntakeCommand(intake, indexer),
        new DriveByTime(5, 0.3, drive),
        new TurnByTime(3, 0.4, drive),
        new ShootingSequence(shooter, indexer, shooter::shootFarHigh)        
    );
    }
}