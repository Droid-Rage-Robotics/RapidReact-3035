package frc.robot.commands.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drive2;

public class GyroDrive2Test extends SequentialCommandGroup {
    public GyroDrive2Test(Drive2 drive){
        addCommands(
            // new InstantCommand(() -> drive.gyroTurn(90, 0.2)),
            // new InstantCommand(() -> drive.gyroDrive(20, 0.2)),
            // // new WaitCommand(1),
            // new GyroDriveCommand(20, 0.2, drive),
            // new GyroTurnCommand(90, 0.3, drive)
        );
        addRequirements(drive);
    }
}
