package org.simple.autorun.command;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by sliang on 10/25/17.
 */
public class OperationCommand {

    /* NUMBER OPERATION BELOW*/

    public static double addNumber(double number1, double number2) {
        return number1 + number2;
    }

    public static double subtractNumber(double number1, double number2) {
        return number1 - number2;
    }

    public static double multiplyNumber(double number1, double number2) {
        return number1 * number2;
    }

    public static double divideNumber(double number1, double number2) {
        return number1 / number2;
    }

    public static double mod(double d1, double d2) {
        return d1 % d2;
    }

    /* BOOLEAN OPERATION BELOW */

    public static boolean and(boolean b1, boolean b2) {
        return b1 && b2;
    }

    public static boolean or(boolean b1, boolean b2) {
        return b1 || b2;
    }

    public static boolean not(boolean b) {
        return !b;
    }

    /* COMPARING OPERATION BELOW */

    public static boolean isLessThan(Comparable c1, Comparable c2) {
        return c1.compareTo(c2) < 0;
    }

    public static boolean isGreaterThan(Comparable c1, Comparable c2) {
        return c1.compareTo(c2) > 0;
    }

    public static boolean isLessOrEqual(Comparable c1, Comparable c2) {
        return c1.compareTo(c2) <= 0;
    }

    public static boolean isGreaterOrEqual(Comparable c1, Comparable c2) {
        return c1.compareTo(c2) >= 0;
    }

}
