package oop.ex6.main.compilationexceptions;

/**
 * Exception indicates a method without a return statement
 *
 * @author lioraryepaz, shahar.mazia
 */

public class NoReturnStatementException extends CompilationException {
    /**
     * Constructor
     *
     * @param line message to be sent
     */
    public NoReturnStatementException(String line) {
        super(line);
    }
}
