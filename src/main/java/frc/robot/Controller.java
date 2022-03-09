package frc.robot;

import java.util.ArrayList;
import java.util.HashMap;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Controller {
    private XboxController[] xboxControllers;

    // private XboxController selected;
    // private JoystickButton joystickButtons;
    private ArrayList<JoystickButton> joystickButtons = new ArrayList<JoystickButton>();

    // private int ports[];
    private HashMap<String, Integer> actionToButtonIndex;

    public Controller (ControllerBuilder builder) {
        this.xboxControllers = builder.xboxControllers;
        this.joystickButtons = builder.joystickButtons;
        this.actionToButtonIndex = builder.actionToButtonIndex;
    }

    



    

    public JoystickButton get(String action) {
        return joystickButtons.get(actionToButtonIndex.get(action));
    }

    public void resetControls() {

    }
    
    

    public static class ControllerBuilder {
        private XboxController[] xboxControllers;
        private ArrayList<JoystickButton> joystickButtons = new ArrayList<JoystickButton>();

        // private int ports[];
        private HashMap<String, Integer> actionToButtonIndex;
        int selected = 0;

        ControllerBuilder(int driverPort, int operatorPort) {
            // this.ports = ports;
                xboxControllers[0] = new XboxController(driverPort);
                xboxControllers[1] = new XboxController(operatorPort);
        }

        public ControllerBuilder add(String action, int buttonNumber) {
            actionToButtonIndex.put(action, joystickButtons.size());
            joystickButtons.add(new JoystickButton(xboxControllers[selected], buttonNumber));
            return this;
        }

        public ControllerBuilder driver() {
            selected = 0;
            return this;
        }

        public ControllerBuilder operator() {
            selected = 1;
            return this;
        }

        public Controller build() {
			Controller user = new Controller(this);
			return user;
		}
    }
}
