package com.example.demo.security;

/**
 * Class pour appliquer des propriétés au JSON Web Token
 */

public class JwtProperties {
    public static final String SECRET = "AG7";
    public static final int EXPIRATION_TIME = 864000000; // 10 jours
    public static final String PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
}
