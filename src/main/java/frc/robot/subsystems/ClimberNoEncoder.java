package frc.robot.subsystems;


import com.revrobotics.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DRPreferences;
import frc.robot.DRPreferences.DoubleKey;


public class ClimberNoEncoder extends SubsystemBase {
    private CANSparkMax 
        lClimberMotor = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless),
        rClimberMotor = new CANSparkMax(7, CANSparkMaxLowLevel.MotorType.kBrushless);

    public ClimberNoEncoder(){
        lClimberMotor.setSmartCurrentLimit(30);
        rClimberMotor.setSmartCurrentLimit(30);

        rClimberMotor.follow(lClimberMotor, false);
    }

    // we have this so that we can take motors out of follow mode and only have to add one more line
    public void setPower(double power){
        lClimberMotor.set(power);
    }

    public void retract(){
            setPower(DRPreferences.get(DoubleKey.CLIMBER_RETRACT_POWER));
    }
    
    public void extend(){
            setPower(DRPreferences.get(DoubleKey.CLIMBER_EXTEND_POWER));
    }

    public void stop() {
        lClimberMotor.set(0);
    }
}