// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

// import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
// import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.DrivePorts;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
// import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
// import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
// import com.revrobotics.SparkMaxAlternateEncoder.Type;

public class DriveSubsystem extends SubsystemBase {
  /**
   * SPARK MAX controllers are intialized over CAN by constructing a CANSparkMax
   * object
   * 
   * The CAN ID, which can be configured using the SPARK MAX Client, is passed as
   * the
   * first parameter
   * 
   * The motor type is passed as the second parameter. Motor type can either be:
   * com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless
   * com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushed
   * 
   * The example below initializes four brushless motors with CAN IDs 1 and 2.
   * Change
   * these parameters to match your setup
   */
  private CANSparkMax leftFront = new CANSparkMax(DrivePorts.leftFront, MotorType.kBrushless),
      leftRear = new CANSparkMax(DrivePorts.leftRear, MotorType.kBrushless),
      rightFront = new CANSparkMax(DrivePorts.rightFront, MotorType.kBrushless),
      rightRear = new CANSparkMax(DrivePorts.rightRear, MotorType.kBrushless);

  /**
   * The RestoreFactoryDefaults method can be used to reset the configuration
   * parameters
   * in the SPARK MAX to their factory default state. If no argument is passed,
   * these
   * parameters will not persist between power cycles
   */
  // It appears that these do not work anymore

  // leftFront.restoreFactoryDefaults();
  // leftRear.restoreFactoryDefaults();

  // The motors on the left side of the drive.
  private final MotorControllerGroup m_leftMotors = new MotorControllerGroup(
      leftFront,
      leftRear);

  // The motors on the right side of the drive.
  private final MotorControllerGroup m_rightMotors = new MotorControllerGroup(
      rightFront,
      rightRear);

  // The robot's drive
  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);

  // The left-side drive encoder
  private final DutyCycleEncoder leftEncoder = new DutyCycleEncoder(DrivePorts.leftEncoder);

  // The right-side drive encoder
  private final DutyCycleEncoder rightEncoder = new DutyCycleEncoder(DrivePorts.rightEncoder);

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightMotors.setInverted(true);

    // We can optionally set the input deadband aka deadzone
    // This is set to 0.02 by default
    // m_drive.setDeadband(0.02);

    // Sets the distance per pulse for the encoders
    // leftFront.getEncoder(SparkMaxAlternateEncoder.Type, countsPerRev)
    leftEncoder.setDistancePerRotation(DriveConstants.kEncoderDistancePerRevolution);
    rightEncoder.setDistancePerRotation(DriveConstants.kEncoderDistancePerRevolution);

    // Motor safety forces motors to turn off by default. it track
    // how long it has been and automagically turns off the motors
    // by default all drive objects enable motor safety
    // m_drive.setSafetyEnabled(enabled);
    // dont disable it unless you need to for some reason

    /**
     * Drive Modes
     * Note
     * The DifferentialDrive class contains three different default modes of driving your robot’s motors.
     * Tank Drive, which controls the left and right side independently
     * Arcade Drive, which controls a forward and turn speed
     * Curvature Drive, a subset of Arcade Drive, which makes your robot handle like a car with constant-curvature turns.
     */
    // Try all 3. they all have advantages

  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void arcadeDrive(double fwd, double rot) {
    m_drive.arcadeDrive(fwd, rot);

  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    m_drive.tankDrive(leftSpeed, rightSpeed);

  }

  public void Cu(double xSpeed, double zRotation, boolean allowTurnInPlace) {
    m_drive.curvatureDrive(xSpeed, zRotation, allowTurnInPlace);

  }

  /** Resets the drive encoders to currently read a position of 0. */
  public void resetEncoders() {
    leftEncoder.reset();
    rightEncoder.reset();
  }

  /**
   * Gets the average distance of the TWO encoders.
   *
   * @return the average of the TWO encoder readings
   */
  public double getAverageEncoderDistance() {
    return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2.0;
  }

  /**
   * Gets the left drive encoder.
   *
   * @return the left drive encoder
   */
  public DutyCycleEncoder getLeftEncoder() {
    return leftEncoder;
  }

  /**
   * Gets the right drive encoder.
   *
   * @return the right drive encoder
   */
  public DutyCycleEncoder getRightEncoder() {
    return rightEncoder;
  }

  /**
   * Sets the max output of the drive. Useful for scaling the drive to drive more
   * slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }
}
