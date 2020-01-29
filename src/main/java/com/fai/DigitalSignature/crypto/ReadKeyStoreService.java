package com.fai.DigitalSignature.crypto;

import com.fai.DigitalSignature.crypto.Configuration;
import com.fai.DigitalSignature.model.CertificateDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * ReadKeyStoreService is the service will be using to read the keystore for the users
 */
public class ReadKeyStoreService {

    /**
     * readCertificateDetails reads the keystore from the keystore based on the user
     * @param jksPath
     * @param jksPassword
     *
     * @return  CertificateDetails object
     */
    public static CertificateDetails readCertificateDetails(String jksPath, String jksPassword) {

        Logger logger = LoggerFactory.getLogger(ReadKeyStoreService.class);
        CertificateDetails certDetails = null;

        try {

            boolean isAliasWithPrivateKey = false;
            KeyStore keyStore = KeyStore.getInstance(Configuration.KEYSTORE_TYPE);

//            FileInputStream inputStream = new FileInputStream(jksPath);

            // Provide location of Java Keystore and password for access
            keyStore.load(new FileInputStream(jksPath), jksPassword.toCharArray());

            // iterate over all aliases
            Enumeration<String> es = keyStore.aliases();
            String alias = Configuration.KEY_ALIAS;
            while (es.hasMoreElements()) {
                alias = (String) es.nextElement();
                // if alias refers to a private key break at that point
                // as we want to use that certificate
                if (isAliasWithPrivateKey = keyStore.isKeyEntry(alias)) {
                    break;
                }
            }

            if (isAliasWithPrivateKey) {

                KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias,
                        new KeyStore.PasswordProtection(jksPassword.toCharArray()));

                PrivateKey myPrivateKey = pkEntry.getPrivateKey();

                // Load certificate chain
                Certificate[] chain = keyStore.getCertificateChain(alias);

                certDetails = new CertificateDetails();
                certDetails.setPrivateKey(myPrivateKey);
                certDetails.setX509Certificate((X509Certificate) chain[0]);

            }

        } catch (KeyStoreException e) {
            logger.error("Error occured " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error occured " + e.getMessage());
        } catch (CertificateException e) {
            logger.error("Error occured " + e.getMessage());
        } catch (FileNotFoundException e) {
            logger.error("Error occured " + e.getMessage());
        } catch (IOException e) {
            logger.error("Error occured " + e.getMessage());
        } catch (UnrecoverableEntryException e) {
            logger.error("Error occured " + e.getMessage());
        }

        return certDetails;
    }

}
