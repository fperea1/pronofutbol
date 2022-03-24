package com.base.rest.dtos;

import javax.validation.constraints.NotBlank;

public class ContactoDTO {
	
	@NotBlank(message = "Asunto. Campo obligatorio.")
	private String asunto;
	
	@NotBlank(message = "Consulta. Campo obligatorio.")
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
