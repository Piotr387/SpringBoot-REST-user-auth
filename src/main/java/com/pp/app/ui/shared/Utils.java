package com.pp.app.ui.shared;

import com.pp.app.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Date;
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

    public static boolean hasTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstants.getTokenSecret())
                    .parseClaimsJws( token )
                    .getBody();
            Date tokenExpirationDate = claims.getExpiration();
            Date todayDate = new Date();

            return tokenExpirationDate.before(todayDate);
        } catch (SignatureException e){
            System.out.println(e);
        }

        return false;
    }

    public String generateEmailVerificationToken(String publicUserId) {
        return generateToken(publicUserId, SecurityConstants.EXPIRATION_TIME);
    }

    public String generatePasswordResetToken(String userId) {
        return generateToken(userId, SecurityConstants.PASSWORD_EXPIRATION_TIME_RESET);
    }

    public String generateToken(String publicUserId, long expirationTime){
        return Jwts.builder()
                .setSubject(publicUserId)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact();
    }
}
