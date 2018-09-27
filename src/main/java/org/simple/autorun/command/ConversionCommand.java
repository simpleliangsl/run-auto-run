package org.simple.autorun.command;

/**
 * Created by sliang on 10/25/17.
 */
public class ConversionCommand {

    public static int numberToInt(double d) {
        return (int)d;
    }

    public static int toInt(String value) {
        return Integer.parseInt(value);
    }

    public static double toNumber(String value) {
        return Double.parseDouble(value);
    }

    public static boolean toBool(String value) {
        return Boolean.parseBoolean(value);
    }
}
