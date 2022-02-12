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
                right = 0,
                left = 1;
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
            right = 0,
            left = 1;
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
