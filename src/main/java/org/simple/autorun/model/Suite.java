package org.simple.autorun.model;

import org.simple.autorun.common.LogWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sliang on 9/28/17.
 */
public class Suite {
    public final String NAME;
    public final List<Feature> FEATURES;


    public Suite(String suitName) {
        this(suitName, new ArrayList<>());
    }

    public Suite(String suitName, List<Feature> features) {
        this.NAME = suitName;
        this.FEATURES = features;
    }

    public void run() {
        LogWrapper.current().info("SUITE: "+this.NAME);
        for (Feature feature : FEATURES) {
            feature.run();
        }
    }
}
