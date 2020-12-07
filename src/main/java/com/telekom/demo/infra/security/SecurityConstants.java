package com.telekom.demo.infra.security;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityConstants {

    public final String SECRET = "some_secret_key";
    public final long EXPIRATION_TIME = 423_000_000; // 5 days
    public final String TOKEN_PREFIX = "Bearer ";
}
