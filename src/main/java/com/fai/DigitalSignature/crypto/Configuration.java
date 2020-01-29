package com.fai.DigitalSignature.crypto;

/**
 * Crypto configurations
 */
public class Configuration {

    //    static SignatureScheme SIGNATURE_SCHEME =  Crypto.ECDSA_SECP256K1_SHA256;
    public static final String HASHING_ALGO = "SHA-256";
    public static final String KEYGEN_ALGO = "RSA";
    public static final String SECRET_KEYGEN_ALGO = "AES";
    public static final String SYMMETRIC_ALGO = "AES/CBC/PKCS5Padding";
    public static final String ASYMMETRIC_ALGO = "RSA/ECB/PKCS1Padding";
    public static final int KEY_SIZE = 2048;
    public static final String KEYGEN_PATH = "artifacts/";
    public static final String KEYSTORE_TYPE = "JKS";
    public static final String KEYSTORE_EXTENSION = ".jks";
    public static final String KEYSTORE_PASSWORD = "nopass";
    public static final String SIG_ALGORITHM = "SHA256withRSA";
    public static final String FILE_PATH = "resource/files/data.txt";
    public static final String ENCRYPTEDFILE_PATH = "resource/encrypted-files/";
    public static final String SIG_PROVIDER = "BC";
    public static final String KEY_ALIAS = "infy";
    public static final String CERT_ALIAS = "cert";
    public static final String KEYSTORE_ALIAS = "keystore.jks";
    public static final String ZIP_ALIAS = "certificates.zip";
    public static final String CERTIFICATE_ALIAS = "x509certificate.pem";
}