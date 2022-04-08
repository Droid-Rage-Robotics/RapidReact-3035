package frc.robot;

import edu.wpi.first.wpilibj.Preferences;

import static frc.robot.DRPreferences.DoubleKey.*;
// import static frc.robot.DRPreferences.StringKey.*;
// import static frc.robot.DRPreferences.BooleanKey.*;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class DRPreferences {
    public static String key;

    public enum DoubleKey {
        FRONT_SHOOTER_P,
        FRONT_SHOOTER_I,
        FRONT_SHOOTER_D,
        FRONT_SHOOTER_F,
        FRONT_SHOOTER_I_ZONE,

        FRONT_SHOOTER_FAR_HIGH,
        FRONT_SHOOTER_CLOSE_HIGH,
        FRONT_SHOOTER_LOW,
        FRONT_SHOOTER_AUTO_FAR_HIGH,
        FRONT_SHOOTER_AUTO_FAR_HIGH_HANGER,
        FRONT_SHOOTER_AUTO_CLOSE_HIGH,
        FRONT_SHOOTER_AUTO_LOW,

        BALL_SENSOR_DISTANCE_THRESHOLD,
        RED_BALL_COLOR,
        BLUE_BALL_COLOR,

        CLIMBER_EXTEND_POWER,
        CLIMBER_RETRACT_POWER,

        INDEXER_INTAKE_SPEED,
        INDEXER_FRONT_INTAKE_SPEED,
        INDEXER_OUTTAKE_SPEED,
        
        INTAKE_INTAKE_SPEED,
        INTAKE_OUTTAKE_SPEED,

        P_DRIVE_COEFF,
        D_DRIVE_COEFF,

        P_TURN_COEFF,
        D_TURN_COEFF

    }

    private static HashMap<DoubleKey, Double> backupDoubleMap = new HashMap<DoubleKey, Double>(Map.ofEntries(
        entry(FRONT_SHOOTER_P, 5),
        entry(FRONT_SHOOTER_I, 0),
        entry(FRONT_SHOOTER_D, 0),
        entry(FRONT_SHOOTER_F, 0.1),
        entry(FRONT_SHOOTER_I_ZONE, 150),

        entry(FRONT_SHOOTER_FAR_HIGH, 2900),
        entry(FRONT_SHOOTER_CLOSE_HIGH, 2200),
        entry(FRONT_SHOOTER_LOW, 1000),

        entry(FRONT_SHOOTER_AUTO_FAR_HIGH, 2450),
        entry(FRONT_SHOOTER_AUTO_FAR_HIGH_HANGER, 2450),

        entry(FRONT_SHOOTER_AUTO_CLOSE_HIGH, 2300),

        entry(FRONT_SHOOTER_AUTO_LOW, 2000),

        entry(BALL_SENSOR_DISTANCE_THRESHOLD, 10),
        entry(RED_BALL_COLOR, 10),
        entry(BLUE_BALL_COLOR, 10),

        entry(CLIMBER_EXTEND_POWER, 1),
        entry(CLIMBER_RETRACT_POWER, -0.8),

        entry(INDEXER_INTAKE_SPEED, 0.3),
        entry(INDEXER_FRONT_INTAKE_SPEED, 0.5),
        entry(INDEXER_OUTTAKE_SPEED, -0.25),

        entry(INTAKE_INTAKE_SPEED,  1),
        entry(INTAKE_OUTTAKE_SPEED, -1),

        entry(P_DRIVE_COEFF, 0.001),
        entry(D_DRIVE_COEFF, 0),

        entry(P_TURN_COEFF, 5),
        entry(D_TURN_COEFF, 0)
        
        

    ));

    private static Map.Entry<DoubleKey, Double> entry(DoubleKey key, double number) {
        return new AbstractMap.SimpleEntry<DoubleKey, Double>(key, number);
    }
    
 
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

    public static double get(DoubleKey key) {
        return Preferences.getDouble(key.toString(), backupDoubleMap.get(key));
    }

    












    







    public enum StringKey {

    }

    private static HashMap<StringKey, String> backupStringMap = new HashMap<StringKey, String>(Map.of(

    ));

    // private static Map.Entry<StringKey, Double> entry(StringKey key, double number) {
    //     return new AbstractMap.SimpleEntry<StringKey, Double>(key, number);
    // }

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

    public static String get(StringKey key) {
        return Preferences.getString(key.toString(), backupStringMap.get(key));
    }

    

















    public enum BooleanKey {

    }

    private static HashMap<BooleanKey, Boolean> backupBooleanMap = new HashMap<BooleanKey, Boolean>(Map.of(

    ));

    // private static Map.Entry<BooleanKey, Double> entry(BooleanKey key, double number) {
    //     return new AbstractMap.SimpleEntry<BooleanKey, Double>(key, number);
    // }

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

    public static boolean get(BooleanKey key) {
        return Preferences.getBoolean(key.toString(), backupBooleanMap.get(key));
    }
}