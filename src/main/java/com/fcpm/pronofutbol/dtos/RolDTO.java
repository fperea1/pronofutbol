package com.fcpm.pronofutbol.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.fcpm.pronofutbol.views.View;
import com.googlecode.jmapper.annotations.JGlobalMap;

@JGlobalMap
public class RolDTO extends BaseDTO {

	@JsonView(View.Public.class)
	private Integer id;
	
	@JsonView(View.Public.class)
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
