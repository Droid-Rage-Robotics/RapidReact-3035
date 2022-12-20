package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DRPreferences;
import frc.robot.DRPreferences.DoubleKey;


public class ClimberNoEncoder extends SubsystemBase {
    private CANSparkMax 
        armTraverse = new CANSparkMax(16, CANSparkMaxLowLevel.MotorType.kBrushless),
        lClimberMotor = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless),
        rClimberMotor = new CANSparkMax(7, CANSparkMaxLowLevel.MotorType.kBrushless);
// private VictorSPX
//         armClimb = new VictorSPX(15);
        
    public NetworkTableEntry leftClimberSliderStatus;
    public NetworkTableEntry rightClimberSliderStatus;
    public NetworkTableEntry leftClimberSlider;
    public NetworkTableEntry rightClimberSlider;
    
    public ClimberNoEncoder() {
        lClimberMotor.setSmartCurrentLimit(40);
        rClimberMotor.setSmartCurrentLimit(40);
        armTraverse.setSmartCurrentLimit(40);

         rClimberMotor.setInverted(true);armTraverse.setIdleMode(IdleMode.kBrake);

        // leftClimberSliderStatus = 
        //     Shuffleboard.getTab("Robot")
        //         .add("Left Climber Slider Status", 0)
        //         .withWidget(BuiltInWidgets.kToggleButton) 
        //         .getEntry();

        // rightClimberSliderStatus = 
        //     Shuffleboard.getTab("Robot")
        //         .add("Right Climber Slider Status", 0)
        //         .withWidget(BuiltInWidgets.kToggleButton) 
        //         .getEntry();

        // leftClimberSlider = 
        //     Shuffleboard.getTab("Robot")
        //         .add("Left Climber Slider", 0)
        //         .withWidget(BuiltInWidgets.kNumberSlider) // specify the widget here
        //         .withProperties(Map.of("min", -1, "max", 1)) // specify widget properties here
        //         .getEntry();

        // rightClimberSlider = 
        //     Shuffleboard.getTab("Robot")
        //         .add("Right Climber Slider", 0)
        //         .withWidget(BuiltInWidgets.kNumberSlider) // specify the widget here
        //         .withProperties(Map.of("min", -1, "max", 1)) // specify widget properties here 
        //         .getEntry();
    }

    // we have this so that we can take motors out of follow mode and only have to add one more line
    public void setClimberPower(double power){
        lClimberMotor.set(power);
        rClimberMotor.set(power);
    }

    public void retractClimber(){
        setClimberPower(DRPreferences.get(DoubleKey.CLIMBER_RETRACT_POWER));
    }
    
    public void extendClimber(){
        setClimberPower(DRPreferences.get(DoubleKey.CLIMBER_EXTEND_POWER));
    }

    public void setTraversePower(double power){
        armTraverse.set(power);
    }

    public void retractTraverse(){
        setTraversePower(-0.5);
    }
    
    public void extendTraverse(){
        setTraversePower((0.5));
    }

    


    public void stopArm() {
        lClimberMotor.set(0);
        rClimberMotor.set(0);
    }

    public void stopTraverse() {
        armTraverse.set(0);
    }
}