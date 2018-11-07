package oop.ex6.main.compilationexceptions;

/**
 * Exception indicates an attempt to assign a non-exist variable
 *
 * @author lioraryepaz, shahar.mazia
 */

public class VariableNotExistException extends CompilationException {
    /**
     * Constructor
     *
     * @param message message to be sent
     */
    public VariableNotExistException(String message) {
        super(message);
    }

}
