// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IndexerConstants;

public class IndexerSubsystem extends SubsystemBase {
    private 775ProMax
        frontIndexer = new 775ProMax(Indexer.front, MotorType.kBrushless),
        backIndexer = new 775ProMax(Indexer.back, MotorType.kBrushless);
        
  
    /** Creates a new ExampleSubsystem. */
  public IndexerSubsystem() {}
  

  /*@Override
  public void initialize() {
    m_indexerSubsystem.stopIndexer();
    // This method will be called once per scheduler run
  }*/
  
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
}
