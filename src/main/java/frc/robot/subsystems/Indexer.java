// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
// import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMax.IdleMode;
// import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DRPreferences;

import static frc.robot.DRPreferences.DoubleKey.*;

public class Indexer extends SubsystemBase {

    public static final int
        frontIndexerPort = 10,
        backIndexerPort = 8; 
    
    // private CANSparkMax
    //     frontIndexer,
    //     backIndexer;
        private VictorSPX 
          frontIndexer,
          backIndexer;

    /** Creates a new Indexer Subsystem. */
      public Indexer() {
        frontIndexer = new VictorSPX(frontIndexerPort);
        backIndexer = new VictorSPX(backIndexerPort);
        // frontIndexer = new CANSparkMax(frontIndexerPort, MotorType.kBrushed);
        // frontIndexer.setIdleMode(IdleMode.kCoast);
        
        // backIndexer = new CANSparkMax(backIndexerPort, MotorType.kBrushed);
        // backIndexer.setIdleMode(IdleMode.kBrake);
        // backIndexer.setInverted(true);    //Invert Back Motor  
      }


      //controls the front motors independently
      public void intakeFrontIndexer(){
        setFrontIndexPower(DRPreferences.get(INDEXER_FRONT_INTAKE_SPEED));
      }
      public void outtakeFrontIndexer() {
        setFrontIndexPower(DRPreferences.get(INDEXER_OUTTAKE_SPEED));
      }
      public void stopFrontIndexer() {
        setFrontIndexPower(0);
      }

      //controls the back motors independently
      public void intakeBacktIndexer(){
        setFrontIndexPower(DRPreferences.get(INDEXER_INTAKE_SPEED));
      }
      public void outtakeBackIndexer() {
        setBackIndexPower(DRPreferences.get(INDEXER_OUTTAKE_SPEED));
      }
      public void stopBackIndexer() {
        setBackIndexPower(0);
      }

      //Controls the front and back motors
      public void intakeBothIndexer(){
        setFrontIndexPower(DRPreferences.get(INDEXER_FRONT_INTAKE_SPEED));
        setBackIndexPower(DRPreferences.get(INDEXER_INTAKE_SPEED));
      }
      public void outtakeBothIndexer() {
        setFrontIndexPower(DRPreferences.get(INDEXER_OUTTAKE_SPEED));
        setBackIndexPower(DRPreferences.get(INDEXER_OUTTAKE_SPEED));
      }
      public void stopBothIndexer() {
        setFrontIndexPower(0);
        setBackIndexPower(0);
      }





      //Set Intake Power - (Used in other functions)
      public void setFrontIndexPower(double power){
        // frontIndexer.set(power);
        frontIndexer.set(ControlMode.PercentOutput, power);
        
      }

      public void setBackIndexPower(double power) {
        // backIndexer.set(power);
        backIndexer.set(ControlMode.PercentOutput, power);
      }
}
