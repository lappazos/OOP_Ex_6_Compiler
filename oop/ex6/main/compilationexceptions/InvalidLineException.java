package oop.ex6.main.compilationexceptions;

/**
 * Exception indicates a Line with no match to any known pattern
 *
 * @author lioraryepaz, shahar.mazia
 */

public class InvalidLineException extends CompilationException {
    /**
     * Constructor
     *
     * @param value message to be sent
     */
    public InvalidLineException(String value) {
        super(value);
    }
}
