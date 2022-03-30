package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drive2;

public class GyroDrive2Test extends SequentialCommandGroup {
    public GyroDrive2Test(Drive2 drive){
        addCommands(
            new InstantCommand(() -> drive.gyroDrive(5))
        );
    }
}
