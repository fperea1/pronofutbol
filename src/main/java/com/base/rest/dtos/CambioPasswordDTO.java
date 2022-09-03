package com.base.rest.dtos;

import javax.validation.constraints.NotBlank;

public class CambioPasswordDTO {
	
	@NotBlank(message = "Id. Campo obligatorio")
	private Integer id;
	
	@NotBlank(message = "Password Anterior. Campo obligatorio")
	private String oldPassword;
	
	@NotBlank(message = "Nueva Password. Campo obligatorio")
	private String newPassword;
	
	@NotBlank(message = "Confirmación Password. Campo obligatorio")
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
