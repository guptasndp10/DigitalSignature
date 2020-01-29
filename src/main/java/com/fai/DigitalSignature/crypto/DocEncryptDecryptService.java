package com.fai.DigitalSignature.crypto;

import com.fai.DigitalSignature.helper.FileManager;
import com.fai.DigitalSignature.model.EncryptionDetails;
import com.fai.DigitalSignature.model.FileDetails;
import com.fai.DigitalSignature.model.SignEncryptDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DocEncryptDecryptService is the service we'll be using to Document Encryption and Decryption operations
 */
@Component
public class DocEncryptDecryptService {

    static Logger LOGGER = LoggerFactory.getLogger(DocEncryptDecryptService.class);

    static byte[] iv = new byte[128/8];
    static SecureRandom srandom = new SecureRandom();
    static IvParameterSpec ivspec = new IvParameterSpec(iv);

    /**
     * encryptDocument encrypt the document
     * @param userId
     * @param counterUserList
     * @param dataInBytes
     * @param docHash
     *
     * @return  SignEncryptDetails json object
     */
    public SignEncryptDetails encryptDocument(String userId, List<String> counterUserList, byte[] dataInBytes, String docHash) throws Exception {
        String encryptedSecKey = null;
        String encryptedDataBytes = null;
        X509Certificate x509Certificate = null;
        List<EncryptionDetails> encryptList = new ArrayList<>();
        SignEncryptDetails signEncryptDetails = new SignEncryptDetails();
        try {
            x509Certificate = FileManager.readX509CertificateFromKeystore(userId);
            SecretKey secretKey = generateSecretkey();
//          secretKey = FileManager.readSecretKeyFromDB(hash);
//          if(secretKey == null){
//              secretKey = generateSecretkey();
//              FileManager.storeSecretKeyInDB(hash, secretKey);
//          }
            encryptedDataBytes = FileManager.encodeByteToString(Encryption.encryptData(secretKey, dataInBytes));
            encryptedSecKey = FileManager.encodeByteToString(Encryption.encryptKey(x509Certificate, secretKey.getEncoded()));
            encryptList.add(new EncryptionDetails(userId, encryptedSecKey));
            for (String counterUser: counterUserList) {
                x509Certificate = FileManager.readX509CertificateFromKeystore(counterUser);
                encryptedSecKey = FileManager.encodeByteToString(Encryption.encryptKey(x509Certificate, secretKey.getEncoded()));
                encryptList.add(new EncryptionDetails(counterUser, encryptedSecKey));
                LOGGER.debug("FILE IS ENCRYPTED BY THE COUNTER USER WITH USERID AS"+counterUser);
            }
            signEncryptDetails.setDocHash(docHash);
            signEncryptDetails.setEncryptedData(encryptedDataBytes);
            signEncryptDetails.setEncryptionDetails(encryptList);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Error occured " + e.getMessage());
        } catch (NoSuchPaddingException e) {
            LOGGER.error("Error occured " + e.getMessage());
        } catch (BadPaddingException e) {
            LOGGER.error("Error occured " + e.getMessage());
        } catch (IllegalBlockSizeException e) {
            LOGGER.error("Error occured " + e.getMessage());
        } catch (InvalidKeyException e) {
            LOGGER.error("Error occured " + e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            LOGGER.error("Error occured " + e.getMessage());
        }
        return signEncryptDetails;
    }

    /**
     * generateSecretkey generates the secret key
     *
     * @return  SecretKey object
     */
    public SecretKey generateSecretkey() throws NoSuchAlgorithmException {
        KeyGenerator kgen = KeyGenerator.getInstance(Configuration.SECRET_KEYGEN_ALGO);
        kgen.init(128);
        return kgen.generateKey();
    }

    /**
     * documentDecrypt decrypts the document
     * @param userId
     * @param encryptkey
     * @param encryptData
     *
     * @return  byte[] encryptSecKey
     */
    public byte[] documentDecrypt(String userId, byte[] encryptkey, byte[] encryptData) throws Exception {
        Cipher cipher = null;
        byte[] sKey = null;
        byte[] decryptData = null;
        FileDetails fileDetails = null;
        PrivateKey privateKey = FileManager.readPrivateKeyFromKeystore(userId);
        sKey = Decryption.decryptKey(privateKey, encryptkey);
        decryptData = Decryption.decryptData(sKey, encryptData);
        return decryptData;
    }



}
