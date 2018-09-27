package org.simple.autorun.model;

import org.apache.commons.lang3.StringUtils;
import org.simple.autorun.common.Context;
import org.simple.autorun.common.ContextInjection;
import org.simple.autorun.common.LogWrapper;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sliang on 10/12/17.
 */
public class Command {
    public final Case CASE;
    public final String COMMAND;
    public final String[] ARGUMENTS;
    public final String RESULT;
    private final Context CONTEXT;

    public Command(Case caze, String command, String[] arguments, String result) {
        this.CASE = caze;
        this.COMMAND = command;
        this.ARGUMENTS = arguments;
        this.RESULT = result;
        this.CONTEXT = caze.CONTEXT;
    }

    public void run() {
        LogWrapper.current().info("COMMAND: {0}({1}) -> {2}", COMMAND, "'"+ StringUtils.join(ARGUMENTS, "', '")+"'", "'"+ RESULT +"'");
        Object returnValue = run(COMMAND, CONTEXT.parse(ARGUMENTS));
        if (RESULT != null && !RESULT.isEmpty()) {
            CONTEXT.put("$" + RESULT, returnValue);
        }
    }

    private Object run(String command, Object[] parameters) {

        // get method id from Context by command
        String methodId = (String) CONTEXT.get(command);
        if (methodId != null) {
            String[] classAndMethod = methodId.split("#");
            if (classAndMethod.length != 2) {
                LogWrapper.current().fatal("Method id "+methodId+" should be formatted with sign '#'.");
            }
        }

        String[] classAndMethod = methodId.split("#");
        if (classAndMethod.length != 2) {
            LogWrapper.current().fatal("Method id "+methodId+" is formatted with sign '#'.");
        }

        String className = classAndMethod[0];
        String methodName = classAndMethod[1];

        if (methodName == null) {
            LogWrapper.current().fatal("Command "+command+" is not defined.");
        }

        try {
            for (Method m : Class.forName(className).getMethods()) {
                if (m.getName().equals(methodName)) { // find the method by NAME

                    Object instance = null;
                    List paramList = new ArrayList(Arrays.asList(parameters));

                    if (paramList.size() > 0) {
                        // get instance from parameter list
                        instance = Modifier.isStatic(m.getModifiers()) ? null : paramList.remove(0);

                        if (m.isAnnotationPresent(ContextInjection.class)) { // inject Context into parameters
                            paramList.add(0, CONTEXT);
                        }
                    }

                    return m.invoke(instance, paramList.toArray()); // invoke method
                }
            }
        } catch (Exception e) {
            LogWrapper.current().fatal(e);
        }

        return null;
    }

    private Object run1(String command, Object[] parameters) {

        // get method id from Context by command
        String methodId = (String) CONTEXT.get(command);

        if (methodId != null) {

        } else {
            List paramList = new ArrayList(Arrays.asList(parameters));
            Object instance = paramList.remove(0);
            List<Class> paramTypes = Arrays.stream(parameters).map(p -> p.getClass()).collect(Collectors.toList());
            try {
                Method method = instance.getClass().getMethod(command, paramTypes.toArray(new Class[0]));
                method.invoke(instance, paramList.toArray());
            } catch (Exception e) {

            }
        }

        return null;
    }
}
