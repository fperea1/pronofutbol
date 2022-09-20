package com.base.rest.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.base.rest.constant.Constantes;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	@Autowired
    MessageSource messageSource;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, messageSource.getMessage(Constantes.NO_AUTH, null, LocaleContextHolder.getLocale()));
		
	}

}
