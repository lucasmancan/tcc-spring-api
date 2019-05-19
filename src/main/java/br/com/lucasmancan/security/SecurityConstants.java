package br.com.lucasmancan.security;

public final class SecurityConstants {

    public static final String AUTH_LOGIN_URL = "/api/auth";

    // Signing key for HS512 algorithm
    // You can use the page http://www.allkeysgenerator.com/ to generate all kinds of keys
    public static final String JWT_SECRET = "r5u8x/A?D(G+KbPeShVmYq3t6v9y$B&E)H@McQfTjWnZr4u7x!z%C*F-JaNdRgUk\n";

    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "vendas-tcc-api-jwt-rocks";
    public static final String TOKEN_AUDIENCE = "secure-app";
    public static final int TOKEN_EXPIRATION = 860_000_000;

}