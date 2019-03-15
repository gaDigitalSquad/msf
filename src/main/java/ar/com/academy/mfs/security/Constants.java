package ar.com.academy.mfs.security;

public class Constants {

	// Spring Security

	public static final String LOGIN_URL = "/login";
	public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
	public static final String TOKEN_BEARER_PREFIX = "Bearer ";

	// JWT

	public static final String ISSUER_INFO = "https://www.autentia.com/";
	public static final String SUPER_SECRET_KEY = "1234";
	// public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day
	public static long TOKEN_EXPIRATION_TIME = 1000*60*60*24*10; // 10 days


	
}