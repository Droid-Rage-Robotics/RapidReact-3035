
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

// import edu.wpi.first.networktables.NetworkTable;
// import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants.SubsystemConstants.ShooterConstants;
import frc.robot.utils.TalonFXSetup;

public class Shooter extends SubsystemBase {
  public final TalonFX motorPrimary;
  // public final WPI_TalonFX motorSecondary;

  private double kP, kI, kD, kF;
  private int iZone;
  private int rpmAdder;

  // private NetworkTable live_dashboard = NetworkTableInstance.getDefault().getTable("Live_Dashboard");



  public Shooter() {
    final int kShooterPort = 11
    //kSecondShooterPort = 1
      ;

    final double velocity = 204.8; // I believe 20480 is 1 revolution per minute
    final boolean secondaryInverted = false;
    final boolean primaryInverted = false;

    // public static final int pigeonID = 0;
    // public static final int driveMotor0 = 1;

    //PID values from Droid Rage Preferences
        kP = Preferences.getDouble("Shooter kP", 0.0465);
        kI = Preferences.getDouble("Shooter kI", 0);
        kD = Preferences.getDouble("Shooter kD", 0.0);
        kF = Preferences.getDouble("Shooter kF", 0.048);
        iZone = (int) Preferences.getDouble("Shooter I-Zone", 150);

        motorPrimary = new WPI_TalonFX(kShooterPort);
        motorPrimary.setInverted(false);
        SupplyCurrentLimitConfiguration supplyCurrentLimit = new SupplyCurrentLimitConfiguration(true, 40, 45, 0.5);
        motorPrimary.configSupplyCurrentLimit(supplyCurrentLimit);
        motorPrimary.setNeutralMode(NeutralMode.Coast);

    //Config for motorPrimary
        motorPrimary.config_kP(10, kP);
        motorPrimary.config_kI(0, kI);   
        motorPrimary.config_kD(0, kD);  
        motorPrimary.config_kF(10, kF);  
        motorPrimary.config_IntegralZone(0, iZone);
        //motorLeft.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 10);

        motorPrimary.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    // //Config for motorPrimary
    //     motorSecondary = new WPI_TalonFX(kSecondShooterPort);
    //     motorSecondary.setInverted(false);  //MAKE SURE WE CHECK THE MOTORS SPINNING BEFOR TEST
    //     motorSecondary.configSupplyCurrentLimit(supplyCurrentLimit);
    //     motorSecondary.follow(motorPrimary);
    //     motorSecondary.setNeutralMode(NeutralMode.Coast);

    
        // TalonFXSetup.velocityStatusFrames(motorSecondary);
        TalonFXSetup.velocityStatusFrames(motorPrimary);
        Preferences.getDouble("Shooter Setpoint", 1000);
        this.setDefaultCommand(new RunCommand(() -> disable() , this));
  }



    //Set Speed
        public void setManualOutput(double speed){
          motorPrimary.set(ControlMode.PercentOutput, speed);
        }
    //Set Velocity
        public void setVelocity( double velocity){
          motorPrimary.set(ControlMode.Velocity, velocity);
        }

        public void DashboardVelocity(){
          //4096 sensor units per rev
          //velocity is in sensor units per 100ms (0.1 secs)
          //Shooter belt is 42 to 24
          //60000 milisecs in 1 min
          //RPM to U/100ms is rotations*4096 / 60000ms
          double wheelRpm = Preferences.getDouble("Shooter Setpoint", 1000);
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

        public boolean isAtRPM() {
          return getWheelRPM() == motorPrimary.getClosedLoopTarget();
        }

        public boolean isGreaterThanRPM() {
          return getWheelRPM() > motorPrimary.getClosedLoopTarget();
        }
          //Max is 6380 RPM
        public void sendIt(){
          setRPM(5600 + rpmAdder);
          // setManualOutput(1.0);
        }
        
        public void shootLow() {
          setRPM(3300 + rpmAdder);
          // setManualOutput(0.3);
        }

        public void shootHigh() {
          setRPM(6000 + rpmAdder);
          // setManualOutput(0.6);
        }

        // public void shootLowAuto() {
        //   setRPM(2000 + rpmAdder);
        //   // setManualOutput(0.3);
        // }

        // public void shootHighAuto() {
        //   setRPM(4500 + rpmAdder);
        //   // setManualOutput(0.5);
        // }

        public void addRPM() {
          rpmAdder += 100;
        }

        public void subtractRPM() {
          rpmAdder -= 100;
        }

        public void disable(){
          motorPrimary.set(ControlMode.PercentOutput, 0);
        }

        public void dashboard() {
        SmartDashboard.putNumber("Shooter Velocity: ", motorPrimary.getSelectedSensorVelocity());
        SmartDashboard.putNumber("WheelRPM: ", getWheelRPM());
        SmartDashboard.putNumber("Shooter OutputPercentage: ", motorPrimary.getMotorOutputPercent());
        SmartDashboard.putNumber("Shooter LeftCurrent: ", motorPrimary.getSupplyCurrent());
        SmartDashboard.putNumber("Shooter RPMAdder: ", rpmAdder);
        // live_dashboard.getEntry("Shooter Velocity2: ").setDouble(motorPrimary.getSelectedSensorVelocity());
        }


        @Override
        public void periodic() {
          dashboard();
        }
 
}