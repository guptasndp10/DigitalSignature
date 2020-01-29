package com.fai.DigitalSignature.exceptions;

/**
 * UserNotRecognized is the Exception class we'll be using to handle exceptions occur during the user is reading the keystore
 */
public class UserNotRecognized extends Exception {

    public UserNotRecognized(String message) {
        super(message);
    }
}
