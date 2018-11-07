package oop.ex6.main.variable;

import oop.ex6.main.Scope;
import oop.ex6.main.compilationexceptions.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Char Variable
 *
 * @author lioraryepaz, shahar.mazia
 */

public class CharVariable extends Variable {

    private Character data;
    private Pattern p = Pattern.compile("'.'");

    /**
     * Constructor
     *
     * @param input   variable input line
     * @param scope   parent scope
     * @param isFinal is the variable final or not
     * @param isExist is the variable part from method declaration
     * @throws CompilationException complation exception
     */
    CharVariable(String input, Scope scope, boolean isFinal, boolean isExist) throws CompilationException {
        super(input, scope, isFinal, isExist);
        if (dataInput != null) {
            variableAssigning(dataInput, scope);
        }
    }

    @Override
    public void variableAssigning(String valueInput, Scope scope) throws CompilationException {
        initializedFinalCheck();
        Matcher tempMatcher = p.matcher(valueInput);
        if (tempMatcher.matches()) {
            data = valueInput.charAt(1);
        } else {
            Variable assignedVariable = scope.findVariable(valueInput);
            Object assignedVariableData = assignedVariableCheck(valueInput, assignedVariable);
            if (assignedVariable instanceof CharVariable) {
                data = (Character) assignedVariableData;
            } else {
                throw new VariableTypeException(assignedVariable.getClass().getName());
            }
        }
    }

    @Override
    public Character getData() {
        return data;
    }

    @Override
    public void setData(Object object) {
        data = (Character) object;
    }
}
