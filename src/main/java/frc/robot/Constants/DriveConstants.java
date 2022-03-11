package frc.robot.Constants;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public final class DriveConstants {

    public static int // drivetrain
        kLeftFrontID = 3,
        kLeftRearID = 2,
        kRightFrontID = 5,
        kRightRearID = 4;

    public static int[]   // A  B
        kLeftEncoderPorts  = {9, 8}, // Through Bore encoders (they take up 2 ports for relative mode, and if in absolute, they take up only 1)
        kRightEncoderPorts = {7, 6};       // https://docs.revrobotics.com/through-bore-encoder/application-examples#5mm-hex 
    
    public static boolean 
    kLeftFrontReversed = false,
    kLeftRearReversed = false,
    kLeftEncoderReversed = false,

    kRightFrontReversed = false,
    kRightRearReversed = false,
    kRightEncoderReversed = false;

    public static boolean kGyroReversed = true;

    public static IdleMode 
    kLeftFrontIdleMode = IdleMode.kCoast,
    kLeftRearIdleMode = IdleMode.kCoast,
    kRightFrontIdleMode = IdleMode.kCoast,
    kRightRearIdleMode = IdleMode.kCoast;

    public static double 
    kMaxAngularSpeed = 2 * Math.PI, // one rotation per second
    kTrackWidth = 0.55, // meters
    kWheelRadius = 0.0762, // meters
    kThroughBoreEncoderResolution = 8192, // PPR
    positionChangePerRotation = 8.73, // Motor rotation per shaft rotation
    kMaxVelocity = 3, // feet per second
    kMaxAcceleration = 1; // Max Accel fet per second squared

    public static double 
    kStatic = 0.268, // Constant feedforward term for the robot drive.
    kV = 4.1711, // Velocity-proportional feedforward term for the robot drive
    kA = 0.5534; //Acceleration-proportional feedforward term for the robot (.348) (.44 protobot)

    
    public static double 
    kRamseteB = 2,// Tuning parameter (b > 0) for which larger values make convergence more aggressive like a proportional term    
    kRamseteZeta = 0.7; // Tuning parameter (0 &lt; zeta &lt; 1) for which larger values provide more damping in response

    public static double 
    kHeadingP = 1,
    kHeadingI = 0,
    kHeadingD = 0;

    public static double 
    kAlignP = 0.031,
    kAlignD = 0.0003;

    public static double kDriveP = 5.76, // 3 stable
    kDriveI = 0,
    kDriveD = 0;

    public static double kVoltageCompensation = 12.0;

    public static int kSmartLimit = 60;

    public static double 
    slowModeSpeed = 0.6,
    normalModeSpeed = 0.8,
    turboModeSpeed = 1;

    public static DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(DriveConstants.kTrackWidth);
}
