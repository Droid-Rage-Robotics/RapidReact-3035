package frc.robot.commands.Drive.GryoCommands;

import static frc.robot.DRPreferences.DoubleKey.D_TURN_COEFF;
import static frc.robot.DRPreferences.DoubleKey.P_TURN_COEFF;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.DRPreferences;
import frc.robot.subsystems.Drive2;

public class GyroTurnCommand2 extends CommandBase {
    private final Drive2 drive;
    

    private final PIDController turnPID = new PIDController(DRPreferences. get(P_TURN_COEFF), 0, DRPreferences.get(D_TURN_COEFF));
    private final double angle,
            speed;
    private double power;
    
    
    public GyroTurnCommand2(double angle, double speed, Drive2 drive) {
        this.angle = angle;
        this.speed = speed;

        this.drive = drive;
        addRequirements(drive);
    }

    @Override
    public void initialize(){
        drive.setMaxOutput(speed);

        drive.resetHeading();

        turnPID.reset();
 
        turnPID.setTolerance(5, 10);
        
        // leftDrivePID.setIntegratorRange(-0.3, 0.3);
        // rightDrivePID.setIntegratorRange(-0.3, 0.3);
    }

    @Override
    public void execute(){   
        power = turnPID.calculate(drive.getHeading(), angle);
        drive.tankDrive(-power, power);
    }

    @Override
    public boolean isFinished() {
        return (drive.getHeading() > angle - 3) && (drive.getHeading() < angle + 3);
    }

    @Override
    public void end(boolean interrupted){
        turnPID.close();
        drive.tankDrive(0, 0);
    } 
}