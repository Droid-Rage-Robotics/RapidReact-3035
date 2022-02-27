package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {

    private CANSparkMax intakeMotor;

    public IntakeSubsystem() {
        intakeMotor = new CANSparkMax(IntakeConstants.kIntakeMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);

        intakeMotor.setSmartCurrentLimit(40);

    }

    public void intakeBalls() {
        setIntakePower(IntakeConstants.kIntakePower);
    }
    
    public void outtakeBalls() {
        setIntakePower(-IntakeConstants.kIntakePower);
    }

    public void disable() {
        setIntakePower(0);
    }

    public void setIntakePower(double power){
        intakeMotor.set(power);

    }
}