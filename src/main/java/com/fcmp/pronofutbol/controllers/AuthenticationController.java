package com.fcmp.pronofutbol.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fcmp.pronofutbol.constant.Constantes;
import com.fcmp.pronofutbol.dtos.AutenticacionDTO;
import com.fcmp.pronofutbol.entities.Log;
import com.fcmp.pronofutbol.security.TokenProvider;
import com.fcmp.pronofutbol.service.interfaces.LogService;
import com.fcmp.pronofutbol.utils.I18nUtils;

@RestController
@RequestMapping(Constantes.AUTENTICATION)
public class AuthenticationController extends BaseController {
	
	Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenProvider jwtTokenUtil;
	
	@Autowired
	private LogService logService;
	
	@PostMapping(value = Constantes.GENERAR_TOKEN)
	public ResponseEntity<String> generateToken(@Valid @RequestBody AutenticacionDTO autenticacion) {
		
		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(autenticacion.getUsername(), autenticacion.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String token = jwtTokenUtil.generateToken(authentication);
		logService.save(new Log(autenticacion.getUsername(), Constantes.USUARIO, Constantes.LOGIN, Constantes.USUARIO + Constantes.SEPARADOR_DOS_PUNTOS + autenticacion.getUsername()));
		return new ResponseEntity<>(token, HttpStatus.OK);
	}
	
	@GetMapping(value = Constantes.LOGOUT)
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logService.save(new Log(getCurrentUserName(), Constantes.USUARIO, Constantes.LOGOUT_LOG, Constantes.LOGOUT_LOG + Constantes.SEPARADOR_DOS_PUNTOS + getCurrentUserName()));
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return new ResponseEntity<>(I18nUtils.getMensaje(Constantes.OPERACION_CORRECTA), HttpStatus.OK);
	}

}
