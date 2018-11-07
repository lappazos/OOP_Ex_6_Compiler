package oop.ex6.main.compilationexceptions;

/**
 * Exception indicates a call to a non-exist method
 *
 * @author lioraryepaz, shahar.mazia
 */

public class NoSuchMethodException extends CompilationException {
    /**
     * Constructor
     *
     * @param message message to be sent
     */
    public NoSuchMethodException(String message) {
        super(message);
    }
}
