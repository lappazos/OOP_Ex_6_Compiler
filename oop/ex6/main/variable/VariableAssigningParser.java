package oop.ex6.main.variable;

import oop.ex6.main.Scope;
import oop.ex6.main.compilationexceptions.*;

/**
 * parser class for variable assigning - singleton
 *
 * @author lioraryepaz, shahar.mazia
 */

public class VariableAssigningParser {

    private static final String EQUAL_SIGN_DELIMITER = "=";
    private static final int VARIABLE_PARTS = 2;
    /**
     * singleton instance
     */
    private static VariableAssigningParser instance = new VariableAssigningParser();

    /**
     * singleton private Constructor
     */
    private VariableAssigningParser() {
    }

    /**
     * getter for singleton instance
     *
     * @return the class single instance
     */
    public static VariableAssigningParser getInstance() {
        return instance;
    }

    /**
     * parser for line of variable assigning
     *
     * @param line  line of assining
     * @param scope scope of assigning
     * @throws CompilationException compilation exception
     */
    public void assignVariables(String line, Scope scope) throws CompilationException {
        line = line.trim();
        line = line.substring(0, line.length() - 1).trim();
        String[] lineElements = line.split(EQUAL_SIGN_DELIMITER, VARIABLE_PARTS);
        Variable variable = scope.findVariable(lineElements[0].trim());
        if (variable == null) {
            throw new VariableNotExistException(lineElements[0].trim());
        }
        if (variable.isFinal()) {
            throw new FinalException();
        }
        variable.variableAssigning(lineElements[1].trim(), scope);
    }

}
