package frc.robot;

import edu.wpi.first.wpilibj.Preferences;

import static frc.robot.DroidRagePreferences.DoubleKey.*;

import java.util.HashMap;
import java.util.Map;

public class DroidRagePreferences {
    public static String key;

    public enum DoubleKey {
        SHOOTER_P,
        SHOOTER_I,
        SHOOTER_D,
    }

    private static HashMap<DoubleKey, Double> backupDoubleMap = new HashMap<DoubleKey, Double>(Map.of(
        SHOOTER_P, 10.0,
        SHOOTER_I, 10.0,
        SHOOTER_D, 10.0
    ));
    

    public static void addDoubleKeys() {
        for (DoubleKey key: DoubleKey.values()) {
            if (!Preferences.containsKey(key.toString())) 
                Preferences.setDouble(key.toString(), backupDoubleMap.get(key));
        }
    }

    public static void resetDoubleKeys() {
        for (DoubleKey key: DoubleKey.values()) {
                Preferences.setDouble(key.toString(), backupDoubleMap.get(key));
        }
    }

    public static void setDouble(DoubleKey key, double value) {
        Preferences.setDouble(key.toString(), value);
    }

    public static double getDouble(DoubleKey key) {
        return Preferences.getDouble(key.toString(), backupDoubleMap.get(key));
    }

    









    public enum StringKey {

    }

    private static HashMap<StringKey, String> backupStringMap = new HashMap<StringKey, String>(Map.of(

    ));

    public static void addStringKeys() {
        for (StringKey key: StringKey.values()) {
            if (!Preferences.containsKey(key.toString())) 
                Preferences.setString(key.toString(), backupStringMap.get(key));
        }
    }

    public static void resetStringKeys() {
        for (StringKey key: StringKey.values()) {
                Preferences.setString(key.toString(), backupStringMap.get(key));
        }
    }

    public static void setString(StringKey key, String value) {
        Preferences.setString(key.toString(), value);
    }

    public static String getString(StringKey key) {
        return Preferences.getString(key.toString(), backupStringMap.get(key));
    }








    public enum BooleanKey {

    }

    private static HashMap<BooleanKey, Boolean> backupBooleanMap = new HashMap<BooleanKey, Boolean>(Map.of(

    ));

    public static void addBooleanKeys() {
        for (BooleanKey key: BooleanKey.values()) {
            if (!Preferences.containsKey(key.toString())) 
                Preferences.setBoolean(key.toString(), backupBooleanMap.get(key));
        }
    }

    public static void resetBooleanKeys() {
        for (BooleanKey key: BooleanKey.values()) {
                Preferences.setBoolean(key.toString(), backupBooleanMap.get(key));
        }
    }

    public static void setBoolean(BooleanKey key, boolean value) {
        Preferences.setBoolean(key.toString(), value);
    }

    public static boolean getBoolean(BooleanKey key) {
        return Preferences.getBoolean(key.toString(), backupBooleanMap.get(key));
    }


    
}
