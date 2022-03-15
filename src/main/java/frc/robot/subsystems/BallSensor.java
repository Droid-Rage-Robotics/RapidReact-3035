package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SubsystemConstants.ColorSensorConstants;

public class BallSensor extends SubsystemBase {
    private ColorSensorV3 ballSensor = new ColorSensorV3(ColorSensorConstants.colorSensorPort);

    public boolean isBallDetected() {
        return ballSensor.getProximity() < ColorSensorConstants.kColorSensorDistaceThreshold;
    }

    public boolean isRed() {
        return ballSensor.getRed() > ColorSensorConstants.kRedBallTarget;
    }

    public boolean isBlue() {
        return ballSensor.getBlue() > ColorSensorConstants.kBlueBallTarget;
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
