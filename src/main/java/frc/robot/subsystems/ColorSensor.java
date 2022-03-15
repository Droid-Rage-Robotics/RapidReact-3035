package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;

import frc.robot.Constants.SubsystemConstants.ColorSensorConstants;

public class ColorSensor {
    private ColorSensorV3 colorSensor = new ColorSensorV3(ColorSensorConstants.colorSensorPort);
}
