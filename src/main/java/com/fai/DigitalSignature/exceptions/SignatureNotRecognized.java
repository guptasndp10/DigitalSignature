package com.fai.DigitalSignature.exceptions;

/**
 * SignatureNotRecognized is the Exception class we'll be using to handle exceptions when the signatures are not Recognized
 */
public class SignatureNotRecognized extends Exception {

    public SignatureNotRecognized(String message) {
        super(message);
    }
}
