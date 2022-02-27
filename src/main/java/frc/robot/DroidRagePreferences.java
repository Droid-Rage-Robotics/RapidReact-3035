package frc.robot;

import edu.wpi.first.wpilibj.Preferences;

public class DroidRagePreferences {
    public static final String kArmPositionKey = "ArmPosition";
    public static final String kArmPKey = "ArmP";
    private static boolean reset = false;
  
    // The P gain for the PID controller that drives this arm.
    private static double kArmKp = 50.0;
  
    private static double armPositionDeg = 75.0;

    public void example() {
        // Set the Arm position setpoint and P constant to Preferences if the keys don't already exist
    if (!Preferences.containsKey(kArmPositionKey)) {
        Preferences.setDouble(kArmPositionKey, armPositionDeg);
      }
      if (!Preferences.containsKey(kArmPKey)) {
        Preferences.setDouble(kArmPKey, kArmKp);
      }

        Preferences.getDouble(kArmPKey, armPositionDeg); // the second number is for if there is not one already on the 
        // robo rio

        // this type of code shoud probably put in a  subsystem or command
        // constructor

        // you can use smart dashboard to edit these preferences
        // you an also use shuffleboard 
        // instructions are found on basic programming/setting robot preferences
        // in wpilib

        // very important

        // read reading stacktraces on wpilib 
        // if you get errors ever
    }

    public static double getNumber(String key, double backup) {
	    return addNumber(key, backup);
	  }

    public static double addNumber(String key, double value) {
      if (!checkForKey(key)){
          Preferences.setDouble(key, value);
      }
      
      return Preferences.getDouble(key, value);
      }
    
      private static boolean checkForKey(String key){
        if (Preferences.containsKey(key) && reset == false)
          return true;
        else{
          return false;
        }
      }
}
