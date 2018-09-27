package org.simple.autorun.model;

import org.simple.autorun.common.LogWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sliang on 9/28/17.
 */
public class Feature {

    public final Suite SUITE;
    public final String NAME;
    public final List<Case> CASES;

    public Feature(Suite suite, String featureName) {
        this(suite, featureName, new ArrayList<>());
    }

    public Feature(Suite suite, String featureName, List<Case> cases) {
        this.SUITE = suite;
        this.NAME = featureName;
        this.CASES = cases;
    }

    public void run() {
        LogWrapper.current().info("FEATURE: "+this.NAME);
        for (Case caze : CASES) {
            caze.run();
        }
    }
}
