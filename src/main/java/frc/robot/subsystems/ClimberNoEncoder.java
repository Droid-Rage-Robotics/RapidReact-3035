package frc.robot.subsystems;


import java.util.Map;

import com.revrobotics.*;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DRPreferences;
import frc.robot.DRPreferences.DoubleKey;
import frc.robot.commands.Drive.DefaultDrive;
import frc.robot.commands.Shooter.ShootForSeconds;
import io.github.oblarg.oblog.annotations.Config;


public class ClimberNoEncoder extends SubsystemBase {
    private CANSparkMax 
        lClimberMotor = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless),
        rClimberMotor = new CANSparkMax(7, CANSparkMaxLowLevel.MotorType.kBrushless);
        
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
        lClimberMotor.set(power);
        rClimberMotor.set(power);
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
    }
}