package com.base.rest.security;

public final class Constantes {

	private Constantes() {}
	
	public final static String LOGIN_URL = "/login";
	
	public final static int TOKEN_EXPIRATION_TIME = 5*60*60;
	
	public final static String ISSUER_INFO = "https://www.ezentis.com/";
	
	public final static String SIGNING_KEY = "PassBaseRest2021?";
	
	public final static String HEADER_AUTHORIZACION_KEY = "Authorization";
	
	public final static String TOKEN_BEARER_PREFIX = "Bearer ";
}
