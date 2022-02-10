package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivePorts;

public class Drivetrain extends SubsystemBase{
    private final PWMSparkMax leftFront = 
        new PWMSparkMax(DrivePorts.kDrivetrainPorts[0]);

    private final PWMSparkMax leftMiddle = 
        new PWMSparkMax(DrivePorts.kDrivetrainPorts[1]);
            
    private final PWMSparkMax leftBack = 
        new PWMSparkMax(DrivePorts.kDrivetrainPorts[2]);
                
    private final PWMSparkMax rightFront = 
        new PWMSparkMax(DrivePorts.kDrivetrainPorts[3]);
            
    private final PWMSparkMax rightMiddle = 
        new PWMSparkMax(DrivePorts.kDrivetrainPorts[4]);
                
    private final PWMSparkMax rightBack = 
        new PWMSparkMax(DrivePorts.kDrivetrainPorts[5]);
    
    public Drivetrain () {

    }

    @Override
    public void periodic() {
      // This method will be called once per scheduler run
    }
  
    @Override
    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    }
    
}
