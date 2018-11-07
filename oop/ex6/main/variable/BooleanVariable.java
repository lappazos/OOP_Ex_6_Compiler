package oop.ex6.main.variable;

import oop.ex6.main.Scope;
import oop.ex6.main.compilationexceptions.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Boolean variable
 *
 * @author lioraryepaz, shahar.mazia
 */

public class BooleanVariable extends Variable {

    private Boolean data;
    private final static Pattern p = Pattern.compile("(true)|(false)|(-?\\d+(\\.\\d+)?)");

    /**
     * Constructor
     *
     * @param input   variable input line
     * @param scope   parent scope
     * @param isFinal is the variable final or not
     * @param isExist is the variable part from method declaration
     * @throws CompilationException complation exception
     */
    BooleanVariable(String input, Scope scope, boolean isFinal, boolean isExist) throws CompilationException {
        super(input, scope, isFinal, isExist);
        if (dataInput != null) {
            variableAssigning(dataInput, scope);
        }
    }

    /**
     * Constructor
     */
    protected BooleanVariable() {
    }

    @Override
    public void variableAssigning(String valueInput, Scope scope) throws CompilationException {
        initializedFinalCheck();
        Matcher tempMatcher = p.matcher(valueInput);
        if (tempMatcher.matches()) {
            data = Boolean.parseBoolean(valueInput);
        } else {
            Variable assignedVariable = scope.findVariable(valueInput);
            Object assignedVariableData = assignedVariableCheck(valueInput, assignedVariable);
            if (assignedVariable instanceof BooleanVariable) {
                data = (Boolean) assignedVariableData;
            } else if (assignedVariable instanceof IntVariable || assignedVariable instanceof DoubleVariable) {
                data = true;
            } else {
                throw new VariableTypeException(assignedVariable.getClass().getName());
            }
        }
    }

    @Override
    public Boolean getData() {
        return data;
    }

    @Override
    public void setData(Object object) {
        data = (Boolean) object;
    }
}
