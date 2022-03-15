package frc.robot.Controllers;

import java.util.HashMap;
import java.util.Map;

import static frc.robot.Controllers.Controls.XboxButton.*;
import static frc.robot.Controllers.Controls.XboxTrigger.*;
import static frc.robot.Controllers.Controls.XboxDPAD.*;

public class Controls {
    public enum XboxButton {
        A, B, X, Y,
        LB, RB, 
        BACK, START,
        LS, RS
    }

    private static HashMap<XboxButton, Integer> buttonToButtonNumberMap = new HashMap<XboxButton, Integer>(Map.of(
        A, 1,
        B, 2, 
        X, 3, 
        Y, 4,
        LB, 5, 
        RB, 6,
        BACK, 7,
        START, 8,
        LS, 9,
        RS, 10
    ));




    public enum XboxTrigger {
        RT, LT
    }

    private static HashMap<XboxTrigger, Integer> triggerToTriggerNumberMap = new HashMap<XboxTrigger, Integer>(Map.of(
        LT, 2,
        RT, 3
    ));





    public enum XboxDPAD {
        DPAD_UP,  DPAD_RIGHT,  
        DPAD_DOWN,  DPAD_LEFT,
        DPAD_UNPRESSED
    }

    private static HashMap<XboxDPAD, Integer> dpadToHatDegreesMap = new HashMap<XboxDPAD, Integer>(Map.of(
        DPAD_UP, 0,
        DPAD_RIGHT, 90,
        DPAD_DOWN, 180,
        DPAD_LEFT, 270,
        DPAD_UNPRESSED, -1
    ));
    
    


    public static int buttonToButtonNumber(XboxButton xboxButton) {
        return buttonToButtonNumberMap.get(xboxButton);
    }

    public static int triggerToTriggerNumber(XboxTrigger xboxTrigger) {
        return triggerToTriggerNumberMap.get(xboxTrigger);
    }

    public static int dpadToHatDegrees(XboxDPAD xboxDPAD) {
        return dpadToHatDegreesMap.get(xboxDPAD);
    }
}
