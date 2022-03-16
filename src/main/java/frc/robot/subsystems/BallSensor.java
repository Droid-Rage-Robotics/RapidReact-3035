package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.DRPreferences;
import edu.wpi.first.wpilibj.I2C;

import static frc.robot.DRPreferences.DoubleKey.*;

public class BallSensor extends SubsystemBase {
    private ColorSensorV3 ballSensor = new ColorSensorV3(I2C.Port.kOnboard);

    public boolean isBallDetected() {
        return ballSensor.getProximity() < DRPreferences.get(BALL_SENSOR_DISTANCE_THRESHOLD);
    }

    public boolean isRed() {
        return ballSensor.getRed() > DRPreferences.get(RED_BALL_COLOR);
    }

    public boolean isBlue() {
        return ballSensor.getBlue() > DRPreferences.get(BLUE_BALL_COLOR);
    }

    public BallColor getColor() {
        if (!isBallDetected()) return BallColor.NO_BALL;
        if (isRed()) return BallColor.RED;
        if (isBlue()) return BallColor.BLUE;
        return BallColor.UNKNOWN;
    }
    

    public enum BallColor {
        RED, BLUE, NO_BALL, UNKNOWN
    }

    
}
