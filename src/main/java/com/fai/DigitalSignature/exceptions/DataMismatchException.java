package com.fai.DigitalSignature.exceptions;

/**
 * DataMismatchException is the Exception class we'll be using to handle exceptions when ever their is a data mismatch
 */
public class DataMismatchException extends Exception {

    public DataMismatchException(String message) {
        super(message);
    }
}
