// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class DriveConstants {
        public static final int kEncoderCPR = 1024;
        public static final double kWheelDiameterInches = 6;
        public static final boolean kLeftEncoderReversed = false;
        public static final boolean kRightEncoderReversed = true;

        public static final double kEncoderDistancePerRevolution =
            // Assumes the encoders are directly mounted on the wheel shafts
            (kWheelDiameterInches * Math.PI);   // / (double) kEncoderCPR;      // we might need to divide by the encoder CPR maybe?
    }

    public static final class DrivePorts {
        public static final int
            leftEncoder = 0,
            rightEncoder = 1;
        
        public static final int 
            leftFront = 0,
            rightFront = 1,
            leftRear = 2,
            rightRear = 4;
    }

    public static final class ClimberPorts {
        public static final int 
            right = 0,
            left = 1;
    }

    public static final class Intake {
        public static final class Piston {
            public static final int 
                intakeMotor = 0;
        }

        public static final class Motor {
            public static final int 
                right = 0,
                left = 1;
        }
    }

    public static final class Indexer {
        public static final int 
            front = 0,
            back = 1;
    }

    public static final class Shooter {
        public static final int 
            PrimaryDeviceID = 0,
            SecondaryDeviceID = 1;
        public static final String 
            primaryCanbus = "idk",
            secondaryCanbus = "idk";
        public static final double velocity = 204.8; // I believe 20480 is 1 revolution per minute
        public static final boolean secondaryInverted = false;
        public static final boolean primaryInverted = false;

        
        public static final int pigeonID = 0;

        public static final int driveMotor0 = 1;
        public static final int angleMotor0 = 2;
        public static final int driveMotor1 = 11;
        public static final int angleMotor1 = 12;
        public static final int driveMotor2 = 21;
        public static final int angleMotor2 = 22;
        public static final int driveMotor3 = 31;
        public static final int angleMotor3 = 32;

        public static final int kIntakeMotor = 40;
        public static final int kIndexerMotor = 41;
        public static final int kTowerMotorFront = 42;
        public static final int kTowerMotorRear = 43;
        
        public static final int kLauncherMotorLeft = 50;
        public static final int kFollowerMotorRight = 51;

        public static final int kClimberMotor = 60;
    }
    
    
    // Unused (idk what it is)
    public static final class AutoConstants {
        public static final double kAutoDriveDistanceInches = 60;
        public static final double kAutoBackupDistanceInches = 20;
        public static final double kAutoDriveSpeed = 0.5;
    }
    
    
    public static final class OIConstants {
        public static final int kDriverControllerPort = 0;
    }
}
