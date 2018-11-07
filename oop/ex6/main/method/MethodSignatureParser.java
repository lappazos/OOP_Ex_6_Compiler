package oop.ex6.main.method;

import oop.ex6.main.MainScope;
import oop.ex6.main.Scope;
import oop.ex6.main.ScopeParser;
import oop.ex6.main.compilationexceptions.CompilationException;
import oop.ex6.main.variable.VariableParser;

import java.util.List;
import java.util.regex.Matcher;

/**
 * Singleton for parsing method signature line
 *
 * @author lioraryepaz, shahar.mazia
 */

public class MethodSignatureParser implements ScopeParser {

    private static final int METHOD_NAME = 2;
    private static final int METHOD_VARIABLE_INPUT = 3;
    //instance of singleton
    private static MethodSignatureParser instance = new MethodSignatureParser();
    ;

    /**
     * private singleton constructor
     */
    private MethodSignatureParser() {
    }

    /**
     * singleton getInstance
     *
     * @return instance of this singleton class
     */
    public static MethodSignatureParser getInstance() {
        return instance;
    }

    /**
     * method that parse signature of methods blocks
     *
     * @param methodsLines             list of scope lines matcher for first signature line
     * @param methodDeclarationMatcher matcher for first signature line
     * @param scope                    parent scope of this block
     * @throws CompilationException compilation exception
     */
    public void parseScopeSignature(List<String> methodsLines, Matcher methodDeclarationMatcher, Scope scope)
            throws CompilationException {
        String methodName = methodDeclarationMatcher.group(METHOD_NAME);
        String[] methodVariablesInput = methodDeclarationMatcher.group(METHOD_VARIABLE_INPUT).split("\\s*,\\s*");
        Method method = new Method(scope, methodName, methodsLines);
        for (String variableInput : methodVariablesInput) {
            variableInput += ";";
            Matcher variableAnalyzer = method.getVariableCreation().matcher(variableInput);
            if (variableAnalyzer.matches()) {
                VariableParser.getInstance().parseVariableDeclarationLine(method, variableAnalyzer, false);
                method.addNumOfMethodArgument();
            }
        }
        ((MainScope) scope).addMethod(method);
    }

}
