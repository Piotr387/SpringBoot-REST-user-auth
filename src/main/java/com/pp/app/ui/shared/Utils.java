package com.pp.app.ui.shared;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Utils {

    private final Random random = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * This function accept a arument which is goint to be an integer value of
     * how long we want the public user id to be, we can generate a user ID, 10 characters long
     * or 15 character long etc.
     * @param length
     * @return
     */
    public String generateUserId(int length){
        return generateRandomString(length);
    }

    public String generateAddressId(int length){
        return generateRandomString(length);
    }

    /**
     * function simply generates a string of random characters of a given length
     * which is using declared alphabet as a source of characters, it will randomly pick up
     * a character from this alphabet string for a given number of times, like 15 etc.
     * @param length
     * @return
     */
    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length;i++){
            returnValue.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }




}
