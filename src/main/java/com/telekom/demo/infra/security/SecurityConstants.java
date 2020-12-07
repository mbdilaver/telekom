package com.telekom.demo.infra.security;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityConstants {

    public final String SECRET = "xrN2wrFwZr"; //todo config'ten alınacak
    public final long EXPIRATION_TIME = 423_000_000; // 5 gün
    public final String TOKEN_PREFIX = "Bearer ";
}
