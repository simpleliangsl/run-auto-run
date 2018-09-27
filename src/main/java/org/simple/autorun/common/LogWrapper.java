package org.simple.autorun.common;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Simple Liang on 9/26/17.
 */
public class LogWrapper {

    private Logger logger;

    private static Map<Long, LogWrapper> loggers = new HashMap<>();

    public static LogWrapper current() {
        Long threadId = Thread.currentThread().getId();
        if (loggers.get(threadId) == null) loggers.put(threadId, new LogWrapper(threadId.toString()));
        return loggers.get(threadId);
    }

    public LogWrapper (String loggerName) {
        // PropertyConfigurator.configure(ClassLoader.getSystemResource("log4j.properties"));
        logger = Logger.getLogger(loggerName);
    }

    public void debug(String message, Object ... arguments) {
        log(Level.DEBUG, message, arguments);
    }

    public void info(String message, Object ... arguments) {
        log(Level.INFO, message, arguments);
    }

    public void warn(String message, Object ... arguments) {
        log(Level.WARN, message, arguments);
    }

    public void error(String message, Object ... arguments) {
        log(Level.ERROR, message, arguments);
    }

    public void error(Exception e) {
        error(toString(e));
    }

    public void fatal(String message, Object ... arguments) {
        log(Level.FATAL, message, arguments);
        System.exit(1);

    }

    public void fatal(Exception e) {
        fatal(toString(e));
    }

    public void log(Level level, String message, Object ... arguments){
        message = arguments.length > 0 ? MessageFormat.format(message, arguments) : message;
        logger.log(level, message);
    }

    private String toString(Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
