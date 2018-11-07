package oop.ex6.main.compilationexceptions;

/**
 * This class represent a super class for all types of compilation errors we will throw.
 *
 * @author lioraryepaz, shahar.mazia
 */

public class CompilationException extends Exception {
    /**
     * Constructor
     */
    public CompilationException() {
        super();
    }

    /**
     * Constructor
     *
     * @param message message to be sent
     */
    CompilationException(String message) {
        super(message);
    }
}
