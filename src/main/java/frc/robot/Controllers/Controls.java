package frc.robot.Controllers;

public class Controls {
    public enum XboxButton {
        A, B, X, Y,
        LB, RB, 
        BACK, START,
        LS, RS
    }

    public enum XboxTrigger {
        RT, LT
    }

    public enum XboxDPAD {
        DPAD_UP,  DPAD_RIGHT,  
        DPAD_DOWN,  DPAD_LEFT,
        DPAD_UNPRESSED
    }
    
    public static int buttonToButtonNumber(XboxButton xboxButton) {
        switch (xboxButton) {
            case A:      return 1;
            case B:      return 2;
            case X:      return 3;
            case Y:      return 4;
            case LB:     return 5;
            case RB:     return 6;
            case BACK:   return 7;
            case START:  return 8;
            case LS:     return 9;
            case RS:     return 10;
            default:     return 0;
        }
    }

    public static int triggerToTriggerNumber(XboxTrigger xboxTrigger) {
        switch (xboxTrigger) {
            case LT: return 2;
            case RT: return 3;
            default: return 2;
        }
    }

    public static int dpadToHatDegrees(XboxDPAD xboxDPAD) {
        switch (xboxDPAD) {
            case DPAD_UP: return 0;
            case DPAD_RIGHT: return 90;
            case DPAD_DOWN: return 180;
            case DPAD_LEFT: return 270;
            case DPAD_UNPRESSED: return -1;
            default: return -1;
            
        }
    }
}
