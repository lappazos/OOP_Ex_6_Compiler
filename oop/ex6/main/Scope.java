package oop.ex6.main;

import oop.ex6.main.Ifwhile.ConditionSignatureParser;
import oop.ex6.main.compilationexceptions.*;
import oop.ex6.main.compilationexceptions.NoSuchMethodException;
import oop.ex6.main.method.Method;
import oop.ex6.main.variable.Variable;
import oop.ex6.main.variable.VariableAssigningParser;
import oop.ex6.main.variable.VariableParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class represents scope object, as main scope, method or ifWhile blocks
 *
 * @author lioraryepaz, shahar.mazia
 */

public abstract class Scope {

    private static final String COMMENT_LINE = "//";
    private static final String VARIABLE_ASSIGNING_DELIMITER = "\\s*,\\s*";
    private static final int METHOD_NAME_GROUP = 1;
    private static final int VARIABLE_ASSIGNING_INPUT = 2;
    /**
     * list of scope variables
     */
    protected List<Variable> variables = new ArrayList<Variable>();
    /**
     * list of scope methods
     */
    List<Method> methods = new ArrayList<Method>();
    /**
     * list of scope lines
     */
    protected List<String> scopeLines;
    /**
     * regex for method name
     */
    final static String methodNameRegex = "(\\s*void\\s+([a-zA-Z]\\w*)\\s*)";
    /**
     * regex for variable name
     */
    final static String variableNameRegex = "(?:(?:[A-Za-z]\\w*)|(?:_\\w+))\\s*";
    /**
     * regex for return line
     */
    protected final static String returnRegex = "\\s*return\\s*;\\s*";
    private final static Pattern returnPattern = Pattern.compile(returnRegex);
    /**
     * regex for variable type
     */
    final static String variableTypesRegex = "(\\s*(int|double|boolean|char|String)\\s+)";
    private Scope parentScope;
    private final static String variableValueRegex = "(?:=\\s*.+\\s*)";
    private final static String stringValue = "(?:\"[^\"]*\")";
    private final static String charValue = "(?:'.?')";
    private final static String doubleValue = "(?:-?\\d+\\.\\d+)";
    private final static String booleanValue = "true|false|-?\\d+|" + doubleValue;
    private final static String generalValue = "(?:-?\\w+)";
    private final static String conditionValue = "(?:" + variableNameRegex + "|" + booleanValue + ")\\s*";
    private final static Pattern conditionRegex = Pattern.compile("\\s*(if|while)\\s*\\(\\s*((?:" + conditionValue +
            "(?:\\|\\||&&)\\s*)*" + conditionValue + ")\\)\\s*\\{\\s*");
    private final static String possibleValue = "\\s*(?:" + stringValue + "|" + charValue + "|" + doubleValue + "|"
            + generalValue + ")\\s*";
    private final static String variableDeclaration = "(?:" + variableNameRegex + "(?:|=" + possibleValue + ")\\s*)";
    private final static Pattern variableCreation = Pattern.compile("\\s*((final\\s+)?" + variableTypesRegex + "((?:" +
            variableDeclaration + ",\\s*)*\\s*" + variableDeclaration + "));\\s*");
    private Matcher tempMatcher;
    /**
     * Matcher object
     */
    Matcher methodDeclarationMatcher;
    private final static Pattern variableAssigning = Pattern.compile(variableNameRegex + variableValueRegex + ";\\s*");
    private final static Pattern scopeOpener = Pattern.compile("\\s*(?:if|while)\\s*\\(.*\\)\\s*\\{\\s*");
    private final static Pattern scopeCloser = Pattern.compile("\\s*}\\s*");
    private final static Pattern methodCall = Pattern.compile("\\s*([a-zA-Z]\\w*)\\s*\\(" + "((?:(?:" + possibleValue +
            "\\s*,\\s*)*" + possibleValue + ")|)\\s*\\)\\s*;\\s*");

    /**
     * Constructor
     *
     * @param parentScope parent scope
     * @param linesList   list of file lines
     */
    public Scope(Scope parentScope, List<String> linesList) {
        this.parentScope = parentScope;
        this.scopeLines = linesList;
    }

    /**
     * Constructor
     *
     * @param linesList list of file lines
     */
    public Scope(List<String> linesList) {
        this.parentScope = null;
        this.scopeLines = linesList;
    }

    /**
     * checks if a certain variable name already exist in its scope
     *
     * @param variableName name of variable
     * @return true if exist, false otherwise
     */
    public boolean isVariableExistInScope(String variableName) {
        for (Variable variable : variables) {
            if (variable.getVariableName().equals(variableName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * parser of part of the possible scope line - general for all scopes
     *
     * @param line line to parse
     * @return true if a match was found and we could skip for next line
     * @throws CompilationException compilation exception
     */
    boolean generalScopeCheck(String line) throws CompilationException {
        tempMatcher = variableCreation.matcher(line);
        if (tempMatcher.matches()) {
            VariableParser.getInstance().parseVariableDeclarationLine(this, tempMatcher, true);
            return true;
        }
        tempMatcher = variableAssigning.matcher(line);
        if (tempMatcher.matches()) {
            VariableAssigningParser.getInstance().assignVariables(line, this);
            return true;
        }
        return line.startsWith(COMMENT_LINE);
    }

    /**
     * detect scope limits when scope signature is detected
     *
     * @param i       line of scope beginning
     * @param parser  instance of relevant scope parser
     * @param matcher matcher of signature line
     * @return last line of scope
     * @throws CompilationException compilation exception
     */
    int wrapScope(int i, ScopeParser parser, Matcher matcher) throws CompilationException {
        String line;
        int counter = 1;
        for (int j = i + 1; j < scopeLines.size(); j++) {
            line = scopeLines.get(j);
            tempMatcher = scopeCloser.matcher(line);
            if (tempMatcher.matches()) {
                counter -= 1;
            }
            tempMatcher = scopeOpener.matcher(line);
            if (tempMatcher.matches()) {
                counter += 1;
            }
            if (counter == 0) {
                parser.parseScopeSignature(scopeLines.subList(i + 1, j), matcher, this);
                i = j;
                break;
            }
        }
        return i;
    }

    /**
     * parse scope inner lies
     *
     * @throws CompilationException compilation exception
     */
    public void scopeAnalyzer() throws CompilationException {
        List<Variable> unInitializedList = unInitializedVariablesMarker();
        for (int i = 0; i < scopeLines.size(); i++) {
            String line = scopeLines.get(i);
            if (generalScopeCheck(line)) continue;
            tempMatcher = returnPattern.matcher(line);
            if (tempMatcher.matches()) {
                continue;
            }
            tempMatcher = methodCall.matcher(line);
            if (tempMatcher.matches()) {
                methodCallHandler();
                continue;
            }
            Matcher conditionSignatureMatcher = conditionRegex.matcher(line);
            if (conditionSignatureMatcher.matches()) {
                i = wrapScope(i, ConditionSignatureParser.getInstance(), conditionSignatureMatcher);
                continue;
            }
            throw new InvalidLineException(line);
        }
        for (Variable variable : unInitializedList) {
            variable.setIsInitializedToFalse();
            variable.setData(null);
        }
    }

    /**
     * parse method call line
     *
     * @throws CompilationException compilation exception
     */
    private void methodCallHandler() throws CompilationException {
        Method method = parentScope.findMethod(tempMatcher.group(METHOD_NAME_GROUP));
        String[] variableAssigningInput = tempMatcher.group(VARIABLE_ASSIGNING_INPUT).split(VARIABLE_ASSIGNING_DELIMITER);
        int numOfInputArgs;
        if (variableAssigningInput[0].equals("")) {
            numOfInputArgs = 0;
        } else {
            numOfInputArgs = variableAssigningInput.length;
        }
        if (method != null) {
            if (numOfInputArgs != method.getNumOfMethodArgument()) {
                throw new WrongNumberOfArgsException(tempMatcher.group(VARIABLE_ASSIGNING_INPUT));
            }
        } else {
            throw new NoSuchMethodException(tempMatcher.group(METHOD_NAME_GROUP));
        }
        for (int j = 0; j < method.getNumOfMethodArgument(); j++) {
            method.getMethodVariables().get(j).variableAssigning(variableAssigningInput[j].trim(), this);
        }
    }

    /**
     * find the unInitialized variables for a given scope
     *
     * @return list of these variables
     */
    private List<Variable> unInitializedVariablesMarker() {
        List<Variable> unInitializedList = new ArrayList<>();
        Scope parent = this.parentScope;
        while (parent != null) {
            for (Variable variable : parent.getVariables()) {
                if (!variable.isInitialized()) {
                    unInitializedList.add(variable);
                }
            }
            parent = parent.parentScope;
        }
        return unInitializedList;
    }

    /**
     * getter
     *
     * @return regex pattern
     */
    public Pattern getVariableCreation() {
        return variableCreation;
    }

    /**
     * setter
     *
     * @param variable a variable to add to scope
     */
    public void addVariable(Variable variable) {
        this.variables.add(variable);
    }

    /**
     * getter
     *
     * @return list of scope variables
     */
    private List<Variable> getVariables() {
        return variables;
    }

    /**
     * search for a specific method in a given scope and his parents
     *
     * @param methodName method name to search for
     * @return the method instance if found, null otherwise
     */
    private Method findMethod(String methodName) {
        for (Method method : methods) {
            if (method.getMethodName().equals(methodName)) {
                return method;
            }
        }
        if (parentScope != null) {
            return parentScope.findMethod(methodName);
        }
        return null;
    }

    /**
     * search for a given variable in the scope and his parents
     *
     * @param name variable name to look for
     * @return the variable if found, null otherwise
     */
    public Variable findVariable(String name) {
        for (Variable variable : variables) {
            if (variable.getVariableName().equals(name)) {
                return variable;
            }
        }
        if (parentScope != null) {
            return parentScope.findVariable(name);
        }
        return null;
    }
}
