package com.tsystems.javaschool.ecare.util;

/**
 * Class of own application exception.
 */
public class AppException extends RuntimeException {

    /*Field of exception message*/
    String message;

    /**
     * Constructor for transmitting a message to the class.
     *
     * @param message string message.
     */
    public AppException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * Constructor for transmitting a message and exception to the class.
     * @param message string message.
     * @param e exception.
     */
    public AppException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
