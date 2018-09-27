package org.simple.autorun.model;

import org.simple.autorun.common.LogWrapper;
import org.simple.autorun.common.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sliang on 9/28/17.
 */
public class Case {

    public final Feature FEATURE;
    public final String NAME;
    public final List<Command> COMMANDS;

    public final Context CONTEXT;

    public Case(Feature feature, String caseName) {
        this(feature, caseName, new ArrayList<>());
    }

    public Case(Feature feature, String caseName, List<Command> commands) {
        this.FEATURE = feature;
        this.NAME = caseName;
        this.COMMANDS = commands;

        this.CONTEXT = new Context();
    }

    public void run() {
        LogWrapper.current().info("CASE: "+this.NAME);
        for(Command command : COMMANDS) {
            command.run();
        }
    }

}
