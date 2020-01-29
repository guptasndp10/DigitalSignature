package com.fai.DigitalSignature.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.PrivateKey;
import java.security.SecureRandom;

/**
 * Decryption is the class will be using to Decryption the Document
 */
public class Decryption {

    static Logger logger = LoggerFactory.getLogger(Decryption.class);

    /**
     * decryptKey decrypt the key
     * @param privateKey
     * @param encryptkey
     *
     * @return  byte[] key
     */
    static byte[] decryptKey(PrivateKey privateKey, byte[] encryptkey){
        Cipher cipher = null;
        byte[] key = null;
        try {
            cipher = Cipher.getInstance(Configuration.ASYMMETRIC_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            key = cipher.doFinal(encryptkey);
            return key;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    /**
     * decryptData decrypts the document
     * @param skey
     * @param encryptData
     *
     * @return  byte[] documentData
     */
    static byte[] decryptData(byte[] skey, byte[] encryptData){
        Cipher ci = null;
        SecretKeySpec keySpec = null;
        byte[] decryptData = null;
        try {
            SecretKey key = new SecretKeySpec(skey, 0, skey.length,  Configuration.SECRET_KEYGEN_ALGO);
            IvParameterSpec ivspec = DocEncryptDecryptService.ivspec;
            ci = Cipher.getInstance(Configuration.SYMMETRIC_ALGO);
            ci.init(Cipher.DECRYPT_MODE, key, ivspec);
            decryptData = ci.doFinal(encryptData);
            return decryptData;
        } catch(Exception e) {
            logger.error("Error occured " + e.getMessage());
        }
        return decryptData;
    }

}
