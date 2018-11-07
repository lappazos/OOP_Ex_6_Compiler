package oop.ex6.main.method;

import oop.ex6.main.Scope;
import oop.ex6.main.compilationexceptions.CompilationException;
import oop.ex6.main.compilationexceptions.NoReturnStatementException;
import oop.ex6.main.variable.Variable;

import java.util.List;

/**
 * class represent a scope of type method
 *
 * @author lioraryepaz, shahar.mazia
 */

public class Method extends Scope {
    private int numOfMethodArgument;
    private final String methodName;

    /**
     * Constructor
     *
     * @param scope       parent scope
     * @param name        method name
     * @param methodLines list of method scope lines
     */
    Method(Scope scope, String name, List<String> methodLines) {
        super(scope, methodLines);
        methodName = name;

    }

    /**
     * parse the body og the method
     *
     * @throws CompilationException compilation exception
     */
    public void initMethodBody() throws CompilationException {
        scopeAnalyzer();
        if (!scopeLines.get(scopeLines.size() - 1).matches(returnRegex)) {
            throw new NoReturnStatementException("for -" + this.methodName);
        }
    }

    /**
     * getter
     *
     * @return method name
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * setter for numOfMethodArguments
     */
    void addNumOfMethodArgument() {
        this.numOfMethodArgument += 1;
    }

    /**
     * getter
     *
     * @return numOfMethodArgument
     */
    public int getNumOfMethodArgument() {
        return numOfMethodArgument;
    }

    /**
     * getter
     *
     * @return list of method variables
     */
    public List<Variable> getMethodVariables() {
        return variables;
    }
}
