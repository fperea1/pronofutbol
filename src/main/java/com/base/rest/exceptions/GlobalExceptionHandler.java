package com.base.rest.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.base.rest.constant.Constantes;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	   Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//	   @ExceptionHandler(TransactionSystemException.class)
//	   protected ResponseEntity<List<String>> handleTransactionException(TransactionSystemException ex) throws Throwable {
//	       Throwable cause = ex.getCause();
//	       if (!(cause instanceof RollbackException)) throw cause;
//	       if (!(cause.getCause() instanceof ConstraintViolationException)) throw cause.getCause();
//	       ConstraintViolationException validationException = (ConstraintViolationException) cause.getCause();
//	       List<String> messages = validationException.getConstraintViolations().stream()
//	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
//	       return new ResponseEntity<>(messages, HttpStatus.CONFLICT);
//	   }
	   
	   @ExceptionHandler(ServiceException.class)
	   protected ResponseEntity<String> handleServiceExceptionn(ServiceException ex) {
	      
		   logger.error(ex.getMessage(), ex);
	       return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	   }
	   
	   @ExceptionHandler(ConstraintViolationException.class)
	   protected ResponseEntity<List<String>> handleConstraintViolationException(ConstraintViolationException ex) {
	      
		   logger.error(ex.getMessage(), ex);
		   List<String> messages = ex.getConstraintViolations().stream()
	               .map(ConstraintViolation::getMessage).collect(Collectors.toList());
	       return new ResponseEntity<>(messages, HttpStatus.CONFLICT);
	   }
	   
	   @ExceptionHandler(MethodArgumentNotValidException.class)
	   protected ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		      
		   logger.error(ex.getMessage(), ex);
		   List<String> messages = new ArrayList<String>();
		   ex.getBindingResult().getAllErrors()
				      .stream()
				      .filter(FieldError.class::isInstance)
				      .map(FieldError.class::cast)
				      .forEach(fieldError -> messages.add(fieldError.getDefaultMessage()));
	       return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
	   }
	 
	   @ExceptionHandler(DataIntegrityViolationException.class)
	   protected ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
	      
		   logger.error(ex.getMessage(), ex);
	       return new ResponseEntity<>((ex.getRootCause() != null && ex.getRootCause().getMessage() != null) 
	    		   ? ex.getRootCause().getMessage() : Constantes.EXC_INTEGRIDAD_DATOS, HttpStatus.CONFLICT);
	   }
	   
	   @ExceptionHandler(MissingRequestHeaderException.class)
	   protected ResponseEntity<String> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
	      
		   logger.error(ex.getMessage(), ex);
	       return new ResponseEntity<>(Constantes.EXC_FALTA_PARAM_HEADER, HttpStatus.BAD_REQUEST);
	   }
	 
	   @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	   protected ResponseEntity<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
	      
		   logger.error(ex.getMessage(), ex);
	       return new ResponseEntity<>(Constantes.EXC_METODO_NO_SOPORTADO, HttpStatus.BAD_REQUEST);
	   }
	   
	   @ExceptionHandler(MissingServletRequestParameterException.class)
	   protected ResponseEntity<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
	      
		   logger.error(ex.getMessage(), ex);
	       return new ResponseEntity<>(Constantes.EXC_FALTAN_PARAM_PETICION, HttpStatus.BAD_REQUEST);
	   }
	   
//	   @ExceptionHandler(BadCredentialsException.class)
//	   protected ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
//		      
//		   logger.error(ex.getMessage(), ex);
//	       return new ResponseEntity<>(Constantes.EXC_CREDENCIALES_ERRONEAS, HttpStatus.BAD_REQUEST);
//	   }
	   
	   @ExceptionHandler(AuthenticationException.class)
	   protected ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
	      
		   logger.error(ex.getMessage(), ex);
	       return new ResponseEntity<>(Constantes.EXC_AUTH_ERRONEA, HttpStatus.BAD_REQUEST);
	   }
	   
	   @ExceptionHandler(MailAuthenticationException.class)
	   protected ResponseEntity<String> handleMailAuthenticationException(MailAuthenticationException ex) {
		      
		   logger.error(ex.getMessage(), ex);
	       return new ResponseEntity<>(Constantes.EXC_CREDENCIALES_ERRONEAS, HttpStatus.BAD_REQUEST);
	   }
	   
	   @ExceptionHandler(JsonException.class)
	   protected ResponseEntity<String> handleJsonException(JsonException ex) {
		      
		   logger.error(ex.getMessage(), ex);
	       return new ResponseEntity<>(Constantes.EXC_JSON_PARSE, HttpStatus.BAD_REQUEST);
	   }
	   
	   @ExceptionHandler(POIException.class)
	   protected ResponseEntity<String> handlePOIException(POIException ex) {
		      
		   logger.error(ex.getMessage(), ex);
	       return new ResponseEntity<>(Constantes.EXC_REPORT_POI, HttpStatus.BAD_REQUEST);
	   }
	   
}
