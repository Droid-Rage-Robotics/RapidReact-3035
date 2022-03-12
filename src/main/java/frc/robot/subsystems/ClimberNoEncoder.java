package frc.robot.subsystems;


import com.revrobotics.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;


public class ClimberNoEncoder extends SubsystemBase {
    private CANSparkMax 
        lClimberMotor,
        rClimberMotor;

    public ClimberNoEncoder(){
        // MotorPorts
            lClimberMotor = new CANSparkMax(ClimberConstants.kLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
            rClimberMotor = new CANSparkMax(ClimberConstants.kRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        
        // Left Current Limit            // Y is their no Limit for Right Motor
            lClimberMotor.setSmartCurrentLimit(30);
        // Right Motor follows Left Motor
            
        // lClimberMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        rClimberMotor.follow(lClimberMotor, false);
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