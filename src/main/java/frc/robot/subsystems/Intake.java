package frc.robot.subsystems;

import static frc.robot.DRPreferences.DoubleKey.INTAKE_INTAKE_SPEED;
import static frc.robot.DRPreferences.DoubleKey.INTAKE_OUTTAKE_SPEED;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DRPreferences;

public class Intake extends SubsystemBase {

        //Intake Motor
        private CANSparkMax intakeMotor = new CANSparkMax(9, CANSparkMaxLowLevel.MotorType.kBrushless);
        private DoubleSolenoid m_LiftSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 2);

        private boolean solenoidIsPowered = false;

        public void intake() {
            setPower(DRPreferences.get(INTAKE_INTAKE_SPEED));
        }

        public void outtake() {
            setPower(DRPreferences.get(INTAKE_OUTTAKE_SPEED));
        }

        public void stopIntake() {
            setPower(0);
        }

        public void setPower(double power){
            intakeMotor.set(power);
        }



        

        public void lift() {
            setSolenoidsReversed();
            setSolenoidsOn();
        }

        public void drop() {
            setSolenoidsForward();
            setSolenoidsOn();
        }

        public void toggleSolenoids() {
            m_LiftSolenoid.toggle();
            solenoidIsPowered = !solenoidIsPowered;
        }

        public void setSolenoidsForward() {
            m_LiftSolenoid.set(Value.kReverse);
        }

        public void setSolenoidsReversed() {
            m_LiftSolenoid.set(Value.kForward);
        }

        public void setCompressorOff() {        // this one is probably wrong
            m_LiftSolenoid.set(Value.kOff);
        }

        public void setSolenoidsOn() {
            if (solenoidIsPowered) toggleSolenoids();
        }

        public void setSolenoidsOff() {
            if (!solenoidIsPowered) toggleSolenoids();
        }
}