package frc.robot.subsystems;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.ControlType;

import frc.robot.Constants.Constants.ClimberConstants;


public class ClimberNoEncoderSubsystem {
    private CANSparkMax lClimberMotor;
    private CANSparkMax rClimberMotor;

    public ClimberNoEncoderSubsystem(){
        lClimberMotor = new CANSparkMax(ClimberConstants.kLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        rClimberMotor = new CANSparkMax(ClimberConstants.kRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);

        lClimberMotor.setSmartCurrentLimit(30);

        rClimberMotor.follow(lClimberMotor, true);

        lClimberMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rClimberMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void setPower(double power){
        lClimberMotor.set(power);
    }

    public void retract(){
            setPower(-0.3);
    }

    public void extend(){
            setPower(0.5);
    }

    public void disable() {
        lClimberMotor.set(0);
    }

}