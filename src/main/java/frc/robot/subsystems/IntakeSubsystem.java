package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
    //Intake Motor
        private CANSparkMax intakeMotor;

    public IntakeSubsystem() {
        intakeMotor = new CANSparkMax(IntakeConstants.IntakeMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        //Intake Current Limit
            intakeMotor.setSmartCurrentLimit(40);

    }
    //Intake
        public void intakeBalls() {
            setIntakePower(IntakeConstants.IntakePower);
        }
    //Outtake
        public void outtakeBalls() {
            setIntakePower(-IntakeConstants.IntakePower);
        }
    //Disable
        public void disable() {
            setIntakePower(0);
        }
    //Set Intake Power
        public void setIntakePower(double power){
            intakeMotor.set(power);
        }
}