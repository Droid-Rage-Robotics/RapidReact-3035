package frc.robot;

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
        UP,  RIGHT,  
        DOWN,  LEFT,
        UNPRESSED
    }
    
    public static int buttonToButtonNumber(XboxButton xboxButton) {
        switch (xboxButton) {
            case A:      return 0;
            case B:      return 1;
            case X:      return 2;
            case Y:      return 3;
            case LB:     return 4;
            case RB:     return 5;
            case BACK:   return 6;
            case START:  return 7;
            case LS:     return 8;
            case RS:     return 9;
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
            case UP: return 0;
            case RIGHT: return 90;
            case DOWN: return 180;
            case LEFT: return 270;
            case UNPRESSED: return -1;
            default: return -1;
            
        }
    }
}
