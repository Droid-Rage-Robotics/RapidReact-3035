package frc.robot.subsystems.Shooter;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
// import com.revrobotics.CANSparkMax;

// import edu.wpi.first.wpilibj.DigitalSource;
// import edu.wpi.first.wpilibj.DutyCycleEncoder;
// import edu.wpi.first.wpilibj.Encoder;
// import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import frc.robot.Constants.DriveConstants;
// import frc.robot.Constants.DrivePorts;
import frc.robot.Constants.Shooter;
// import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
// import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
// import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import com.revrobotics.CANSparkMaxLowLevel.MotorType;
// import com.revrobotics.SparkMaxAlternateEncoder.Type;


public class ShooterSubsystem extends SubsystemBase {
    /**
     * Parameters:
        deviceNumber device ID of motor controller
        canbus Name of the CANbus; can be a CANivore device name or serial number. Pass in nothing or "rio" to use the roboRIO
     */
    WPI_TalonFX primaryShooter = new WPI_TalonFX(Shooter.PrimaryDeviceID, Shooter.primaryCanbus);
    WPI_TalonFX secondaryShooter = new WPI_TalonFX(Shooter.SecondaryDeviceID, Shooter.secondaryCanbus);


    // these motors have 2048 ticks per revolution
    public ShooterSubsystem() {
        primaryShooter.setInverted(Shooter.primaryInverted);
        secondaryShooter.setInverted(Shooter.secondaryInverted);
        secondaryShooter.set(TalonFXControlMode.Follower, Shooter.PrimaryDeviceID);
        
        // Reading the TALONFX documentation is highly reccomended
        // I think 1 rpm = 2048 ticks * 1 seconds / 10 to convert from ms to seconds
        // so 204.8 velocity is 1 rpm
        // leftShooter.set(TalonFXControlMode.Velocity, 0); // measured in position achange / 100 ms so basically i think its like how many ticks per 100 ms
    }

    public void shoot() {
        primaryShooter.set(TalonFXControlMode.Velocity, Shooter.velocity);
    }
    
    public void stop() {
        primaryShooter.set(TalonFXControlMode.Velocity, 0);
    }
}