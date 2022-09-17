package com.base.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.base.rest.constant.Constantes;
import com.base.rest.entities.Log;
import com.base.rest.service.interfaces.LogService;

public class BaseController {
	
	@Autowired
    MessageSource messageSource;
	
	@Autowired
	private LogService logService;

	protected String getCurrentUserName() {
		
		return ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
	}
	
	protected String getMensajeOperacionCorrecta() {
		return messageSource.getMessage(Constantes.OPERACION_CORRECTA, null, LocaleContextHolder.getLocale());
	}
	
	protected ResponseEntity<String> responseOperationCorrecta(String entidad, String accion, String observaciones) {
		
		logService.save(new Log(getCurrentUserName(), entidad, accion, observaciones));
		return new ResponseEntity<>(getMensajeOperacionCorrecta(), HttpStatus.OK);
	}
}
