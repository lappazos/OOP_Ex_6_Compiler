package oop.ex6.main.compilationexceptions;

/**
 * Exception indicates a call to a method with wrong number of args
 *
 * @author lioraryepaz, shahar.mazia
 */

public class WrongNumberOfArgsException extends CompilationException {
    /**
     * Constructor
     *
     * @param message message to be sent
     */
    public WrongNumberOfArgsException(String message) {
        super(message);
    }
}
