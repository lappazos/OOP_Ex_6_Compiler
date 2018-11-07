package oop.ex6.main.Ifwhile;

import oop.ex6.main.Scope;
import oop.ex6.main.ScopeParser;
import oop.ex6.main.compilationexceptions.CompilationException;

import java.util.List;
import java.util.regex.Matcher;

/**
 * Singleton class used to parse the first line of if/while blocks
 *
 * @author lioraryepaz, shahar.mazia
 */

public class ConditionSignatureParser implements ScopeParser {

    private static final int CONDITION_VARIABLE_INPUT = 2;
    //instance of singleton
    private static ConditionSignatureParser instance = new ConditionSignatureParser();

    /**
     * private singleton constructor
     */
    private ConditionSignatureParser() {
    }

    /**
     * singleton getInstance
     *
     * @return instance of this singleton class
     */
    public static ConditionSignatureParser getInstance() {
        return instance;
    }

    /**
     * method that parse opening line of if/while blocks
     *
     * @param scopeLines                list of scope lines
     * @param conditionSignatureMatcher matcher for first signature line
     * @param scope                     parent scope of this block
     * @throws CompilationException compilation exception
     */
    public void parseScopeSignature(List<String> scopeLines, Matcher conditionSignatureMatcher, Scope scope)
            throws CompilationException {
        BooleanTester booleanTest = new BooleanTester();
        String[] conditionVariablesInput = conditionSignatureMatcher.group(CONDITION_VARIABLE_INPUT).
                split("\\s*(\\|\\||&&)\\s*");
        IfWhile newScope = new IfWhile(scope, scopeLines);
        for (String variableInput : conditionVariablesInput) {
            booleanTest.variableAssigning(variableInput.trim(), scope);
        }
        newScope.scopeAnalyzer();
    }

}
