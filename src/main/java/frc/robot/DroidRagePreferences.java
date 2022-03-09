package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Preferences;

public class DroidRagePreferences {

  double ShooterkP = Preferences.getDouble("shooterkP", 0);
  double ShootrkI = Preferences.getDouble("shooterkI", 0);
  double shooterkD = Preferences.getDouble("shooterkD", 2.8);
  double shooterkF = Preferences.getDouble("Shooter kF", 0);

    // public static double getNumber(String key, double value) {
    //     if (!checkForKey(key)){
    //         Preferences.setDouble(key, value);
    //     }
        
    //     return Preferences.getDouble(key, value);
    //   }
    
    // private static boolean checkForKey(String key){
    //     if (Preferences.containsKey(key) && !reset)
    //       return true;
    //     else {
    //       return false;
    //     }
    //     Preferences.getDouble(key, backup)
    //   }
}
