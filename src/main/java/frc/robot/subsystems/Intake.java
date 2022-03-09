package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
        //Intake Motor
        private CANSparkMax intakeMotor;

        private Compressor pcmCompressor1  = new Compressor(0, PneumaticsModuleType.CTREPCM);
        private Compressor pcmCompressor2  = new Compressor(1, PneumaticsModuleType.CTREPCM);

        private boolean enabled = pcmCompressor1.enabled();
        private boolean pressureSwitch = pcmCompressor1.getPressureSwitchValue();
        private double current = pcmCompressor1.getCurrent();
        
        //We use CTRE  Pneumatics Control Module
        //2 double solenoids for 2 pistons
        private DoubleSolenoid lLiftDoubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 2);
        private DoubleSolenoid rLiftDoubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 3, 4);
     
    public Intake() {
        lLiftDoubleSolenoid.set(Value.kOff);
        lLiftDoubleSolenoid.set(Value.kForward);
        lLiftDoubleSolenoid.set(Value.kReverse);
        pcmCompressor1.enableDigital();
        pcmCompressor1.disable();

        rLiftDoubleSolenoid.set(Value.kOff);
        rLiftDoubleSolenoid.set(Value.kForward);
        rLiftDoubleSolenoid.set(Value.kReverse);
        pcmCompressor2.enableDigital();
        pcmCompressor2.disable();

    
        lLiftDoubleSolenoid.get();
        rLiftDoubleSolenoid.get();

        //Intake Current Limit
        intakeMotor.setSmartCurrentLimit(40);

        intakeMotor = new CANSparkMax(IntakeConstants.IntakeMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);    
    }


    //Intake
        public void intakeBalls() {
            setIntakePower(IntakeConstants.IntakePower);
            lLiftDoubleSolenoid.set(Value.kForward);
            rLiftDoubleSolenoid.set(Value.kForward);
        }
    //Outtake
        public void outtakeBalls() {
            setIntakePower(-IntakeConstants.IntakePower);
            lLiftDoubleSolenoid.set(Value.kForward);
            rLiftDoubleSolenoid.set(Value.kForward);
        }
    //Disable
        public void disableIntake() {
            setIntakePower(0);
        }
        public void intakeLift() {
            lLiftDoubleSolenoid.set(Value.kReverse);
            rLiftDoubleSolenoid.set(Value.kReverse);
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