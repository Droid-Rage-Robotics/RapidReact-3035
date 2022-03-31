package frc.robot.commands.Drive.GryoCommands;

import static frc.robot.Constants.DriveConstants.COUNTS_PER_INCH;
import static frc.robot.DRPreferences.DoubleKey.D_DRIVE_COEFF;
import static frc.robot.DRPreferences.DoubleKey.P_DRIVE_COEFF;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.DRPreferences;
import frc.robot.subsystems.Drive2;

public class GyroDriveCommand2 extends CommandBase {
    private final Drive2 drive;

    // final double     COUNTS_PER_MOTOR_REV    = 2048 ;    // eg: TETRIX Motor Encoder
    // final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    // final double     WHEEL_DIAMETER_INCHES   = 6.0 ;     // For figuring circumference
    // final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
    //                                                   (WHEEL_DIAMETER_INCHES * Math.PI);

    final int moveCounts;

    final PIDController leftDrivePID  = new PIDController(DRPreferences.get(P_DRIVE_COEFF), 0, DRPreferences.get(D_DRIVE_COEFF));
    final PIDController rightDrivePID = new PIDController(DRPreferences.get(P_DRIVE_COEFF), 0, DRPreferences.get(D_DRIVE_COEFF));

    final double distance, speed;
    
    
    public GyroDriveCommand2(double distance, double speed, Drive2 drive) {
        this.distance = distance;
        this.speed = speed;

        moveCounts = (int) (distance * COUNTS_PER_INCH);

        this.drive = drive;
        addRequirements(drive);
    }

    @Override
    public void initialize(){
        drive.setMaxOutput(speed);

        drive.leftEncoder.reset();
        drive.rightEncoder.reset();

        leftDrivePID.reset();
        rightDrivePID.reset();
 
        leftDrivePID.setTolerance(5, 10);
        rightDrivePID.setTolerance(5, 10);
        
        // leftDrivePID.setIntegratorRange(-0.3, 0.3);
        // rightDrivePID.setIntegratorRange(-0.3, 0.3);
    }

    @Override
    public void execute(){   
           drive.tankDrive(leftDrivePID.calculate(drive.leftEncoder.get(), moveCounts), rightDrivePID.calculate(drive.rightEncoder.get(), -moveCounts));
    }

    @Override
    public boolean isFinished() {
        return drive.leftEncoder.get() > moveCounts - 200 && drive.leftEncoder.get() < moveCounts + 200;
    }

    @Override
    public void end(boolean interrupted){
        leftDrivePID.close();
        rightDrivePID.close();
    } 
}