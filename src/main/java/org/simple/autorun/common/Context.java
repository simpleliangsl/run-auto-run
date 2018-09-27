package org.simple.autorun.common;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.text.StrSubstitutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by sliang on 9/26/17.
 */
public class Context {

    private Map<String, Object> contextMap;
    private static Properties commandProperties;

    private static Properties getCommandProperties() {
        if (commandProperties == null) {
            String[] fileNames = {"auto-run-commands.properties", Config.getProperty("customCommands")};
            commandProperties = Config.loadProperties(fileNames);
        }

        return commandProperties;
    }

    public Context() {
        contextMap = new HashMap<>();
        contextMap.put("$null", null);
        contextMap.put("$true", true);
        contextMap.put("$false", false);
        contextMap.put("$empty", "");

        injectCommands();
    }

    private void injectCommands() {
        for(Object key : getCommandProperties().keySet()) {
            contextMap.put((String) key, commandProperties.getProperty((String) key));
        }
    }

    public void put(String key, Object value) {
        contextMap.put(key, value);
    }

    public Object get(String key) {
        return contextMap.get(key);
    }

    public boolean has(String key) {
        return contextMap.containsKey(key);
    }

    public String replace(String template) {
        return StrSubstitutor.replace(template, contextMap, "{", "}");
    }

    public Object[] parse(String[] arguments) {
        if (arguments == null) return null;

        Object[] result = new Object[arguments.length];

        for(int i=0; i<arguments.length; i++) {
            result[i] = parse(arguments[i]);
        }

        return result;
    }

    public Object parse(String argument) {

        if (argument == null) return null; // null

        Object result;

        if (this.has(argument)) { // already in Context
            result = this.get(argument);
        } else if (NumberUtils.isParsable(argument)) { // a number (double or integer)
            if (argument.contains(".")) { // a double
                result = Double.parseDouble(argument);
            } else { // a integer
                result = Integer.parseInt(argument);
            }
        } else if (argument.startsWith("[") && argument.endsWith("]")) { // a list
            result = toList(argument);
        } else { // replace the placeholder with value from Context
            result = this.replace(argument);
        }

        return result;
    }

    private List toList(String expression) {
        List result = new ArrayList();

        expression = expression.substring(1,expression.length()-1);

        for (String value : expression.split(",")) {
            result.add(parse(value.trim()));
        }

        return result;
    }
}
