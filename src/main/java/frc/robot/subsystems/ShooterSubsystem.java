
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DroidRagePreferences;
import frc.robot.Constants.ShooterConstants;
import frc.robot.utils.TalonFXSetup;

public class ShooterSubsystem extends SubsystemBase {
  public final WPI_TalonFX motorPrimary;
  public final WPI_TalonFX motorSecondary;

  private double kP, kI, kD, kF;
  private int iZone;


  public ShooterSubsystem() {

    kP = DroidRagePreferences.getNumber("Shooter kP", 0.0465);
    kI = DroidRagePreferences.getNumber("Shooter kI", 0.0005);
    kD = DroidRagePreferences.getNumber("Shooter kD", 0.0);
    kF = DroidRagePreferences.getNumber("Shooter kF", 0.048);
    iZone = (int) DroidRagePreferences.getNumber("Shooter I-Zone", 150);

    
    motorPrimary = new WPI_TalonFX(ShooterConstants.kShooterMotorLeft);
    motorPrimary.setInverted(true);
    SupplyCurrentLimitConfiguration supplyCurrentLimit = new SupplyCurrentLimitConfiguration(true, 40, 45, 0.5);
    motorPrimary.configSupplyCurrentLimit(supplyCurrentLimit);
    motorPrimary.setNeutralMode(NeutralMode.Coast);

    motorPrimary.config_kP(0, kP);
    motorPrimary.config_kI(0, kI);   
    motorPrimary.config_kD(0, kD);  
    motorPrimary.config_kF(0, kF);  
    motorPrimary.config_IntegralZone(0, iZone);
    //motorLeft.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 10);

    motorPrimary.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    motorSecondary = new WPI_TalonFX(ShooterConstants.kFollowerMotorRight);
    motorSecondary.setInverted(false);  //MAKE SURE WE CHECK THE MOTORS SPINNING BEFOR TEST
    motorSecondary.configSupplyCurrentLimit(supplyCurrentLimit);
    motorSecondary.follow(motorPrimary);
    motorSecondary.setNeutralMode(NeutralMode.Coast);

    
    TalonFXSetup.velocityStatusFrames(motorSecondary);
    TalonFXSetup.velocityStatusFrames(motorPrimary);

  
    DroidRagePreferences.getNumber("Shooter Setpoint", 1000);

    this.setDefaultCommand(new RunCommand(() -> disable() , this));
  }

  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setManualOutput(double speed){
    motorPrimary.set(ControlMode.PercentOutput, speed);
  }

  public void setVelocity( double velocity){
    motorPrimary.set(ControlMode.Velocity, velocity);
  }

  public void DashboardVelocity(){
    //4096 sensor units per rev
    //velocity is in sensor units per 100ms (0.1 secs)
    //Shooter belt is 42 to 24
    //60000 milisecs in 1 min
    //RPM to U/100ms is rotations*4096 / 60000ms
    double wheelRpm = DroidRagePreferences.getNumber("Shooter Setpoint", 1000);
    double motorVelocity = (wheelRpm / 600 * 2048) / 1.75;
    motorPrimary.set(ControlMode.Velocity, motorVelocity);
  }

  public void setRPM(double wheelRPM){
    //Sensor Velocity in ticks per 100ms / Sensor Ticks per Rev * 600 (ms to min) * 1.5 gear ratio to shooter
    //Motor Velocity in RPM / 600 (ms to min) * Sensor ticks per rev / Gear Ratio 42to24
    double motorVelocity = (wheelRPM / 600 * 2048) / 1.75;
    setVelocity(motorVelocity);
  }

  public double getWheelRPM() {
    return (motorPrimary.getSelectedSensorVelocity() * 1.75) / 2048 * 600;
  }

  // public boolean isAtRPM() {
  //   return getWheelRPM() == motorPrimary.getClosedLoopTarget();
  // }

  public void fullSpeed(){
    setManualOutput(1.0);
  }
  
  public void shootLow() {
    setRPM(200);
  }

  public void shootHigh() {
    setRPM(300);
  }

  public void disable(){
    motorPrimary.set(ControlMode.PercentOutput,0);
  }

  public void dashboard() {
  SmartDashboard.putNumber("Shooter Velocity", motorPrimary.getSelectedSensorVelocity());
  SmartDashboard.putNumber("WheelRPM", getWheelRPM());
  SmartDashboard.putNumber("Shooter OutputPercentage", motorPrimary.getMotorOutputPercent());
  SmartDashboard.putNumber("Shooter LeftCurrent", motorPrimary.getSupplyCurrent());
  }

 
}