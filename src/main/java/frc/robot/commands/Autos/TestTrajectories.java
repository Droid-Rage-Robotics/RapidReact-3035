package frc.robot.commands.Autos;

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

    public TestTrajectories(Drive drivetrain, Shooter shooter, Indexer indexer, Intake intake){
    addCommands(
        new InstantCommand(intake::lift, intake),
        new IntakeCommand(intake, indexer),
        new DriveByTime(5, 0.3, drivetrain),
        new TurnByTime(3, 0.4, drivetrain),
        new ShootingSequence(shooter, indexer, shooter::shootHigh)        
    );
    }
}