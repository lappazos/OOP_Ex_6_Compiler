package oop.ex6.main.variable;

import oop.ex6.main.Scope;
import oop.ex6.main.compilationexceptions.CompilationException;
import oop.ex6.main.compilationexceptions.FinalException;
import oop.ex6.main.compilationexceptions.IdenticalVariableNameException;
import oop.ex6.main.compilationexceptions.InvalidValueException;

/**
 * abstract class for variable
 *
 * @author lioraryepaz, shahar.mazia
 */

public abstract class Variable {

    private static final String EQUAL_SIGN_DELIMITER = "=";
    private static final int VARIABLE_PARTS = 2;
    private static final int MIN_PARTS = 1;
    private String variableName;
    /**
     * variable Scope
     */
    protected Scope scope;
    /**
     * data input for variable
     */
    String dataInput;
    /**
     * is variable final or not
     */
    protected boolean isFinal;
    /**
     * is part from method declaration
     */
    protected boolean isExist;

    private boolean isInitialized = false;

    /**
     * Constructor
     *
     * @param input   variable input line
     * @param scope   parent scope
     * @param isFinal is the variable final or not
     * @param isExist is the variable part from method declaration
     * @throws CompilationException complation exception
     */
    public Variable(String input, Scope scope, boolean isFinal, boolean isExist) throws CompilationException {
        this.scope = scope;
        this.isFinal = isFinal;
        this.isExist = isExist;
        String[] variablesElements = input.split(EQUAL_SIGN_DELIMITER, VARIABLE_PARTS);
        variableName = variablesElements[0].trim();
        if (this.scope.isVariableExistInScope(variableName)) {
            throw new IdenticalVariableNameException(variableName);
        }
        scope.addVariable(this);
        if (variablesElements.length > MIN_PARTS) {
            dataInput = variablesElements[1].trim();
        } else {
            if (isFinal && isExist) {
                throw new FinalException();
            }
        }
    }

    /**
     * Constructor
     */
    public Variable() {
    }

    /**
     * getter
     *
     * @return variable name
     */
    public String getVariableName() {
        return variableName;
    }

    /**
     * getter
     *
     * @return variable data
     */
    public abstract Object getData();

    /**
     * setter
     *
     * @param object object containing data for variable
     */
    public abstract void setData(Object object);

    /**
     * checks if final & initialized
     *
     * @throws FinalException exception of attempt to assign final variable
     */
    void initializedFinalCheck() throws FinalException {
        if (isInitialized && isFinal) {
            throw new FinalException();
        }
        isInitialized = true;
    }

    /**
     * method for assigning data to variable
     *
     * @param valueInput input
     * @param scope      scope of assigning
     * @throws CompilationException compilation exception
     */
    public abstract void variableAssigning(String valueInput, Scope scope) throws CompilationException;

    /**
     * getter
     *
     * @return isFinal
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * helper for check received assigned variables
     *
     * @param valueInput       input
     * @param assignedVariable variable to assign his data from
     * @return data from assigned variable
     * @throws InvalidValueException exception for attemp to assign inValid data or variable
     */
    Object assignedVariableCheck(String valueInput, Variable assignedVariable) throws InvalidValueException {
        if (assignedVariable == null) {
            throw new InvalidValueException(valueInput);
        }
        Object assignedVariableData = assignedVariable.getData();
        if (assignedVariableData == null && assignedVariable.isExist) {
            throw new InvalidValueException(assignedVariable.getVariableName());
        }
        return assignedVariableData;
    }

    /**
     * getter
     *
     * @return isInitialized
     */
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * setter
     */
    public void setIsInitializedToFalse() {
        isInitialized = false;
    }
}
