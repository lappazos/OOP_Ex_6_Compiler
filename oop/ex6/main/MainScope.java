package oop.ex6.main;

import oop.ex6.main.compilationexceptions.CompilationException;
import oop.ex6.main.compilationexceptions.InvalidLineException;
import oop.ex6.main.method.Method;
import oop.ex6.main.method.MethodSignatureParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Scope of the File itself
 *
 * @author lioraryepaz, shahar.mazia
 */

public class MainScope extends Scope {

    /**
     * Constructor
     *
     * @param linesList list of file lines
     * @throws CompilationException compilation  exception
     */
    MainScope(ArrayList<String> linesList) throws CompilationException {
        super(null, linesList);
        scopeLines.removeAll(emptyLineFinder());
        mainScopeParser();
    }

    /**
     * parse lines of main scope
     *
     * @throws CompilationException compilation exception
     */
    private void mainScopeParser() throws CompilationException {
        for (int i = 0; i < scopeLines.size(); i++) {
            String line = scopeLines.get(i);
            if (generalScopeCheck(line)) continue;
            String methodVariableDeclaration = "(" + "(\\s*final\\s+)?" + variableTypesRegex + variableNameRegex;
            Pattern methodDeclaration = Pattern.compile(methodNameRegex + "\\((" + methodVariableDeclaration + ",)*" +
                    methodVariableDeclaration + ")" + "|\\s*)\\)\\s*\\{\\s*");
            methodDeclarationMatcher = methodDeclaration.matcher(line);
            if (methodDeclarationMatcher.matches()) {
                i = wrapScope(i, MethodSignatureParser.getInstance(), methodDeclarationMatcher);
                continue;
            }
            throw new InvalidLineException(line);
        }
        for (Method method : methods) {
            method.initMethodBody();
        }
    }

    /**
     * find empty lines from scope
     *
     * @return empty lines list
     */
    private List<String> emptyLineFinder() {
        List<String> toRemove = new ArrayList<String>();
        for (String line : scopeLines) {
            Pattern emptyLine = Pattern.compile("\\s*");
            if (emptyLine.matcher(line).matches()) {
                toRemove.add(line);
            }
        }
        return toRemove;
    }

    /**
     * setter
     *
     * @param method add method to scope
     */
    public void addMethod(Method method) {
        methods.add(method);
    }
}
