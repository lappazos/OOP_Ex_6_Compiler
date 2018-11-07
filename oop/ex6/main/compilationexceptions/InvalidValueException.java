package oop.ex6.main.compilationexceptions;

/**
 * Exception indicates an attempt to assign wrong value to variable
 *
 * @author lioraryepaz, shahar.mazia
 */

public class InvalidValueException extends CompilationException {
    /**
     * Constructor
     *
     * @param value message to be sent
     */
    public InvalidValueException(String value) {
        super(value);
    }
}
