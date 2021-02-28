package com.myweb.instaweb.security;




/*
 * @author
 * @version
 * @return
 */


public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/auth/**";


    public static final String SECRET = "SecretKeyGenJWT";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "application/json";

    /** Session time 10 min*/
    public static final long EXPIRATION_TIME = 600_000;

}
