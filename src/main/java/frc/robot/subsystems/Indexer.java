// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IndexerConstants;

public class Indexer extends SubsystemBase {
    private CANSparkMax
        frontIndexer,
        backIndexer;
  
    /** Creates a new ExampleSubsystem. */
      public Indexer() {
        frontIndexer = new CANSparkMax(IndexerConstants.frontIndexerPort, MotorType.kBrushed);
        backIndexer = new CANSparkMax(IndexerConstants.backIndexerPort, MotorType.kBrushed);

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

      //controls the both motors
      public void outtakeBothIndexer() {
        setIndexPowers(IndexerConstants.outtakeSpeed);
      }
      public void intakeBothIndexer() {
        setIndexPowers(IndexerConstants.intakeSpeed);
      }
      public void stopBothIndexer() {
        setIndexPowers(IndexerConstants.stopSpeed);
      }

      //controls the front motors independently
      public void intakeFrontIndexer(){
        setFrontIndexPower(IndexerConstants.intakeSpeed);
      }
      public void outtakeFrontIndexer() {
        setFrontIndexPower(IndexerConstants.outtakeSpeed);
      }
      public void disableFrontIndexer() {
        setFrontIndexPower(IndexerConstants.stopSpeed);
      }

      //controls the back motors independently
      public void intakeBacktIndexer(){
        setFrontIndexPower(IndexerConstants.intakeSpeed);
      }
      public void outtakeBackIndexer() {
        setBackIndexPower(IndexerConstants.outtakeSpeed);
      }
      public void disableBackIndexer() {
        setBackIndexPower(IndexerConstants.stopSpeed);
      }


      //Set Intake Power - (Used in other functions)
      public void setFrontIndexPower(double power){
        frontIndexer.set(power);
        
      }

      public void setBackIndexPower(double power) {
        backIndexer.set(power);
      }

      public void setIndexPowers(double power) {
        setFrontIndexPower(power);
        setBackIndexPower(power);
      }
    
  /*@Override
  public void initialize() {
    m_indexerSubsystem.stopIndexer();
    // This method will be called once per scheduler run
  }*/
}
