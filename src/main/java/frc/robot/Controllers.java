package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.XboxController;

public class Controllers {
    private final ArrayList<XboxController> xboxControllers = new ArrayList<XboxController>();
    
    /**
     * Creates Controllers which can be retrieved with {@link Controllers#get()} or
     * commands can be added to with  {@link Controllers#addCommands()}
     *
     * @param numberOfControllers the number of Controllers
     */
    // public Controllers(int numberOfControllers) {
    //     for (int i = 0; i < numberOfControllers; i++) {
    //          xboxControllers.add(new XboxController(i));
    //     }
    // }
    public Controllers(XboxController... xboxControllers) {
        for (XboxController xboxController: xboxControllers) {
            this.xboxControllers.add(xboxController);
        }
    }

    /**
     * Allows adding commands with the methods from {@link SuperSpecialTrigger} that can be chained with
     * {{@link SpecialTrigger#add()}
     *
     * @param port the port number to add commands for
     * @return Returns {@link SpecialTrigger} so it can be chained
     */

    public SuperSpecialTrigger addCommandsToController(int port) {
        
        return new SuperSpecialTrigger(this, port, () -> xboxControllers.get(port).isConnected());
    }

    /**
     * Retrieves {@link XboxController} of port number
     * 
     * @return Returns {@link XboxController}
     */
    public XboxController get(int port) {
        return xboxControllers.get(port);
    }
}