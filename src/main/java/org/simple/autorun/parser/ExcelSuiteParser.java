package org.simple.autorun.parser;


import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.simple.autorun.common.Config;
import org.simple.autorun.model.Case;
import org.simple.autorun.model.Command;
import org.simple.autorun.model.Feature;
import org.simple.autorun.model.Suite;
import org.simple.autorun.common.LogWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sliang on 9/28/17.
 */
public class ExcelSuiteParser implements SuiteParser {

    private DataFormatter dataFormatter = new DataFormatter();

    @Override
    public Suite parse(String suiteDir) {

        Suite suite = null;

        try {
            String suiteName = new File(suiteDir).getName();
            suite = new Suite(suiteName);

            String suiteRunPath = suiteDir + "/" + Config.getProperty("suiteRunFile") + ".run.xlsx";
            Workbook suiteRun = WorkbookFactory.create(new File(suiteRunPath));
            Sheet sheetRun = suiteRun.getSheet("run");

            // for each feature
            for(int i=1; i<=sheetRun.getLastRowNum(); i++) {
                Row featureRow = sheetRun.getRow(i);
                String featureName = dataFormatter.formatCellValue(featureRow.getCell(0));
                boolean featureEnabled = featureRow.getCell(1).getBooleanCellValue();

                if (featureEnabled) {
                    Feature feature = new Feature(suite, featureName);
                    suite.FEATURES.add(feature);

                    String featureRunPath = suiteDir + "/" + featureName + ".run.xlsx";
                    Workbook featureRun = WorkbookFactory.create(new File(featureRunPath));
                    Sheet sheetFeature = suiteRun.getSheet(featureName);

                    // for each case
                    for(int j=1; j<=sheetFeature.getLastRowNum(); j++) {
                        Row caseRow = sheetFeature.getRow(j);
                        String groupName = dataFormatter.formatCellValue(caseRow.getCell(0));
                        String caseName = dataFormatter.formatCellValue(caseRow.getCell(1));
                        boolean caseEnabled = caseRow.getCell(2).getBooleanCellValue();

                        if (Config.isGroupEnabled(groupName) && caseEnabled) {
                            Case caze = new Case(feature, caseName);
                            feature.CASES.add(caze);

                            Sheet sheetCase = featureRun.getSheet(caseName);

                            // for each command
                            for (int k=1; k<=sheetCase.getLastRowNum(); k++) {
                                Row commandRow = sheetCase.getRow(k);
                                String result = dataFormatter.formatCellValue(commandRow.getCell(0));
                                String command = dataFormatter.formatCellValue(commandRow.getCell(1));
                                String[] arguments = getArguments(commandRow);

                                caze.COMMANDS.add(new Command(caze, command, arguments, result));
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            LogWrapper.current().fatal(e);
        }
        return suite;
    }

    private String[] getArguments(Row commandRow) {
        final int ARG_START = 2;
        final int ARG_END = commandRow.getLastCellNum();
        List<String> arguments = new ArrayList<>();

        for (int i=ARG_START; i<ARG_END; i++) {
            arguments.add(dataFormatter.formatCellValue(commandRow.getCell(i)));
        }

        return arguments.toArray(new String[0]);
    }
}
