//Created by Spectrum3847
package frc.robot.subsystems.Shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.lib.util.Logger;
import frc.lib.util.TalconFXSetup;
import frc.robot.DroidRagePreferences;
import frc.robot.Robot;
import frc.robot.Constants.Shooter;
//import frc.robot.telemetry.Log;

public class ShooterSubsytem2 extends SubsystemBase {
  //public static final String name = Log._launcher;
  public final WPI_TalonFX motorLeft;
  public final WPI_TalonFX motorRight;
  public final double closeShoot = 0;
  public final double intitationLineShot = 0.6;
  public final double farShot = 1.0;

  private double kP, kI, kD, kF;
  private int iZone;

  /**
   * Creates a new Intake.
   */
  public ShooterSubsytem2() {
    //setName(name);
    //Pid

    kP = DroidRagePreferences.getNumber("Launcher kP", 0.0465);
    kI = DroidRagePreferences.getNumber("Launcher kI", 0.0005);
    kD = DroidRagePreferences.getNumber("Launcher kD", 0.0);
    kF = DroidRagePreferences.getNumber("Launcher kF", 0.048);
    iZone = (int) DroidRagePreferences.getNumber("Launcher I-Zone", 150);

    
    motorLeft = new WPI_TalonFX(Shooter.kLauncherMotorLeft);
    motorLeft.setInverted(true);
    SupplyCurrentLimitConfiguration supplyCurrentLimit = new SupplyCurrentLimitConfiguration(true, 40, 45, 0.5);
    motorLeft.configSupplyCurrentLimit(supplyCurrentLimit);
    motorLeft.setNeutralMode(NeutralMode.Coast);

    motorLeft.config_kP(0, kP);
    motorLeft.config_kI(0, kI);   
    motorLeft.config_kD(0, kD);  
    motorLeft.config_kF(0, kF);  
    motorLeft.config_IntegralZone(0, iZone);
    //motorLeft.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 10);

    motorLeft.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    motorRight = new WPI_TalonFX(Shooter.kFollowerMotorRight);
    motorRight.setInverted(false);   //should be inverse of motorLeft
    motorRight.configSupplyCurrentLimit(supplyCurrentLimit);
    motorRight.follow(motorLeft);
    motorRight.setNeutralMode(NeutralMode.Coast);

    
    TalonFXSetup.velocityStatusFrames(motorRight);
    TalonFXSetup.velocityStatusFrames(motorLeft);

    DroidRagePreferences.getNumber("Launcher Setpoint", 1000);

    this.setDefaultCommand(new RunCommand(() -> stop() , this));
  }

  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setManualOutput(double speed){
    motorLeft.set(ControlMode.PercentOutput, speed);
  }

  public void setVelocity( double velocity){
    motorLeft.set(ControlMode.Velocity, velocity);
  }

  public void DashboardVelocity(){
    //4096 sensor units per rev
    //velocity is in sensor units per 100ms (0.1 secs)
    //launcher belt is 42 to 24
    //60000 milisecs in 1 min
    //RPM to U/100ms is rotations*4096 / 60000ms
    double wheelRpm = DroidRagePreferences.getNumber("Launcher Setpoint", 1000);
    double motorVelocity = (wheelRpm / 600 * 2048) / 1.75;
    motorLeft.set(ControlMode.Velocity, motorVelocity);
  }

  public void setRPM(double wheelRPM){
    //Sensor Velocity in ticks per 100ms / Sensor Ticks per Rev * 600 (ms to min) * 1.5 gear ratio to shooter
    //Motor Velocity in RPM / 600 (ms to min) * Sensor ticks per rev / Gear Ratio 42to24
    double motorVelocity = (wheelRPM / 600 * 2048) / 1.75;
    setVelocity(motorVelocity);
  }

  public double getWheelRPM() {
    return (motorLeft.getSelectedSensorVelocity() * 1.75) / 2048 * 600;
  }
  public void full(){
    setManualOutput(1.0);
  }

  public void stop(){
    motorLeft.set(ControlMode.PercentOutput,0);
  }

  public void dashboard() {
  SmartDashboard.putNumber("Launcher/Velocity", motorLeft.getSelectedSensorVelocity());
  SmartDashboard.putNumber("Launcher/WheelRPM", getWheelRPM());
  SmartDashboard.putNumber("Launcher/OutputPercentage", motorLeft.getMotorOutputPercent());
  SmartDashboard.putNumber("Launcher/LeftCurrent", motorLeft.getSupplyCurrent());
  }

  /*public static void printDebug(String msg){
    Logger.println(msg, name, Logger.low1);
  }
  
  public static void printInfo(String msg){
    Logger.println(msg, name, Logger.normal2);
  }
  
  public static void printWarning(String msg) {
    Logger.println(msg, name, Logger.high3);
  }

  public static void printError(String msg) {
    Logger.println(msg, name, Logger.critical4);
  }*/
}