
package frc.robot.subsystems;

import static frc.robot.Constants.DriveConstants.kGyroReversed;
import static frc.robot.Constants.DriveConstants.kThroughBoreEncoderResolution;
import static frc.robot.Constants.DriveConstants.kWheelRadiusInches;
import static frc.robot.Constants.DriveConstants.knormalModeSpeed;
import static frc.robot.Constants.DriveConstants.kslowModeSpeed;
import static frc.robot.Constants.DriveConstants.kturboModeSpeed;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive2 extends SubsystemBase {
    // TODO: calibrate NAVX at start
    // Drivetrain
    private CANSparkMax 
        leftFrontMotor = new CANSparkMax (3, CANSparkMaxLowLevel.MotorType.kBrushless),
        leftRearMotor = new CANSparkMax (2, CANSparkMaxLowLevel.MotorType.kBrushless),
        rightFrontMotor = new CANSparkMax (5, CANSparkMaxLowLevel.MotorType.kBrushless),
        rightRearMotor = new CANSparkMax (4, CANSparkMaxLowLevel.MotorType.kBrushless);

    private MotorControllerGroup
        leftMotors = new MotorControllerGroup(leftFrontMotor, leftRearMotor),
        rightMotors = new MotorControllerGroup(rightFrontMotor, rightRearMotor);
    
    private DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);


    // Encoders
    public final Encoder leftEncoder = new Encoder (        //Left Encoder
        2,
        3,
        true, // Reversed
        CounterBase.EncodingType.k4X
    );

    public final Encoder rightEncoder = new Encoder(        //Right Encoder
        0,
        1,
        false, //reversed
        CounterBase.EncodingType.k4X
    );

    // Gyro
    private AHRS navx = new AHRS(SPI.Port.kMXP);

    private double kP = 0.03;

    public Drive2() {
        leftFrontMotor .setSmartCurrentLimit(40);
        leftRearMotor  .setSmartCurrentLimit(40);
        rightFrontMotor.setSmartCurrentLimit(40);
        rightRearMotor .setSmartCurrentLimit(40);

        rightMotors.setInverted(false);
        leftMotors.setInverted(true);

        rightFrontMotor.setIdleMode(IdleMode.kCoast);
        rightRearMotor.setIdleMode(IdleMode.kCoast);
        leftFrontMotor.setIdleMode(IdleMode.kCoast);
        leftRearMotor.setIdleMode(IdleMode.kCoast);
        
        drive.setSafetyEnabled(false);

        // Encoders
        leftEncoder.reset();
        rightEncoder.reset();

        leftEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadiusInches / kThroughBoreEncoderResolution);
        rightEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadiusInches / kThroughBoreEncoderResolution);

        // PID Controller
        leftFrontMotor.getPIDController();
        rightFrontMotor.getPIDController();
        leftRearMotor.getPIDController();
        rightRearMotor.getPIDController();

        
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(leftEncoder.getRate(), rightEncoder.getRate());
    }

    /**
     * Resets the drive encoders to currently read a position of 0.
     */
    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    public void resetOdometry() {
        resetEncoders();
        resetHeading();
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

    public void tankDrive(double leftSpeed, double rightSpeed) {
        drive.tankDrive(leftSpeed, rightSpeed);
    }

    // public void tankDrive(double leftPower, double rightPower) {
    //     leftFrontMotor.set(leftPower);
    //     rightFrontMotor.set(rightPower);
    // }

    public void curvatureDrive(double forward, double rotation) {
        drive.curvatureDrive(forward, rotation, true);
    }

    public void slowDrive() {
        drive.setMaxOutput(kslowModeSpeed);     //0.3
    }

    public void normalDrive() {
        drive.setMaxOutput(knormalModeSpeed);   //1
    }

    public void turboDrive() {
        drive.setMaxOutput(kturboModeSpeed);    //0.65
    }

    public void setRightSideInverted() {
        rightMotors.setInverted(true);
    }

    public void setRightSideForward() {
        rightMotors.setInverted(false);
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
     * Zeroes the heading of the robot.
     */
    public void resetHeading() {
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
     * Returns the turn rate of the robot.
     *
     * @return The turn rate of the robot, in degrees per second
     */
    public double getTurnRate() {
        return navx.getRate() * (kGyroReversed ? -1.0 : 1.0);
    }


    @Override
    public void periodic() {
        SmartDashboard.putNumber("Robot Roll", navx.getRoll());
        SmartDashboard.putNumber("Robot heading", navx.getYaw());
        SmartDashboard.putNumber("Left Encoder", leftEncoder.getDistance());
        SmartDashboard.putNumber("Right Encoder", rightEncoder.getDistance());
    }

    public void antiTipArcadeDrive(double xAxisRate, double zAxisRate) {
        drive.setSafetyEnabled(true);

        // double xAxisRate = stick.getX();
        // double yAxisRate = stick.getY();
        double rollAngleDegrees    = navx.getRoll();
        // double rollAngleDegrees     = navx.getRoll();
        final double kOffBalanceAngleThresholdDegrees = 5;
        final double kOonBalanceAngleThresholdDegrees  = 2;
        boolean fixBalance = false;
        
        if ( !fixBalance && 
                (Math.abs(rollAngleDegrees) >= 
                Math.abs(kOffBalanceAngleThresholdDegrees))) {
            fixBalance = true;
        }
        else if ( fixBalance && 
                    (Math.abs(rollAngleDegrees) <= 
                    Math.abs(kOonBalanceAngleThresholdDegrees))) {
            fixBalance = false;
        }
        
        // Control drive system automatically, 
        // driving in reverse direction of pitch/roll angle,
        // with a magnitude based upon the angle
        
        if (fixBalance) {
            double pitchAngleRadians = rollAngleDegrees * (Math.PI / 180.0);
            xAxisRate = Math.sin(pitchAngleRadians) * -1;
        }
        drive.arcadeDrive(xAxisRate, zAxisRate);
    }

    
    public void turnCommand (int degrees)
    {
        resetHeading();
        double heading = getHeading();
        double degreeGoals = degrees + heading;

        double error = degreeGoals - navx.getAngle();
        
        drive.tankDrive(kP*error, kP*error);
    }
    
    public void setMaxOutput(double speed) {
        drive.setMaxOutput(speed);
    }


    public void encoderDrive(double leftInches, double rightInches, double leftSpeed, double rightSpeed) {
        double leftTarget = leftEncoder.getDistance() + leftInches;
        double rightTarget = rightEncoder.getDistance() + rightInches;

        while (leftEncoder.getDistance() < leftTarget || rightEncoder.getDistance() < rightTarget) {
            if (leftEncoder.getDistance() < leftTarget) {
                leftFrontMotor.set(leftSpeed);
            }
            if (rightEncoder.getDistance() < rightTarget) {
                rightFrontMotor.set(rightSpeed);
            } 
        }
        tankDrive(0, 0);
    }

    public void encoderDrive(double leftInches, double rightInches) {
        encoderDrive(leftInches, rightInches, 0.25, 0.25);
    }
    
}