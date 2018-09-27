package org.simple.autorun.common;

import org.simple.autorun.parser.SuiteParser;

/**
 * Created by sliang on 10/3/17.
 */
public class ClassFactory {

    public static SuiteParser newSuiteParser() {
        return ClassFactory.create("suiteParser");
    }

    public static <T> T create(String key) {
        String classKey = Config.getProperty(key);
        String className = Config.getProperty(classKey);

        if (className == null) return null;

        try {
            return (T) Class.forName(Config.getProperty(classKey)).newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
