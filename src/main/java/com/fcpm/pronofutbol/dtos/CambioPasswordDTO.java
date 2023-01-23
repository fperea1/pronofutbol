package com.fcpm.pronofutbol.dtos;

import com.fcpm.pronofutbol.constant.Constantes;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CambioPasswordDTO {
	
	private Integer id;
	
	private String oldPassword;
	
	@NotNull(message = Constantes.VALIDATION_NUEVA_PASSWORD_OBLIGATORIO)
	@Size(min = 10, max = 100, message = Constantes.VALIDATION_PASSWORD_USUARIO_SIZE)
	private String newPassword;
	
	@NotNull(message = Constantes.VALIDATION_CONFIRMACION_PASSWORD_OBLIGATORIO)
	@Size(min = 10, max = 100, message = Constantes.VALIDATION_PASSWORD_USUARIO_SIZE)
	private String newPassword2;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPassword2() {
		return newPassword2;
	}

	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}

}
