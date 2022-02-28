package frc.robot.subsystems;

import com.revrobotics.CANSparkMax.ControlType;

import com.revrobotics.*;
import frc.robot.Constants.Constants.ClimberConstants;


public class ClimberNoEncoderSubsystem {
    private CANSparkMax 
        lClimberMotor,
        rClimberMotor;

    public ClimberNoEncoderSubsystem(){
        //MotorPorts
            lClimberMotor = new CANSparkMax(ClimberConstants.kLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
            rClimberMotor = new CANSparkMax(ClimberConstants.kRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        
        //Left Current Limit            //Y is their no Limit for Right Motor
            lClimberMotor.setSmartCurrentLimit(30);
        //Right Motor follows Left Motor
            rClimberMotor.follow(lClimberMotor, true);

        lClimberMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rClimberMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    //SetPower
        public void setPower(double power){
            lClimberMotor.set(power);
        }
        //RetractPower
            public void retract(){
                    setPower(-0.3);
            }
        //ExtendPower
            public void extend(){
                    setPower(0.5);
            }
        //DisableClimber
            public void disable() {
                lClimberMotor.set(0);
            }
}