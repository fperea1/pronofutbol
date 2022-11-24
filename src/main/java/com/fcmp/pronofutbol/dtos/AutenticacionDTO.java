package com.fcmp.pronofutbol.dtos;

import javax.validation.constraints.NotBlank;

import com.fcmp.pronofutbol.constant.Constantes;

public class AutenticacionDTO {
	
	@NotBlank(message = Constantes.VALIDATION_USERNAME_OBLIGATORIO)
	private String username;
	
	@NotBlank(message = Constantes.VALIDATION_PASSWORD_OBLIGATORIO)
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
