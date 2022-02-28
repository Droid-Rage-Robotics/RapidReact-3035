// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants.IndexerConstants;

public class IndexerSubsystem extends SubsystemBase {
    private VictorSPX
        frontIndexer = new VictorSPX(0),
        backIndexer = new VictorSPX(1);
        
  
    /** Creates a new ExampleSubsystem. */
  public IndexerSubsystem() {

  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void stopIndexer() {
    
  }
  
  /*@Override
  public void initialize() {
    m_indexerSubsystem.stopIndexer();
    // This method will be called once per scheduler run
  }*/
}
