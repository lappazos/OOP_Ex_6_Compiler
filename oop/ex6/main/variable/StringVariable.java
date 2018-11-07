package oop.ex6.main.variable;

import oop.ex6.main.Scope;
import oop.ex6.main.compilationexceptions.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String Variable
 *
 * @author lioraryepaz, shahar.mazia
 */

public class StringVariable extends Variable {

    private String data;
    private Pattern stringPattern = Pattern.compile("(\".*\")");

    /**
     * Constructor
     *
     * @param input   variable input line
     * @param scope   parent scope
     * @param isFinal is the variable final or not
     * @param isExist is the variable part from method declaration
     * @throws CompilationException complation exception
     */
    StringVariable(String input, Scope scope, boolean isFinal, boolean isExist) throws CompilationException {
        super(input, scope, isFinal, isExist);
        if (dataInput != null) {
            variableAssigning(dataInput, scope);
        }
    }

    @Override
    public void variableAssigning(String valueInput, Scope scope) throws CompilationException {
        initializedFinalCheck();
        Matcher m = stringPattern.matcher(valueInput);
        if (m.matches()) {
            data = valueInput;
        } else {
            Variable assignedVariable = scope.findVariable(valueInput);
            Object assignedVariableData = assignedVariableCheck(valueInput, assignedVariable);
            if (assignedVariable instanceof StringVariable) {
                data = (String) assignedVariableData;
            } else {
                throw new VariableTypeException(assignedVariable.getClass().getName());
            }
        }
    }


    @Override
    public String getData() {
        return data;
    }

    @Override
    public void setData(Object object) {
        data = (String) object;
    }
}
