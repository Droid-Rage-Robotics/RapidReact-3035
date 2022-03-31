package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive2;
// import frc.robot.subsystems.Drive2;

import java.util.function.DoubleSupplier;

public class DriverControl extends CommandBase {
    private final Drive2 drive;
    private final DoubleSupplier forward;
    private final DoubleSupplier rotate;

    public DriverControl(Drive2 drive, DoubleSupplier forward, DoubleSupplier rotate) {
        this.drive = drive;
        
        this.forward = forward;
        this.rotate = rotate;

        addRequirements(drive);
    }

    @Override
    public void execute() {

        double rawX = forward.getAsDouble();
        double rawPivot = rotate.getAsDouble();

        if (Math.abs(forward.getAsDouble()) < 0.07)
            rawX = 0;

        if (Math.abs(rotate.getAsDouble()) < 0.1)
            rawPivot = 0;

        drive.arcadeDrive(-rawX, rawPivot);

    }
}