package oop.ex6.main.variable;

import oop.ex6.main.Scope;
import oop.ex6.main.compilationexceptions.*;

/**
 * Int Variable
 *
 * @author lioraryepaz, shahar.mazia
 */

public class IntVariable extends Variable {

    private Integer data;

    /**
     * Constructor
     *
     * @param input   variable input line
     * @param scope   parent scope
     * @param isFinal is the variable final or not
     * @param isExist is the variable part from method declaration
     * @throws CompilationException complation exception
     */
    IntVariable(String input, Scope scope, boolean isFinal, boolean isExist) throws CompilationException {
        super(input, scope, isFinal, isExist);
        if (dataInput != null) {
            variableAssigning(dataInput, scope);
        }
    }

    @Override
    public void variableAssigning(String valueInput, Scope scope) throws CompilationException {
        initializedFinalCheck();
        try {
            data = Integer.parseInt(valueInput);
        } catch (NumberFormatException e) {
            Variable assignedVariable = scope.findVariable(valueInput);
            Object assignedVariableData = assignedVariableCheck(valueInput, assignedVariable);
            if (assignedVariable instanceof IntVariable) {
                data = (Integer) assignedVariableData;
            } else {
                throw new VariableTypeException(assignedVariable.getClass().getName());
            }
        }
    }

    @Override
    public Integer getData() {
        return data;
    }

    @Override
    public void setData(Object object) {
        data = (Integer) object;
    }
}
