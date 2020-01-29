package com.fai.DigitalSignature.exceptions;

/**
 * DecryptionFailed is the Exception class we'll be using to handle exceptions occur during the document decryption
 */
public class DecryptionFailed extends Exception {

    public DecryptionFailed(String message) {
        super(message);
    }
}
