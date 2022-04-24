package com.pp.app.security;

import com.pp.app.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864_000_000; //10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";

    public static String getTokenSecret(){
        AppProporties appProporties = (AppProporties) SpringApplicationContext.getBean("appProporties");
        return appProporties.getTokenSecret();
    }

}
