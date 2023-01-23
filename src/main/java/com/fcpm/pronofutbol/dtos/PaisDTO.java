package com.fcpm.pronofutbol.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.views.View;
import com.googlecode.jmapper.annotations.JGlobalMap;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JGlobalMap
public class PaisDTO extends BaseDTO {

	@JsonView(View.Public.class)
	private Integer id;
	
	@JsonView(View.Public.class)
	@NotNull(message = Constantes.VALIDATION_NOMBRE_OBLIGATORIO)
	@Size(min = 1, max = 50, message = Constantes.VALIDATION_NOMBRE_PAIS_SIZE)
	private String nombre;

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

}
