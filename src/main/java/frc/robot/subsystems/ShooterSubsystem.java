package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants.Shooter;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAlternateEncoder.Type;


public class ShooterSubsystem extends SubsystemBase {
    WPI_TalonFX
        shooter1 = new WPI_TalonFX(Shooter.right),
        shooter2 = new WPI_TalonFX(Shooter.left);

    private boolean shoot = false;

    public void robotInit () {

    }
        
      /** Creates a new ExampleSubsystem. */
    public ShooterSubsystem() {}
        
    @Override
    public void periodic() {
       // This method will be called once per scheduler run
     }
      
     @Override
    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    }
}