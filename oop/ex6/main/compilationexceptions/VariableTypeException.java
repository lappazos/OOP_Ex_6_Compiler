package oop.ex6.main.compilationexceptions;

/**
 * Exception indicates an attempt to assign a variable a value from wrong type
 *
 * @author lioraryepaz, shahar.mazia
 */

public class VariableTypeException extends CompilationException {
    /**
     * Constructor
     *
     * @param message message to be sent
     */
    public VariableTypeException(String message) {
        super(message);
    }
}
