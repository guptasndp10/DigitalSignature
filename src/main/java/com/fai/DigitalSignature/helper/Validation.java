package com.fai.DigitalSignature.helper;

import com.fai.DigitalSignature.crypto.DocEncryptDecryptService;
import com.fai.DigitalSignature.exceptions.DataMismatchException;
import com.fai.DigitalSignature.model.User;
import com.fai.DigitalSignature.model.UserSignForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Validation is the validation class we'll be using to validate the user inputs
 */
public class Validation {

    static Logger LOGGER = LoggerFactory.getLogger(DocEncryptDecryptService.class);

    /**
     * validateUserForSignEncrypt validates the user input
     * @param userData json of User model
     *
     * @exception DataMismatchException
     * @return  void
     */
    public static void validateUserForSignEncrypt(User userData) throws  Exception{
        LOGGER.debug("INSIDE VALIDATOR "+userData.getUserId());
        validateString("UserId", userData.getUserId());
        validateByte("File Data", userData.getFileData());
        validateList("Counter Party User List", userData.getCounterUserList());
    }

    /**
     * validateUserForSign validates the user input
     * @param userData json of User model
     *
     * @exception DataMismatchException
     * @return  void
     */
    public static void validateUserForSign(User userData) throws  Exception{
        validateString("UserId", userData.getUserId());
        validateString("File Hash", userData.getHash());
        validateString("Signature", userData.getSignature());
        validateList("Counter Party User List", userData.getCounterUserList());
    }

    /**
     * validateUserForVerifySign validates the user input
     * @param userData json of User model
     *
     * @exception DataMismatchException
     * @return  void
     */
    public static void validateUserForVerifySign(User userData) throws  Exception{
        validateString("UserId", userData.getUserId());
        validateString("File Hash", userData.getHash());
        validateString("Signature", userData.getSignature());
    }

    /**
     * validateUserForVerifyDecrypt validates the user input
     * @param userData json of User model
     *
     * @exception DataMismatchException
     * @return  void
     */
    public static void validateUserForVerifyDecrypt(User userData) throws  Exception{
        validateString("UserId", userData.getUserId());
        validateString("File Hash", userData.getHash());
        validateString("Signature", userData.getSignature());
        validateString("Secret Key", userData.getSecretKey());
        validateString("Encrypted Data", userData.getEncryptedData());
        validateList("Counter Party User List", userData.getCounterUserList());
    }

    /**
     * validateString validates the user input
     * @param data
     * @param dataValue
     *
     * @throw DataMismatchException
     * @return  void
     */
    public static void validateString(String data, String dataValue) throws Exception{

        if (dataValue == null || dataValue.isEmpty()) {
            LOGGER.debug(data + " cannot be null or empty !");
            throw new DataMismatchException(data + " cannot be null or empty !");
        }

    }

    /**
     * validateByte validates the user input
     * @param data
     * @param dataValue
     *
     * @throw  DataMismatchException
     * @return  void
     */
    public static void validateByte(String data, byte[] dataValue) throws  Exception{

        if (dataValue == null || dataValue.length <= 0) {
            LOGGER.debug(data+" cannot be null or empty !");
            throw new DataMismatchException(data+" cannot be null or empty !");
        }

    }

    /**
     * validateList validates the user input
     * @param data
     * @param dataValue
     *
     * @throw  DataMismatchException
     * @return  void
     */
    public static void validateList(String data, List<String> dataValue) throws  Exception {

        if (dataValue == null || dataValue.size() <= 0 ) {
            LOGGER.debug(data + " cannot be null or empty !");
            throw new DataMismatchException(data + " cannot be null or empty !");
        }
        else if (dataValue.size() >0){

            for(String counterpart:dataValue){
                validateString("counter party",counterpart);
            }

        }


    }

}
