
package frc.robot.subsystems;

import static frc.robot.Constants.DriveConstants.kGyroReversed;
import static frc.robot.Constants.DriveConstants.kThroughBoreEncoderResolution;
import static frc.robot.Constants.DriveConstants.kWheelRadiusInches;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.math.controller.PIDController;
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
    private final Encoder leftEncoder = new Encoder (        //Left Encoder
        9,
        8,
        false, // Reversed
        CounterBase.EncodingType.k4X
    );

    private final Encoder rightEncoder = new Encoder(        //Right Encoder
        7,
        6,
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

        rightMotors.setInverted(true);
        leftMotors.setInverted(false);

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
    public void arcadeDrive(double forward, double rotation) {
        drive.arcadeDrive(forward, rotation);
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        drive.tankDrive(leftSpeed, rightSpeed);
    }

    public void curvatureDrive(double forward, double rotation) {
        drive.curvatureDrive(forward, rotation, true);
    }

    public void slowDrive() {
        drive.setMaxOutput(0.3);
    }

    public void normalDrive() {
        drive.setMaxOutput(1);
    }

    public void turboDrive() {
        drive.setMaxOutput(0.65);
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
    













    final double     COUNTS_PER_MOTOR_REV    = 8192 ;    // eg: TETRIX Motor Encoder
    final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    final double     WHEEL_DIAMETER_INCHES   = 6.0 ;     // For figuring circumference
    final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * 3.1415);

    // These constants define the desired driving/control characteristics
    // The can/should be tweaked to suite the specific robot drive train.
    final double     DRIVE_SPEED             = 0.7;     // Nominal speed for better accuracy.
    final double     TURN_SPEED              = 0.5;     // Nominal half speed for better accuracy.
    
    final double     HEADING_THRESHOLD       = 1 ;      // As tight as we can make it with an integer gyro
    final double     P_TURN_COEFF            = 0.1;     // Larger is more responsive, but also less stable
    final double     P_DRIVE_COEFF           = 0.15;     // Larger is more responsive, but also less stable

    PIDController leftDrivePID = new PIDController(P_DRIVE_COEFF, 0, 0);
    PIDController rightDrivePID = new PIDController(P_DRIVE_COEFF, 0, 0);

    PIDController turnPID = new PIDController(P_TURN_COEFF, 0, 0);
    SparkMaxPIDController newDrive;

    
    public void gyroDrive(double distance) {
        int     newLeftTarget;
        int     newRightTarget;
        int     moveCounts;
        double  max;
        double  error;
        double  steer;
        double  leftSpeed;
        double  rightSpeed;

        moveCounts = (int)(distance * COUNTS_PER_INCH);

        newLeftTarget = leftEncoder.get() + moveCounts;
        newRightTarget = rightEncoder.get() + moveCounts;
        
        while (leftDrivePID.getPositionError() < 1 || leftDrivePID.getPositionError() > 0) {
            leftDrivePID.setTolerance(5, 10);
            rightDrivePID.setTolerance(5, 10);
            leftDrivePID.setIntegratorRange(-0.3, 0.3);
            rightDrivePID.setIntegratorRange(-0.3, 0.3); // idk what thisi des
            leftSpeed = leftDrivePID.calculate(leftEncoder.get(), newLeftTarget);
            rightSpeed = rightDrivePID.calculate(rightEncoder.get(), newRightTarget);
            leftMotors.set(leftSpeed);
            rightMotors.set(rightSpeed);
        }
        leftMotors.set(0);
        rightMotors.set(0);
    }
}