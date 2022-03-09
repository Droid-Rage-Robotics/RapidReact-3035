package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
    //Intake Motor
        private CANSparkMax intakeMotor;
        private DoubleSolenoid liftDoubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 2);

    public Intake() {
        intakeMotor = new CANSparkMax(IntakeConstants.IntakeMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        //Intake Current Limit
        intakeMotor.setSmartCurrentLimit(40);
        liftDoubleSolenoid.get();

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

        public void lift() {

        }

        public void drop() {

        }
}