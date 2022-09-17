package com.base.rest.dtos;

import javax.validation.constraints.NotBlank;

import com.base.rest.constant.Constantes;

public class ContactoDTO {
	
	@NotBlank(message = Constantes.VALIDATION_ASUNTO_OBLIGATORIO)
	private String asunto;
	
	@NotBlank(message = Constantes.VALIDATION_CONSULTA_OBLIGATORIO)
	private String consulta;

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getConsulta() {
		return consulta;
	}

	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}

}
