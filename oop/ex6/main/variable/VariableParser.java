package oop.ex6.main.variable;

import oop.ex6.main.Scope;
import oop.ex6.main.compilationexceptions.CompilationException;

import java.util.regex.Matcher;

/**
 * parser for variable creation - singleton
 *
 * @author lioraryepaz, shahar.mazia
 */

public class VariableParser {

    private static final int IS_FINAL = 2;
    private static final int TYPE_GROUP = 4;
    private static final int VARIABLES_INPUT = 5;
    private static final String DELIMITER = ",";
    private static final String INT_TYPE = "int";
    private static final String STRING_TYPE = "String";
    private static final String BOOLEAN_TYPE = "boolean";
    private static final String CHAR_TYPE = "char";
    private static final String DOUBLE_TYPE = "double";
    /**
     * the only instance of the class
     */
    private static VariableParser instance = new VariableParser();

    /**
     * private singleton Constructor
     */
    private VariableParser() {
    }

    /**
     * getter of singleton instance
     *
     * @return the only class instance
     */
    public static VariableParser getInstance() {
        return instance;
    }

    /**
     * parse line of variable declaration
     *
     * @param scope   scope of declaration
     * @param matcher signature line matcher
     * @param isExist are the variables exist or not, according to where and how have they been declared
     * @throws CompilationException compilation exception
     */
    public void parseVariableDeclarationLine(Scope scope, Matcher matcher, boolean isExist)
            throws CompilationException {
        boolean isFinal = false;
        if (matcher.group(IS_FINAL) != null) {
            isFinal = true;
        }
        String type = matcher.group(TYPE_GROUP);
        String[] variablesInput = matcher.group(VARIABLES_INPUT).split(DELIMITER);
        for (String variable : variablesInput) {
            variablesFactory(variable, type, scope, isExist, isFinal);
        }
    }

    /**
     * factory of variables
     *
     * @param variableInput input of variable
     * @param type          variable type
     * @param scope         variable scope
     * @param isExist       is variable exist
     * @param isFinal       is variable final
     * @throws CompilationException compilation exception
     */
    private void variablesFactory(String variableInput, String type, Scope scope, boolean isExist, boolean isFinal)
            throws CompilationException {
        switch (type) {
            case INT_TYPE: {
                new IntVariable(variableInput, scope, isFinal, isExist);
                break;
            }
            case STRING_TYPE: {
                new StringVariable(variableInput, scope, isFinal, isExist);
                break;
            }
            case DOUBLE_TYPE: {
                new DoubleVariable(variableInput, scope, isFinal, isExist);
                break;
            }
            case BOOLEAN_TYPE: {
                new BooleanVariable(variableInput, scope, isFinal, isExist);
                break;
            }
            case CHAR_TYPE: {
                new CharVariable(variableInput, scope, isFinal, isExist);
                break;
            }
            default:
                //should never reach here
                throw new CompilationException();
        }
    }


}
