package com.base.rest.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class BaseController {

	protected String getCurrentUserName() {
		
		return ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
	}
}
