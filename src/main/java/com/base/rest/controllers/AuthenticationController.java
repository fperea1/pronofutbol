package com.base.rest.controllers;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.rest.constant.Constantes;
import com.base.rest.entities.Log;
import com.base.rest.security.TokenProvider;
import com.base.rest.service.interfaces.LogService;

@RestController
@RequestMapping(Constantes.TOKEN)
public class AuthenticationController {
	
	Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenProvider jwtTokenUtil;
	
	@Autowired
	private LogService logService;
	
	@PostMapping(value = Constantes.GENERAR_TOKEN)
	public ResponseEntity<String> generateToken(@RequestParam String username, @RequestParam String password) {
		
		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String token = jwtTokenUtil.generateToken(authentication);
		logService.save(new Log(username, Constantes.USUARIO, Constantes.LOGIN, Constantes.USUARIO + Constantes.SEPARADOR_DOS_PUNTOS + username, new Date()));
		return new ResponseEntity<>(token, HttpStatus.OK);
	}

}
