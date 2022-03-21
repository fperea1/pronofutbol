package com.base.rest.security;

public final class ConstantesToken {

	private ConstantesToken() {}
	
	public static final String LOGIN_URL = "/login";
	
	public static final int TOKEN_EXPIRATION_TIME = 5*60*60;
	
	public static final String ISSUER_INFO = "https://www.ezentis.com/";
	
	public static final String SIGNING_KEY = "PassBaseRest2021?";
	
	public static final String HEADER_AUTHORIZACION_KEY = "authorization";
	
	public static final String TOKEN_BEARER_PREFIX = "Bearer ";
}
