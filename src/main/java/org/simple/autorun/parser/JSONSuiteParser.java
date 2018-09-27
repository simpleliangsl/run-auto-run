package org.simple.autorun.parser;

import com.google.gson.Gson;
import org.simple.autorun.common.Config;
import org.simple.autorun.common.LogWrapper;
import org.simple.autorun.model.Case;
import org.simple.autorun.model.Command;
import org.simple.autorun.model.Feature;
import org.simple.autorun.model.Suite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

/**
 * Created by sliang on 10/1/17.
 */
public class JSONSuiteParser implements SuiteParser {

    @Override
    public Suite parse(String suiteDir) {

        Suite suite = null;

        try {
            String suiteName = new File(suiteDir).getName();
            suite = new Suite(suiteName);

            Gson gson = new Gson();
            String suiteRunPath = suiteDir + "/" + Config.getProperty("suiteRunFile") + ".run.json";
            SuiteRun suiteRun = gson.fromJson(new FileReader(suiteRunPath), SuiteRun.class);

            // for each feature
            for (String featureName : suiteRun.run) {
                String featureDir = suiteDir + "/" + featureName;
                FeatureRun featureRun = suiteRun.features.get(featureName);
                Feature feature = new Feature(suite, featureName);
                suite.FEATURES.add(feature);

                // for each group
                for (String groupName : featureRun.run) {

                    // exclude a group
                    if (!Config.isGroupEnabled(groupName)) {
                        continue;
                    }

                    // for each case
                    for (String caseName : featureRun.groups.get(groupName)) {
                        String caseRunPath = featureDir + "/" + caseName + ".run.json";
                        CaseRun caseRun = gson.fromJson(new FileReader(caseRunPath), CaseRun.class);
                        Case caze = new Case(feature, caseName);
                        feature.CASES.add(caze);

                        // for each command
                        for (CommandRun cr : caseRun.run) {
                            caze.COMMANDS.add(new Command(caze, cr.command, cr.arguments, cr.result));
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            LogWrapper.current().fatal(e);
        }

        return suite;
    }

    private static class SuiteRun {
        public String[] run;
        public Map<String, FeatureRun> features;
    }

    private static class FeatureRun {
        public String[] run;
        public Map<String, String[]> groups;
    }

    private static class CaseRun {
        public CommandRun[] run;
    }

    private static class CommandRun {
        public String command;
        public String[] arguments;
        public String result;
    }
}
