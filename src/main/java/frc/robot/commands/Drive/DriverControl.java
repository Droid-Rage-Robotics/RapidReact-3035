package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

import java.util.function.DoubleSupplier;

public class DriverControl extends CommandBase {
    private final Drive drivetrain;
    private final DoubleSupplier forward;
    private final DoubleSupplier rotate;

    public DriverControl(Drive subsystem, DoubleSupplier forward, DoubleSupplier rotate) {
        drivetrain = subsystem;
        addRequirements(subsystem);
        this.forward = forward;
        this.rotate = rotate;
    }

    @Override
    public void execute() {

        double rawX = forward.getAsDouble();
        double rawPivot = rotate.getAsDouble();

        if (Math.abs(forward.getAsDouble()) < 0.07)
            rawX = 0;

        if (Math.abs(rotate.getAsDouble()) < 0.1)
            rawPivot = 0;

        if(!drivetrain.isControlsFlipped())
            drivetrain.arcadeDrive(-rawX, rawPivot);
        else
            drivetrain.arcadeDrive(rawX, rawPivot);

    }
}