package org.simple.autorun;

import org.simple.autorun.command.BasicCommand;
import org.simple.autorun.common.ClassFactory;
import org.simple.autorun.common.Config;
import org.simple.autorun.model.Suite;
import org.simple.autorun.parser.SuiteParser;

/**
 * Created by sliang on 9/26/17.
 */
public class AppMain {

    public static void main(String[] args) {
        SuiteParser parser = ClassFactory.newSuiteParser();
        Suite suite = parser.parse(Config.getProperty("suiteDir"));
        suite.run();
    }
}
