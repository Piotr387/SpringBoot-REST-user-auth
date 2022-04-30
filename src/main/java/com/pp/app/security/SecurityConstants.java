package com.pp.app.security;

import com.pp.app.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864_000_000; //10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    public static final String EMAIL_VERIFICATION_PAGE = "/email-verification-page";
    public static final String EMAIL_VERIFICATION_URL = "/users/email-verification";
    public static final String EMAIL_HOST = "localhost";
    public static final String EMAIL_PORT = "25";


    public static String getTokenSecret(){
        AppProporties appProporties = (AppProporties) SpringApplicationContext.getBean("appProporties");
        return appProporties.getTokenSecret();
    }

}
