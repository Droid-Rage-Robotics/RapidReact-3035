package frc.robot.commands.Drive.GryoCommands.BadOnes;


import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drive2;

public class GyroDriveCommand extends SequentialCommandGroup {
    public GyroDriveCommand(double distance, double speed, Drive2 drive) {
        final double     COUNTS_PER_MOTOR_REV    = 2048 ;    // eg: TETRIX Motor Encoder
        final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
        final double     WHEEL_DIAMETER_INCHES   = 6.0 ;     // For figuring circumference
        final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * Math.PI);

        int moveCounts;
        // double  max,
        //         error,
        //         steer;
        
        final double     P_DRIVE_COEFF           = 0.001;     // Larger is more responsive, but also less stable
        final double     D_DRIVE_COEFF           = 0;
        double  leftSpeed;
        double  rightSpeed;

        PIDController leftDrivePID = new PIDController(P_DRIVE_COEFF, 0, D_DRIVE_COEFF);
        PIDController rightDrivePID = new PIDController(P_DRIVE_COEFF, 0, D_DRIVE_COEFF);

        drive.setMaxOutput(speed);

        moveCounts = (int)(distance * COUNTS_PER_INCH);

        drive.leftEncoder.reset();
        drive.rightEncoder.reset();

        leftDrivePID.reset();
        rightDrivePID.reset();
 
        leftDrivePID.setTolerance(5, 10);
        rightDrivePID.setTolerance(5, 10);

        while (!(drive.leftEncoder.get() > moveCounts - 500 && drive.leftEncoder.get() < moveCounts + 500)) {
            // leftDrivePID.setIntegratorRange(-0.3, 0.3);
            // rightDrivePID.setIntegratorRange(-0.3, 0.3);

            leftSpeed = leftDrivePID.calculate(drive.leftEncoder.get(), moveCounts);
            rightSpeed = rightDrivePID.calculate(drive.rightEncoder.get(), moveCounts);

            drive.tankDrive(leftSpeed, rightSpeed);
        }
        leftDrivePID.close();
        rightDrivePID.close();
    }
}