package org.simple.autorun.parser;

import org.simple.autorun.model.Suite;

/**
 * Created by sliang on 10/2/17.
 */
public interface SuiteParser {
    Suite parse (String suiteDir);
}
