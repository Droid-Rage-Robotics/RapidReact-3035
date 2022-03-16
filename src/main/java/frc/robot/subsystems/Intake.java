package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
        public static int IntakeMotorPort = 9;
        public static double IntakePower = 1; // was 1.0
        public static int[] kSolenoidPort  = {4, 5};


        //Intake Motor
        private CANSparkMax intakeMotor = new CANSparkMax(IntakeMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);

        // private Compressor pcmCompressor1  = new Compressor(0, PneumaticsModuleType.CTREPCM);
        // private Compressor pcmCompressor2  = new Compressor(1, PneumaticsModuleType.CTREPCM);

        // private boolean enabled = pcmCompressor1.enabled();
        // private boolean pressureSwitch = pcmCompressor1.getPressureSwitchValue();
        // private double current = pcmCompressor1.getCurrent();
        
        // //We use CTRE  Pneumatics Control Module
        // //2 double solenoids for 2 pistons
        private DoubleSolenoid m_LiftSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, kSolenoidPort[0], kSolenoidPort[1]);
        //private DoubleSolenoid m_RightLiftDoubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, IntakeConstants.kRightSolenoidPorts[0], IntakeConstants.kRightSolenoidPorts[1]);
        private boolean solenoidIsPowered = false;
     
    public Intake() {
        // m_LiftSolenoid.set(Value.kForward);
        // m_RightLiftDoubleSolenoid.set(Value.kForward); 
    }


    //Intake
        public void intake() {
            setIntakePower(IntakePower);
            // lLiftDoubleSolenoid.set(Value.kForward);
            // rLiftDoubleSolenoid.set(Value.kForward);
        }

    //Outtake
        public void outtake() {
            setIntakePower(-IntakePower);
            // lLiftDoubleSolenoid.set(Value.kForward);
            // rLiftDoubleSolenoid.set(Value.kForward);
        }

    //Disable
        public void stopIntake() {
            setIntakePower(0);
        }

    // public void intakeLift() {
    //     // lLiftDoubleSolenoid.set(Value.kReverse);
    //     // rLiftDoubleSolenoid.set(Value.kReverse);
    // }

        public void setIntakePower(double power){
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
            // m_RightLiftDoubleSolenoid.toggle();
            solenoidIsPowered = !solenoidIsPowered;
        }

        public void setSolenoidsForward() {
            m_LiftSolenoid.set(Value.kForward);
            // m_RightLiftDoubleSolenoid.set(Value.kForward);
        }

        public void setSolenoidsReversed() {
            m_LiftSolenoid.set(Value.kReverse);
            // m_RightLiftDoubleSolenoid.set(Value.kReverse);
        }

        public void setCompressorOff() {        // this one is probably wrong
            m_LiftSolenoid.set(Value.kOff);
            // m_RightLiftDoubleSolenoid.set(Value.kOff);
        }

        public void setSolenoidsOn() {
            if (solenoidIsPowered) toggleSolenoids();
        }

        public void setSolenoidsOff() {
            if (!solenoidIsPowered) toggleSolenoids();
        }
}