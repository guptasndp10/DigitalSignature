package com.fai.DigitalSignature.crypto;

import com.fai.DigitalSignature.helper.FileManager;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * ValidateDocData is the class will be using to verify the Document Integrity
 */
@Component
public class ValidateDocData {

    /**
     * verifyFileContent verifies the document integrity
     * @param data
     * @param actualHash
     *
     * @return  boolean flag
     */
    public static boolean verifyFileContent(byte[] data, byte[] actualHash){
        System.out.println("actualHash is "+ new String(actualHash));
        System.out.println("data is "+ new String(data));
        byte[] calculatedHash = FileManager.calculateHash(data);
        System.out.println("calculatedHash is "+ new String(calculatedHash));
        if(!Arrays.equals(calculatedHash,actualHash)){
            return false;
        }
        return true;
    }

}
