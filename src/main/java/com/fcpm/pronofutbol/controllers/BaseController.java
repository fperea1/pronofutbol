package com.fcpm.pronofutbol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.entities.Log;
import com.fcpm.pronofutbol.service.interfaces.LogService;
import com.fcpm.pronofutbol.utils.I18nUtils;

public class BaseController {
	
	@Autowired
	private LogService logService;

	protected String getCurrentUserName() {
		
		return ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
	}
	
	protected ResponseEntity<String> responseOperationCorrecta(String entidad, String accion, String observaciones) {
		
		logService.save(new Log(getCurrentUserName(), entidad, accion, observaciones));
		return new ResponseEntity<>(I18nUtils.getMensaje(Constantes.OPERACION_CORRECTA), HttpStatus.OK);
	}
}
