package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;


public class IntakeCommand extends SequentialCommandGroup {
    public IntakeCommand(Intake intake, Indexer indexer) {
        addCommands(
            new InstantCommand(intake::lift, intake),
            new InstantCommand(intake::intake, intake)
        );
    }
}