package com.fai.DigitalSignature.crypto;

import com.fai.DigitalSignature.helper.FileManager;
import com.fai.DigitalSignature.helper.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.cert.X509Certificate;

/**
 * DocSigningService is the class will be using to sign the Document
 */
@Component
public class DocSigningService {

    Logger logger = LoggerFactory.getLogger(DocSigningService.class);

    /**
     * verifyFileContent verifies the document integrity
     * @param userId
     * @param dataInBytes
     *
     * @return  String  sign
     */
    public String signDocument(String userId, byte[] dataInBytes) throws Exception{
        byte[] signatureBytes = null;
        try {
            PrivateKey privateKey = FileManager.readPrivateKeyFromKeystore(userId);
            Signature privateSignature = Signature.getInstance(Configuration.SIG_ALGORITHM);
            privateSignature.initSign(privateKey);
            privateSignature.update(dataInBytes);
            signatureBytes = privateSignature.sign();
            return FileManager.encodeByteToString(signatureBytes);
        }catch (SignatureException e) {
            logger.error("Error occured " + e.getMessage());
        } catch (NoSuchAlgorithmException e1) {
            logger.error("Error occured " + e1.getMessage());
        } catch (InvalidKeyException e1) {
            logger.error("Error occured " + e1.getMessage());
        }
        return FileManager.encodeByteToString(signatureBytes);
    }

    /**
     * verifyFileContent verifies the document integrity
     * @param data
     * @param signature
     * @param userId
     * @return  boolean flag
     */
    public boolean verifySignature(byte[] data, byte[] signature, String userId) throws Exception {
        X509Certificate x509Certificate = FileManager.readX509CertificateFromKeystore(userId);
        Signature publicSignature = Signature.getInstance(Configuration.SIG_ALGORITHM);
        publicSignature.initVerify(x509Certificate);
        publicSignature.update(data);
        return publicSignature.verify(signature);
    }

}
