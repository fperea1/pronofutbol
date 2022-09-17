package com.base.rest.dtos;

import javax.validation.constraints.NotBlank;

import com.base.rest.constant.Constantes;

public class CambioPasswordDTO {
	
	@NotBlank(message = Constantes.VALIDATION_ID_OBLIGATORIO)
	private Integer id;
	
	@NotBlank(message = Constantes.VALIDATION_PASSWORD_ANTERIOR_OBLIGATORIO)
	private String oldPassword;
	
	@NotBlank(message = Constantes.VALIDATION_NUEVA_PASSWORD_OBLIGATORIO)
	private String newPassword;
	
	@NotBlank(message = Constantes.VALIDATION_CONFIRMACION_PASSWORD_OBLIGATORIO)
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
