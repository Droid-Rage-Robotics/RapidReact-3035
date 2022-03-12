
package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.Drive.DriverControl;

import static frc.robot.Constants.DriveConstants.*;

import java.util.function.DoubleSupplier;

public class Drive extends SubsystemBase {
    // Motors
        private CANSparkMax 
            leftFrontMotor,
            leftRearMotor,
            rightRearMotor,
            rightFrontMotor;
        
    // NeoEncoders
        // private RelativeEncoder leftNeoEncoder,
        // rightNeoEncoder;


    // Encoders
        private final Encoder leftEncoder = new Encoder(        //Left Encoder
            kLeftEncoderPorts[0],
            kLeftEncoderPorts[1],
            kLeftEncoderReversed,
            CounterBase.EncodingType.k4X
        );
        private final Encoder rightEncoder = new Encoder(        //Right Encoder
                kRightEncoderPorts[0],
                kRightEncoderPorts[1],
                kRightEncoderReversed,
                CounterBase.EncodingType.k4X
        );

    // Gyro
        private AHRS navx = new AHRS(SPI.Port.kMXP);

        private DifferentialDrive drive;

        private DifferentialDriveOdometry externalOdometry;
        private DifferentialDriveOdometry internalOdometry;

        private boolean isControlsFlipped = false;

        private NetworkTable live_dashboard = NetworkTableInstance.getDefault().getTable("Live_Dashboard");

    public Drive() {
        // Motor Ports 
            leftFrontMotor = new CANSparkMax (kLeftFrontID, CANSparkMaxLowLevel.MotorType.kBrushless);
            leftRearMotor = new CANSparkMax  (kLeftRearID, CANSparkMaxLowLevel.MotorType.kBrushless);
            rightFrontMotor = new CANSparkMax(kRightFrontID, CANSparkMaxLowLevel.MotorType.kBrushless);
            rightRearMotor = new CANSparkMax (kRightRearID, CANSparkMaxLowLevel.MotorType.kBrushless);

            rightFrontMotor.setInverted(true);
            drive = new DifferentialDrive(leftFrontMotor, rightFrontMotor);
            drive.setSafetyEnabled(false);

            leftFrontMotor.setIdleMode(IdleMode.kBrake);
            rightFrontMotor.setIdleMode(IdleMode.kBrake);

        // Encoders
            leftEncoder.reset();
            rightEncoder.reset();

            leftEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadius / kThroughBoreEncoderResolution);
            rightEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadius / kThroughBoreEncoderResolution);

            // leftNeoEncoder = leftFrontMotor.getEncoder();
            // rightNeoEncoder = rightFrontMotor.getEncoder();

            // leftNeoEncoder.setPosition(0);
            // rightNeoEncoder.setPosition(0);


            externalOdometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));
            internalOdometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));

        // PID Controller
            leftFrontMotor.getPIDController();
            rightFrontMotor.getPIDController();

            resetAll();
    }
    public void tankDrive(double leftPower, double rightPower) {
        leftFrontMotor.set(leftPower);
        rightFrontMotor.set(rightPower);
    }


    public void SimpleDriveForward(double power) {
        tankDrive(DriveConstants.kslowModeSpeed, DriveConstants.kslowModeSpeed);
    }
    
    /**
     * Returns the currently-estimated pose of the robot.
     *
     * @return The pose.
     */
    public Pose2d getPose() {
        return externalOdometry.getPoseMeters();
    }

    /**
     * Returns the current wheel speeds of the robot.
     *
     * @return The current wheel speeds.
     */
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(leftEncoder.getRate(), rightEncoder.getRate());
    }

    /**
     * Resets the odometry to the specified pose.
     *
     * @param pose The pose to which to set the odometry.
     */
    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        navx.zeroYaw();
        internalOdometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
        externalOdometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
    }

    /**
     * Drives the robot using arcade controls.
     *
     * @param fwd the commanded forward movement
     * @param rot the commanded rotation
     */
    public void arcadeDrive(double fwd, double rot) {
        drive.arcadeDrive(fwd, rot);
    }

    public void slowDrive() {
        drive.setMaxOutput(kslowModeSpeed);
    }

    public void normalDrive() {
            drive.setMaxOutput(knormalModeSpeed);
    }

    public void turboDrive() {
        drive.setMaxOutput(kturboModeSpeed);
    }

    


    public void curvatureDrive(double fwd, double rot) {
        drive.curvatureDrive(fwd, rot, true);
    }

    /**
     * Controls the left and right sides of the drive directly with voltages.
     *
     * @param leftVolts  the commanded left output
     * @param rightVolts the commanded right output
     */
    public void tankDriveVolts(double leftVolts, double rightVolts) {
        leftFrontMotor.setVoltage(leftVolts);
        rightFrontMotor.setVoltage(rightVolts);
    }

    /**
     * Resets the drive encoders to currently read a position of 0.
     */
    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
        // leftNeoEncoder.setPosition(0);
        // rightNeoEncoder.setPosition(0);
    }

    /**
     * Gets the average distance of the two encoders.
     *
     * @return the average of the two encoder readings
     */
    public double getAverageEncoderDistance() {
        return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2.0;
    }

    /**
     * Gets the left drive encoder.
     *
     * @return the left drive encoder
     */
    public Encoder getLeftEncoder() {
        return leftEncoder;
    }

    /**
     * Gets the right drive encoder.
     *
     * @return the right drive encoder
     */
    public Encoder getRightEncoder() {
        return rightEncoder;
    }

    /**
     * Sets the max output of the drive.  Useful for scaling the drive to drive more slowly.
     *
     * @param maxOutput the maximum output to which the drive will be constrained
     */
    public void setMaxOutput(double maxOutput) {
        drive.setMaxOutput(maxOutput);
    }


    /**
     * Zeroes the heading of the robot.
     */
    public void zeroHeading() {
        navx.zeroYaw();
    }

    /**
     * Returns the heading of the robot.
     *
     * @return the robot's heading in degrees, from 180 to 180
     */
    public double getHeading() {
        return navx.getRotation2d().getDegrees();
    }

    /**
     * @return True if external encoders and internal encoders conflict
     */
   public boolean isEncoderError() {
        return internalOdometry.getPoseMeters().getTranslation().getDistance(externalOdometry.getPoseMeters().getTranslation()) > 0.5;
    }
    

    /**
     * Returns the turn rate of the robot.
     *
     * @return The turn rate of the robot, in degrees per second
     */
    public double getTurnRate() {
        return navx.getRate() * (kGyroReversed ? -1.0 : 1.0);
    }

    public DifferentialDriveKinematics getKinematics() {
        return kDriveKinematics;
    }
    

    public void resetAll() {
        resetOdometry(new Pose2d());
        navx.reset();
    }

    public void setControlsFlipped(boolean controlsFlipped) {
        isControlsFlipped = controlsFlipped;
    }

    public boolean isControlsFlipped() {
        return isControlsFlipped;
    }

    public void initDefaultCommands(DoubleSupplier forward, DoubleSupplier rotate) {
        setDefaultCommand(new DriverControl(
                this,
                forward,
                rotate
        ));
    }


    @Override
    public void periodic() {
        double leftDist = leftEncoder.getDistance();
        double rightDist = rightEncoder.getDistance();

        // Update the odometry in the periodic block
        externalOdometry.update(Rotation2d.fromDegrees(getHeading()),
                leftDist,
                rightDist);

                // TODO:  there is a very good chance I messed up the math here
        // internalOdometry.update(Rotation2d.fromDegrees(getHeading()),
        //         (-leftNeoEncoder.getPosition() / 8.73) * 2 * Math.PI * kWheelRadius,
        //         (rightNeoEncoder.getPosition() / 8.73) * 2 * Math.PI * kWheelRadius);
        internalOdometry.update(Rotation2d.fromDegrees(getHeading()),
            -leftEncoder.getDistance(),
            rightEncoder.getDistance());

        live_dashboard.getEntry("robotX").setDouble(Units.metersToFeet(getPose().getTranslation().getX()));
        live_dashboard.getEntry("robotY").setDouble(Units.metersToFeet(getPose().getTranslation().getY()));
        live_dashboard.getEntry("robotHeading").setDouble(getPose().getRotation().getRadians());

        SmartDashboard.putNumber("robotX", Units.metersToFeet(getPose().getTranslation().getX()));
        SmartDashboard.putNumber("robotY", Units.metersToFeet(getPose().getTranslation().getY()));
        SmartDashboard.putNumber("robotHeading", getPose().getRotation().getDegrees());

        SmartDashboard.putNumber("Internal RobotX", Units.metersToFeet(internalOdometry.getPoseMeters().getTranslation().getX()));
        SmartDashboard.putNumber("Internal RobotY", Units.metersToFeet(internalOdometry.getPoseMeters().getTranslation().getY()));

        SmartDashboard.putNumber("Left Encoder = ", leftEncoder.getDistance());
        SmartDashboard.putNumber("Right Encoder = ", rightEncoder.getDistance());

        // TODO: there is a good change i messed up the math
        // SmartDashboard.putNumber("NEO left Encoder", (leftNeoEncoder.getPosition() / 8.73) * 2 * Math.PI * kWheelRadius);
        // SmartDashboard.putNumber("NEO right encoder", (rightNeoEncoder.getPosition() / 8.73) * 2 * Math.PI * kWheelRadius);

        SmartDashboard.putNumber("NEO left Encoder", leftEncoder.getDistance());
        SmartDashboard.putNumber("NEO right encoder", rightEncoder.getDistance());
    }

}
