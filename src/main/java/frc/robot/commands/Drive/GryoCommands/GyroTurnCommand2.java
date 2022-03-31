package frc.robot.commands.Drive.GryoCommands;

import static frc.robot.Constants.DriveConstants.COUNTS_PER_MOTOR_REV;
import static frc.robot.Constants.DriveConstants.DRIVE_GEAR_REDUCTION;
import static frc.robot.DRPreferences.DoubleKey.D_TURN_COEFF;
import static frc.robot.DRPreferences.DoubleKey.P_TURN_COEFF;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.DRPreferences;
import frc.robot.subsystems.Drive2;

public class GyroTurnCommand2 extends CommandBase {
    private final Drive2 drive;

    // final double     COUNTS_PER_MOTOR_REV    = 2048 ;    // eg: TETRIX Motor Encoder
    // final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP\

    final int moveCounts;
    

    final PIDController leftDrivePID = new PIDController(DRPreferences. get(P_TURN_COEFF), 0, DRPreferences.get(D_TURN_COEFF));
    final PIDController rightDrivePID = new PIDController(DRPreferences.get(P_TURN_COEFF), 0, DRPreferences.get(D_TURN_COEFF));

    final double angle,
            speed;
    
    
    public GyroTurnCommand2(double angle, double speed, Drive2 drive) {
        this.angle = angle;
        this.speed = speed;
        
        moveCounts = (int)((angle / 90) * (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION));

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