package frc.robot.Autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Drive.GryoCommands.EncoderDriveCommand2;
import frc.robot.commands.Drive.GryoCommands.EncoderTurnCommand2;
import frc.robot.subsystems.Drive2;

public class GyroDrive2Test extends SequentialCommandGroup {
    public GyroDrive2Test(Drive2 drive){
        addCommands(
            new EncoderDriveCommand2(35, 0.2, drive),
            new EncoderTurnCommand2(180, 0.1, drive),
            new EncoderDriveCommand2(35, 0.2, drive),
            new EncoderTurnCommand2(180, 0.1, drive)
            // new GyroTurnCommand2(90, 0.2, drive)
        );
        addRequirements(drive);
    }
}
