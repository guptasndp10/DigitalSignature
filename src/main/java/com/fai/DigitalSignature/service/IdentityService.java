package com.fai.DigitalSignature.service;

import com.fai.DigitalSignature.crypto.DocEncryptDecryptService;
import com.fai.DigitalSignature.crypto.DocSigningService;
import com.fai.DigitalSignature.crypto.ValidateDocData;
import com.fai.DigitalSignature.exceptions.DecryptionFailed;
import com.fai.DigitalSignature.exceptions.SignatureNotRecognized;
import com.fai.DigitalSignature.helper.FileManager;
import com.fai.DigitalSignature.helper.Validation;
import com.fai.DigitalSignature.model.User;
import com.fai.DigitalSignature.model.SignEncryptDetails;
import com.fai.DigitalSignature.model.EncryptionDetails;
import com.fai.DigitalSignature.model.FileDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

/**
 * IdentityService is the main service we'll be using to handle crypto operations
 */
@Component
public class IdentityService {

    Logger LOGGER = LoggerFactory.getLogger(IdentityService.class);

    @Autowired
    DocSigningService docSigningService;

    @Autowired
    DocEncryptDecryptService docEncryptDecryptService;

    /**
     * handleDocSignAndEncrypt takes http request with user model as input
     * @input json of User model
     *
     * @return  ServerResponse contains SignEncryptDetails object with Success message and status 200
     */
    public Mono<ServerResponse> handleDocSignAndEncrypt(ServerRequest request) {
        long startTime = System.currentTimeMillis();
        Mono<User> userObj = request.bodyToMono(User.class);
        return userObj.flatMap(userData -> {
            SignEncryptDetails signEncrypt = null;
            LOGGER.debug("USER DATA RECEVIED WITH USER ID AS ",userData.getUserId());
            try{
                Validation.validateUserForSignEncrypt(userData);
                LOGGER.debug("USER DATA IS VALIDATED USING VALIDATOR");
                String signature;
                List<EncryptionDetails> encryptList = new ArrayList<>();
                byte[] docHash = FileManager.calculateHash(userData.getFileData());
                signature = docSigningService.signDocument(userData.getUserId(), docHash);
                LOGGER.debug("DOC IS SIGNED BY THE USER WITH USERID AS"+userData.getUserId());
                signEncrypt = docEncryptDecryptService.encryptDocument(userData.getUserId(), userData.getCounterUserList(), userData.getFileData(), FileManager.encodeByteToString(docHash));
                LOGGER.debug("DOC IS ENCRYPTED WITH USER AND COUNTERUSER");
                signEncrypt.setUserId(userData.getUserId());
                signEncrypt.setSignature(signature);
                long endTime =System.currentTimeMillis();
                LOGGER.debug("DOC IS SIGNED AND ENCRYPTED IN "+((double)(endTime-startTime)/1000)+" SECONDS");
                return ServerResponse.status(200).contentType(MediaType.APPLICATION_JSON).syncBody(signEncrypt);
            }catch (Exception e) {
                long endTime =System.currentTimeMillis();
                LOGGER.debug("REQUEST FAILED IN "+((double)(endTime-startTime)/1000)+" SECONDS");
                return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromObject(e.getMessage()));
            }
        });
    }

    /**
     * handleVerifySigDecryptDocIntegrityCheck takes http request with user model as input
     * @input json of User model
     *
     * @return  ServerResponse contains FileDetails object with Success message and status 200
     */
    public Mono<ServerResponse> handleVerifySigDecryptDocIntegrityCheck(ServerRequest request) {
        long startTime = System.currentTimeMillis();
        Mono<User> userObj = request.bodyToMono(User.class);
        return userObj.flatMap(userData -> {
            LOGGER.debug("USER DATA RECEVIED WITH USER ID AS ",userData.getUserId());
            try{
                FileDetails fileDetails = null;
                Validation.validateUserForVerifyDecrypt(userData);
                LOGGER.debug("USER DATA IS VALIDATE USING VALIDATOR");
                fileDetails = verifySigAndDecrypt(userData);
                if(fileDetails != null){
                    //verify document Integrity.
                    boolean verifiedFileData = ValidateDocData.verifyFileContent(fileDetails.getFileData(), FileManager.decodeStringToByte(userData.getHash()));
                    fileDetails.setFileDataIntegrity(verifiedFileData);
                }
                long endTime =System.currentTimeMillis();
                LOGGER.debug("SIGN VERIFIED DECRYPTED AND DOC INTEGRITY CHECK IN "+((double)(endTime-startTime)/1000)+" SECONDS");
                return ServerResponse.status(200).contentType(MediaType.APPLICATION_JSON).syncBody(fileDetails);
            } catch (Exception e) {
                long endTime =System.currentTimeMillis();
                LOGGER.debug("REQUEST FAILED IN "+((double)(endTime-startTime)/1000)+" SECONDS");
                return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromObject(e.getMessage()));
            }
        });
    }

    /**
     * handleVerifySigAndDecrypt takes http request with user model as input
     * @input json of User model
     *
     * @return  ServerResponse contains FileDetails object with Success message and status 200
     */
    public Mono<ServerResponse> handleVerifySigAndDecrypt(ServerRequest request) {
        long startTime = System.currentTimeMillis();
        Mono<User> userObj = request.bodyToMono(User.class);
        return userObj.flatMap(userData -> {
            LOGGER.debug("USER DATA RECEVIED WITH USER ID AS ",userData.getUserId());
            try{
                FileDetails fileDetails = null;
                Validation.validateUserForVerifyDecrypt(userData);
                LOGGER.debug("USER DATA IS VALIDATE USING VALIDATOR");
                fileDetails = verifySigAndDecrypt(userData);
                if(!(fileDetails == null)) {
                    fileDetails.setFileDataIntegrity(true);
                } else{
                    throw new DecryptionFailed("Error While Decrypting");
                }
                long endTime =System.currentTimeMillis();
                LOGGER.debug("SIGN VERIFIED AND DECRYPTED IN "+((double)(endTime-startTime)/1000)+" SECONDS");
                return ServerResponse.status(200).contentType(MediaType.APPLICATION_JSON).syncBody(fileDetails);
            } catch (DecryptionFailed decryptionFailed) {
                decryptionFailed.printStackTrace();
                long endTime =System.currentTimeMillis();
                LOGGER.debug("REQUEST FAILED IN "+((double)(endTime-startTime)/1000)+" SECONDS");
                return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromObject(decryptionFailed.getMessage()));
            } catch (Exception e) {
                long endTime =System.currentTimeMillis();
                LOGGER.debug("REQUEST FAILED IN "+((double)(endTime-startTime)/1000)+" SECONDS");
                return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromObject(e.getMessage()));
            }
        });
    }

    /**
     * handleDocSign takes http request with user model as input
     * @input json of User model
     *
     * @return  ServerResponse containing signatures on the document with Success message and status 200
     */
    public Mono<ServerResponse> handleDocSign(ServerRequest request) {
        long startTime = System.currentTimeMillis();
        Mono<User> userObj = request.bodyToMono(User.class);
        return userObj.flatMap(userData -> {
            SignEncryptDetails signEncrypt = null;
            LOGGER.debug("USER DATA RECEVIED WITH USER ID AS ",userData.getUserId());
            try{
                Validation.validateUserForSign(userData);
                LOGGER.debug("USER DATA IS VALIDATE USING VALIDATOR");
                boolean signVerified = docSigningService.verifySignature(FileManager.decodeStringToByte(userData.getHash()), FileManager.decodeStringToByte(userData.getSignature()), userData.getUserId());
                LOGGER.debug("INITIATOR SIGNATURE IS VERIFIED "+signVerified);
                String signature;
                signature = docSigningService.signDocument(userData.getCounterUserList().get(0), FileManager.decodeStringToByte(userData.getHash()));
                LOGGER.debug("DOC IS SIGNED WITH USERID "+userData.getUserId());
                long endTime =System.currentTimeMillis();
                LOGGER.debug("DOC IS SIGNED IN "+((double)(endTime-startTime)/1000)+" SECONDS");
                return ServerResponse.status(200).contentType(MediaType.TEXT_PLAIN).syncBody(signature);
            }catch (Exception e) {
//                LOGGER.error("Error occured " + e.getMessage());
                long endTime =System.currentTimeMillis();
                LOGGER.debug("REQUEST FAILED IN "+((double)(endTime-startTime)/1000)+" SECONDS");
                return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                        .body(BodyInserters.fromObject(e.getMessage()));
            }
        });
    }

    /**
     * handleVerifySig takes http request with user model as input
     * @input json of User model
     *
     * @return  ServerResponse containing flag for verifying signatures on the document with Success message and status 200
     */
    public Mono<ServerResponse> handleVerifySig(ServerRequest request) {
        long startTime = System.currentTimeMillis();
        Mono<User> userObj = request.bodyToMono(User.class);
        return userObj.flatMap(userData -> {
            LOGGER.debug("USER DATA RECEVIED WITH USER ID AS ",userData.getUserId());
            try{
                Validation.validateUserForVerifySign(userData);
                LOGGER.debug("USER DATA IS VALIDATE USING VALIDATOR");
                boolean signVerified = docSigningService.verifySignature(FileManager.decodeStringToByte(userData.getHash()), FileManager.decodeStringToByte(userData.getSignature()), userData.getUserId());
                LOGGER.debug("SIGNATURE IS VERIFIED "+signVerified);
                long endTime =System.currentTimeMillis();
                LOGGER.debug("SIGN VERIFIED IN "+((double)(endTime-startTime)/1000)+" SECONDS");
                return ServerResponse.status(200).contentType(MediaType.APPLICATION_JSON).syncBody(signVerified);
            } catch (Exception e) {
                long endTime =System.currentTimeMillis();
                LOGGER.debug("REQUEST FAILED IN "+((double)(endTime-startTime)/1000)+" SECONDS");
                return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromObject(e.getMessage()));
            }
        });
    }

    /**
     * handleVerifySig takes user model as input
     * @param userData json User model
     *
     * @return  FileDetails object
     */
    private FileDetails verifySigAndDecrypt(User userData){
        FileDetails fileDetails = null;
        try {
            boolean signVerified = docSigningService.verifySignature(FileManager.decodeStringToByte(userData.getHash()), FileManager.decodeStringToByte(userData.getSignature()), userData.getUserId());
            LOGGER.debug("SIGN IS VERIFIED " + signVerified);
            if (signVerified) {
                byte[] decryptedData = docEncryptDecryptService.documentDecrypt(userData.getCounterUserList().get(0), FileManager.decodeStringToByte(userData.getSecretKey()), FileManager.decodeStringToByte(userData.getEncryptedData()));
                LOGGER.debug("DECRYPTED DATA IS " + FileManager.encodeByteToString(decryptedData));
                if(!(decryptedData == null)) {
                    fileDetails = new FileDetails(decryptedData);
                } else{
                    throw new DecryptionFailed("Error While Decrypting");
                }
            } else {
                throw new SignatureNotRecognized("Signature Not Recognized");
            }
        } catch (Exception e) {
            LOGGER.error("Error occured " + e.getMessage());
        }
        return fileDetails;
    }

}
