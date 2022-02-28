// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IndexerConstants;

public class IndexerSubsystem extends SubsystemBase {
    private VictorSPX
        frontIndexer,
        backIndexer;
  
    /** Creates a new ExampleSubsystem. */
      public IndexerSubsystem() {
        frontIndexer = new VictorSPX(IndexerConstants.frontIndexer);
        backIndexer = new VictorSPX(IndexerConstants.backIndexer);

        //Invert Back Motor
          backIndexer.setInverted(true);
      }
    
      @Override
      public void periodic() {
        // This method will be called once per scheduler run
      }

      @Override
      public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
      }
  //Indexer Up
      public void upIndexer() {
        setIndexerPower(IndexerConstants.upSpeed);
      }
  //Indexer Down
      public void downIndexer() {
        setIndexerPower(IndexerConstants.downSpeed);
      }
  //Indexer Stop
      public void stopIndexer() {
        setIndexerPower(IndexerConstants.stopSpeed);
      }

  //Set Intake Power - (Used in other functions)
      public void setIndexerPower(double power){
        frontIndexer.set(ControlMode.PercentOutput, power);
        backIndexer.set(ControlMode.PercentOutput, power);
      }
    
  /*@Override
  public void initialize() {
    m_indexerSubsystem.stopIndexer();
    // This method will be called once per scheduler run
  }*/
}
