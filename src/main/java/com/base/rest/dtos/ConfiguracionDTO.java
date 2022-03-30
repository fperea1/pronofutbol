package com.base.rest.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.googlecode.jmapper.annotations.JGlobalMap;

@JGlobalMap
public class ConfiguracionDTO {

	private Integer id;
	
	@Size(min = 1, max = 50, message = "Nombre debe tener entre 1 y 50 caracteres")
	@NotBlank(message = "Nombre. Campo obligatorio.")
	private String nombre;
	
	@Size(min = 1, max = 500, message = "Valor debe tener entre 1 y 500 caracteres")
	@NotBlank(message = "Valor. Campo obligatorio")
	private String valor;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
}
