package com.fai.DigitalSignature.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;
import java.security.cert.X509Certificate;

/**
 * Encryption is the class will be using to Encryption the Document
 */
public class Encryption {

    static Logger logger = LoggerFactory.getLogger(Encryption.class);
    //    static SecretKey secretKey = null;
    static IvParameterSpec ivspec = null;

    /**
     * encryptData encrypts the document
     * @param skey
     * @param dataInBytes
     *
     * @return  byte[] encryptedData
     */
    static byte[] encryptData(SecretKey skey, byte[] dataInBytes) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        byte[] encryptedDataBytes = null;
        DocEncryptDecryptService.srandom.nextBytes(DocEncryptDecryptService.iv);
        IvParameterSpec ivspec = DocEncryptDecryptService.ivspec;
        Cipher ci = Cipher.getInstance(Configuration.SYMMETRIC_ALGO);
        ci.init(Cipher.ENCRYPT_MODE, skey, ivspec);
        encryptedDataBytes = ci.doFinal(dataInBytes);
        return encryptedDataBytes;
    }

    /**
     * encryptKey verifies the document integrity
     * @param x509Certificate
     * @param skeyInBytes
     *
     * @return  byte[] encryptSecKey
     */
    static byte[] encryptKey(X509Certificate x509Certificate, byte[] skeyInBytes) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        byte[] encryptSecKey = null;
        Cipher cipher = Cipher.getInstance(Configuration.ASYMMETRIC_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, x509Certificate);
        encryptSecKey = cipher.doFinal(skeyInBytes);
        return encryptSecKey;
    }
}
