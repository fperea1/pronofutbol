package com.fcpm.pronofutbol.dtos;

import com.fcpm.pronofutbol.constant.Constantes;
import com.googlecode.jmapper.annotations.JGlobalMap;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JGlobalMap
public class ConfiguracionDTO extends BaseDTO {

	private Integer id;
	
	@Size(min = 1, max = 50, message = Constantes.VALIDATION_NOMBRE_CONFIG_SIZE)
	@NotNull(message = Constantes.VALIDATION_NOMBRE_OBLIGATORIO)
	private String nombre;
	
	@Size(min = 1, max = 500, message = Constantes.VALIDATION_VALOR_CONFIG_SIZE)
	@NotNull(message = Constantes.VALIDATION_VALOR_OBLIGATORIO)
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
