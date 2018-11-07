package oop.ex6.main;

import oop.ex6.main.compilationexceptions.CompilationException;

import java.util.List;
import java.util.regex.Matcher;

/**
 * Interface for Scope Parser
 *
 * @author lioraryepaz, shahar.mazia
 */

public interface ScopeParser {

    /**
     * parse scope lines
     *
     * @param scopeLines              list of scope lines
     * @param scopeDeclarationMatcher matcher for scope signature line
     * @param scope                   parent scope
     * @throws CompilationException compilation exception
     */
    void parseScopeSignature(List<String> scopeLines, Matcher scopeDeclarationMatcher, Scope scope)
            throws CompilationException;
}
