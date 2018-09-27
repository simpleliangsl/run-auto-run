package org.simple.autorun.command;

import org.simple.autorun.common.LogWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by sliang on 10/1/17.
 */
public class BasicCommand {

    public static Object newInstance(String className) {
        try {
            return Class.forName(className).newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    public static Object assign(Object obj) {
        return obj;
    }

    public static String ask(String prompt) {
        if (prompt != null) System.out.println(prompt);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static String askPassword(String prompt) {
        return new String(System.console().readPassword(prompt));
    }

    public static void printLine(Object obj) {
        LogWrapper.current().info(obj != null ? obj.toString() : null);
    }


    public static Map map() {
        return new HashMap();
    }

    public static Object ifElse(boolean condition, Object ifValue, Object elseValue) {
        return condition ? ifValue : elseValue;
    }

    public static boolean sleep(double seconds) {
        long milliseconds = (long)(seconds * 1000);
        try {Thread.sleep(milliseconds); return true;} catch (Exception e) {/* Swallow the exception */ return false;}
    }

    public static double randomNumber(double origin, double bound) {
        return ThreadLocalRandom.current().nextDouble(origin, bound);
    }

    public static int randomInteger(int origin, int bound) {
        return ThreadLocalRandom.current().nextInt(origin, bound);
    }

}