package frc.robot.subsystems;


import java.util.Map;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DRPreferences;
import frc.robot.DRPreferences.DoubleKey;


public class ClimberNoEncoder extends SubsystemBase {
    private CANSparkMax 
        lClimberMotor = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless),
        rClimberMotor = new CANSparkMax(7, CANSparkMaxLowLevel.MotorType.kBrushless);
private VictorSPX
        armClimb = new VictorSPX(15);
        
    public NetworkTableEntry leftClimberSliderStatus;
    public NetworkTableEntry rightClimberSliderStatus;
    public NetworkTableEntry leftClimberSlider;
    public NetworkTableEntry rightClimberSlider;
    
    public ClimberNoEncoder() {
        lClimberMotor.setSmartCurrentLimit(30);
        rClimberMotor.setSmartCurrentLimit(30);
        
        rClimberMotor.setInverted(true);

        leftClimberSliderStatus = 
            Shuffleboard.getTab("Robot")
                .add("Left Climber Slider Status", 0)
                .withWidget(BuiltInWidgets.kToggleButton) 
                .getEntry();

        rightClimberSliderStatus = 
            Shuffleboard.getTab("Robot")
                .add("Right Climber Slider Status", 0)
                .withWidget(BuiltInWidgets.kToggleButton) 
                .getEntry();

        leftClimberSlider = 
            Shuffleboard.getTab("Robot")
                .add("Left Climber Slider", 0)
                .withWidget(BuiltInWidgets.kNumberSlider) // specify the widget here
                .withProperties(Map.of("min", -1, "max", 1)) // specify widget properties here
                .getEntry();

        rightClimberSlider = 
            Shuffleboard.getTab("Robot")
                .add("Right Climber Slider", 0)
                .withWidget(BuiltInWidgets.kNumberSlider) // specify the widget here
                .withProperties(Map.of("min", -1, "max", 1)) // specify widget properties here 
                .getEntry();
    }

    // we have this so that we can take motors out of follow mode and only have to add one more line
    public void setPower(double power){
        // lClimberMotor.set(power);
        // rClimberMotor.set(power);
        armClimb.set(ControlMode.PercentOutput, power);
    }

    public void retract(){
        setPower(DRPreferences.get(DoubleKey.CLIMBER_RETRACT_POWER));
    }
    
    public void extend(){
        setPower(DRPreferences.get(DoubleKey.CLIMBER_EXTEND_POWER));
        // return;
        // setPower(6);
    }

    


    public void stop() {
        lClimberMotor.set(0);
        rClimberMotor.set(0);
        armClimb.set(ControlMode.PercentOutput, 0);
    }
}