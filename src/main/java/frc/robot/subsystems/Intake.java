package frc.robot.subsystems;

import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator.Validity;
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

        private Compressor pcmCompressor  = new Compressor(0, PneumaticsModuleType.CTREPCM);
        private Compressor phCompressor  = new Compressor(1, PneumaticsModuleType.REVPH);

        private boolean enabled = pcmCompressor.enabled();
        private boolean pressureSwitch = pcmCompressor.getPressureSwitchValue();
        private double current = pcmCompressor.getCurrent();
        
        //2 double solenoids for 2 pistons
        private DoubleSolenoid leftliftDoubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 2);
        private DoubleSolenoid rightLiftDoubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 3, 4);
     
    public Intake() {
        leftliftDoubleSolenoid.set(Value.kForward);
        leftliftDoubleSolenoid.set(Value.kForward);
        pcmCompressor.enableDigital();
        pcmCompressor.disable();




        intakeMotor = new CANSparkMax(IntakeConstants.IntakeMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
        //Intake Current Limit
        intakeMotor.setSmartCurrentLimit(40);
        // liftDoubleSolenoid.get();
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