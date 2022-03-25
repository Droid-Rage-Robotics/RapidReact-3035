
package frc.robot.subsystems;

import static frc.robot.DRPreferences.DoubleKey.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DRPreferences;

public class Shooter extends SubsystemBase {

  private final TalonFX m_front = new TalonFX(11);
  private final TalonFX m_back = new TalonFX(13);
                      
  private int rpmAdder;

  // private NetworkTable live_dashboard = NetworkTableInstance.getDefault().getTable("Live_Dashboard");



  public Shooter() {

    
        // motorFront = new WPI_TalonFX(11);


    // //Config for motorPrimary
        // motorBack = new WPI_TalonFX(13);
        // motorBack.setInverted(false);
        // motorBack.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 45, 0.5));
        // motorBack.setNeutralMode(NeutralMode.Coast);

        // motorBack.config_kP(10, DRPreferences.get(BACK_SHOOTER_P));
        // motorBack.config_kI(0, DRPreferences.get(BACK_SHOOTER_I));   
        // motorBack.config_kD(0, DRPreferences.get(BACK_SHOOTER_D));  
        // motorBack.config_kF(10, DRPreferences.get(BACK_SHOOTER_F));  
        // motorBack.config_IntegralZone(0, (int) DRPreferences.get(BACK_SHOOTER_I_ZONE));
    //     motorSecondary.configSupplyCurrentLimit(supplyCurrentLimit);
    //     motorSecondary.follow(motorPrimary);
    //     motorSecondary.setNeutralMode(NeutralMode.Coast);

    
        // TalonFXSetup.velocityStatusFrames(motorSecondary);
        // TalonFXSetup.velocityStatusFrames(motorFront);
        // TalonFXSetup.velocityStatusFrames(motorBack);
        // Preferences.getDouble("Shooter Setpoint", 1000);
        // this.setDefaultCommand(new RunCommand(() -> stop() , this));
        // motorBack.follow(motorFront);
        // motorBack.setInverted(InvertType.OpposeMaster);






    //     motorFront.setInverted(true);
    //     motorFront.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 45, 0.5));
    //     motorFront.setNeutralMode(NeutralMode.Coast);

    // //Config for motorPrimary
    m_front.setInverted(true);
    m_back.setInverted(false);
    m_front.config_kP(10, DRPreferences.get(FRONT_SHOOTER_P));
    m_front.config_kI(0, DRPreferences.get(FRONT_SHOOTER_I));   
    m_front.config_kD(0, DRPreferences.get(FRONT_SHOOTER_D));  
    m_front.config_kF(10, DRPreferences.get(FRONT_SHOOTER_F));  
    m_front.config_IntegralZone(0, (int) DRPreferences.get(FRONT_SHOOTER_I_ZONE));
    //motorLeft.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 10);

    // motorFront.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    m_front.setNeutralMode(NeutralMode.Coast);
    m_back.setNeutralMode(NeutralMode.Coast);

    m_back.follow(m_front);   //Foll0w Front
    m_back.setInverted(InvertType.OpposeMaster);  //Inverted from Front
  }

  @Override
  public void periodic() {
    dashboard();
  }



//Set Speed
  public void setManualOutput(double frontSpeed, double backSpeed){
    m_front.set(ControlMode.PercentOutput, frontSpeed);
    // motorBack.set(ControlMode.PercentOutput, backSpeed);
  }

  public void setFrontOutput(double frontSpeed){
    setManualOutput(frontSpeed, 0);
  }
//Set Velocity
  public void setVelocity(double frontVelocity, double backVelocity){
    m_front.set(ControlMode.Velocity, frontVelocity);
    // zmotorBack.set(ControlMode.Velocity, backVelocity);
  }

  public void setVelocity(double frontVelocity){
    setVelocity(frontVelocity, 0);
  }

  public void DashboardVelocity(){
    // 4096 sensor units per rev
    // velocity is in sensor units per 100ms (0.1 secs)
    // Shooter belt is 42 to 24
    // 60000 milisecs in 1 min
    // RPM to U/100ms is rotations*4096 / 60000ms
    double wheelRpm = Preferences.getDouble("Shooter Setpoint", 1000);
    double motorVelocity = (wheelRpm / 600 * 2048) / 1.75;
    m_front.set(ControlMode.Velocity, motorVelocity);
  }

  public void setRPM(double wheelRPM){
    // Sensor Velocity in ticks per 100ms / Sensor Ticks per Rev * 600 (ms to min) * 1.5 gear ratio to shooter
    // Motor Velocity in RPM / 600 (ms to min) * Sensor ticks per rev / Gear Ratio 42to24
    double motorVelocity = (wheelRPM / 600 * 2048) / 1.75;
    setVelocity(motorVelocity);
  }

  public void setRPM(double frontWheelRPM, double backWheelRPM){
    // Sensor Velocity in ticks per 100ms / Sensor Ticks per Rev * 600 (ms to min) * 1.5 gear ratio to shooter
    // Motor Velocity in RPM / 600 (ms to min) * Sensor ticks per rev / Gear Ratio 42to24
    double frontMotorVelocity = (frontWheelRPM / 600 * 2048) / 1.75;
    double backMotorVelocity = (backWheelRPM / 600 * 2048) / 1.75;
    setVelocity(frontMotorVelocity, backMotorVelocity);
  }

  public double getWheelRPM() {
    return (m_front.getSelectedSensorVelocity() * 1.75) / 2048 * 600;
  }

  public boolean isAtRPM() {
    return getWheelRPM() == m_front.getClosedLoopTarget();
  }

  public boolean isGreaterThanRPM() {
    return getWheelRPM() > m_front.getClosedLoopTarget();
  }

  public void shootFarHigh() {
    setRPM(
      DRPreferences.get(FRONT_SHOOTER_FAR_HIGH) + rpmAdder
      // ,DRPreferences.get(BACK_SHOOTER_FAR_HIGH) + rpmAdder
    );
  }

    public void shootCloseHigh(){
    setRPM(
      DRPreferences.get(FRONT_SHOOTER_CLOSE_HIGH) + rpmAdder
      // DRPreferences.get(BACK_SHOOTER_CLOSE_HIGH) + rpmAdder
    );
  }
  
  public void shootLow() {
    setRPM(
      DRPreferences.get(FRONT_SHOOTER_LOW) + rpmAdder
      // DRPreferences.get(BACK_SHOOTER_LOW) + rpmAdder
    );
  }

  

  public void shootLowAuto() {
    setRPM(
      DRPreferences.get(FRONT_SHOOTER_AUTO_LOW) + rpmAdder,
      DRPreferences.get(BACK_SHOOTER_AUTO_LOW) + rpmAdder
    );
  }

  public void shootHighFarAuto() {
    setRPM(
      DRPreferences.get(FRONT_SHOOTER_AUTO_FAR_HIGH) + rpmAdder,
      DRPreferences.get(BACK_SHOOTER_AUTO_HIGH) + rpmAdder
    );
  }

  public void shootHighCloseAuto() {
    setRPM(
      DRPreferences.get(FRONT_SHOOTER_AUTO_CLOSE_HIGH) + rpmAdder,
      DRPreferences.get(BACK_SHOOTER_AUTO_CLOSE_HIGH) + rpmAdder
    );
  }

  public void addRPM() {
    rpmAdder += 100;
  }

  public void subtractRPM() {
    rpmAdder -= 100;
  }

  public void stop(){
    setRPM(0, 0);
  }

  public void dashboard() {
  SmartDashboard.putNumber("Shooter Velocity: ", m_front.getSelectedSensorVelocity());
  SmartDashboard.putNumber("WheelRPM: ", getWheelRPM());
  SmartDashboard.putNumber("Shooter OutputPercentage: ", m_front.getMotorOutputPercent());
  SmartDashboard.putNumber("Shooter LeftCurrent: ", m_front.getSupplyCurrent());
  SmartDashboard.putNumber("Shooter RPMAdder: ", rpmAdder);
  // live_dashboard.getEntry("Shooter Velocity2: ").setDouble(motorPrimary.getSelectedSensorVelocity());
  }
 
}