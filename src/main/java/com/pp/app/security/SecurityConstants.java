package com.pp.app.security;

import com.pp.app.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864_000_000; //10 days
    public static final long PASSWORD_EXPIRATION_TIME_RESET = 3_600_000; // 1 HOUR
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    public static final String EMAIL_VERIFICATION_PAGE = "/email-verification-page";
    public static final String EMAIL_VERIFICATION_URL = "/users/email-verification";
    public static final String PASSWORD_RESET_REQUEST_URL = "/users/password-reset-request";
    public static final String PASSWORD_RESET = "/users/password-reset";
    public static final String PASSWORD_RESET_PAGE = "/password-reset";


    public static String getTokenSecret(){
        AppProporties appProporties = (AppProporties) SpringApplicationContext.getBean("appProporties");
        return appProporties.getTokenSecret();
    }

}
