package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Drive.GryoCommands.GyroDriveCommand2;
import frc.robot.commands.Drive.GryoCommands.GyroTurnCommand2;
import frc.robot.subsystems.Drive2;

public class GyroDrive2Test extends SequentialCommandGroup {
    public GyroDrive2Test(Drive2 drive){
        addCommands(
            new GyroDriveCommand2(35, 0.2, drive),
            new GyroTurnCommand2(180, 0.1, drive),
            new GyroDriveCommand2(35, 0.2, drive),
            new GyroTurnCommand2(180, 0.1, drive)
            // new GyroTurnCommand2(90, 0.2, drive)
        );
        addRequirements(drive);
    }
}
