package com.fcpm.pronofutbol.security;

public final class ConstantesToken {

	private ConstantesToken() {}
	
	public static final String LOGIN_URL = "/login";
	
	public static final int TOKEN_EXPIRATION_TIME = 5*60*60;
	
	public static final String ISSUER_INFO = "https://www.pronofutbol.com/";
	
	public static final String SIGNING_KEY = "PassPronofutbol3000?";
	
	public static final String HEADER_AUTHORIZACION_KEY = "authorization";
	
	public static final String TOKEN_BEARER_PREFIX = "Bearer ";
}
