package com.fai.DigitalSignature.helper;

import com.fai.DigitalSignature.crypto.Configuration;
import com.fai.DigitalSignature.crypto.ReadKeyStoreService;
import com.fai.DigitalSignature.exceptions.UserNotRecognized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Base64;

/**
 * Class handles file related operations such as writing to file, reading data from file.
 */
public class FileManager {

    private static Logger LOGGER = LoggerFactory.getLogger(FileManager.class);

    /**
     * getFileInBytes fetch the file data in bytes
     * @param file
     *
     * @return  void
     */
    public static byte[] getFileInBytes(File file) {
        FileInputStream fis = null;
        byte[] fbytes = null;
        try {
            fis = new FileInputStream(file);
            fbytes = new byte[(int) file.length()];
            fis.read(fbytes);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fbytes;
    }

    /**
     * writeToFile write the bytes into a file
     * @param output file
     * @param toWrite data to be written
     *
     * @return  void
     */
    public static File writeToFile(File output, byte[] toWrite) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(output);
            fos.write(toWrite);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * readPrivateKeyFromKeystore reads the private key from the keystore
     * @param userId
     *
     * @return PrivateKey
     */
    public static PrivateKey readPrivateKeyFromKeystore(String userId) throws Exception{
        PrivateKey privateKey = null;

        Validation.validateString(userId, userId);
        privateKey = new ReadKeyStoreService().readCertificateDetails(Configuration.KEYGEN_PATH+userId+"/"+userId+Configuration.KEYSTORE_EXTENSION, Configuration.KEYSTORE_PASSWORD).getPrivateKey();
        if(!privateKey.equals(null)) {
            return privateKey;
        } else{
            throw new UserNotRecognized("User's Private Key doesn't exist, Not a valid user");
        }


    }

    /**
     * readX509CertificateFromKeystore reads the X509Certificate from the keystore
     * @param userId
     *
     * @return X509Certificate
     */
    public static X509Certificate readX509CertificateFromKeystore(String userId) throws Exception{
        X509Certificate x509Certificate = null;
        Validation.validateString(userId, userId);
        x509Certificate = new ReadKeyStoreService().readCertificateDetails(Configuration.KEYGEN_PATH+userId+"/"+userId+Configuration.KEYSTORE_EXTENSION, Configuration.KEYSTORE_PASSWORD).getX509Certificate();
        if(!x509Certificate.equals(null)) {
            return x509Certificate;
        } else{
            throw new UserNotRecognized("User's Certificate doesn't exist, Not a valid user");
        }


    }

    /**
     * encodeByteToString encodes the data from byte[] to String
     * @param data to be encoded
     *
     * @return X509Certificate
     */
    public static String encodeByteToString(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * decodeStringToByte decodes the data from String to byte[]
     * @param data to be decoded
     *
     * @return byte[]
     */
    public static byte[] decodeStringToByte(String data){
        return Base64.getDecoder().decode(data);
    }

    /**
     * calculateHash computes the hash for the given data
     * @param dataInBytes
     *
     * @return byte[]
     */
    public static byte[] calculateHash(byte[] dataInBytes){
        try {

            return MessageDigest.getInstance(Configuration.HASHING_ALGO).digest(dataInBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static SecretKey readSecretKeyFromDB(String hash){
////        fetch the secret key against the hash from mongo
//        SecretkeyModel smodel = secretkeyRepository.findSecretKeyByDocHash(hash);
//        SecretKey key = new SecretKeySpec(smodel.getSecretKey(), 0, smodel.getSecretKey().length,  Configuration.SECRET_KEYGEN_ALGO);
//        return key;
//    }

//    public static void storeSecretKeyInDB(String hash, SecretKey secretKey) {
//        store secret key against the hash in mongo
//        SecretkeyModel secretkeyModel = new SecretkeyModel(hash, secretKey.getEncoded());
//        secretkeyRepository.save(secretkeyModel);
//    }

}
