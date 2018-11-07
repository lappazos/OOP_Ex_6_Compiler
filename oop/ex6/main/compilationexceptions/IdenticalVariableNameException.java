package oop.ex6.main.compilationexceptions;

/**
 * Exception indicates attempt to create more than one variable with the same name at the same scope
 *
 * @author lioraryepaz, shahar.mazia
 */

public class IdenticalVariableNameException extends CompilationException {
    /**
     * Constructor
     *
     * @param message message to be sent
     */
    public IdenticalVariableNameException(String message) {
        super(message);
    }

}
