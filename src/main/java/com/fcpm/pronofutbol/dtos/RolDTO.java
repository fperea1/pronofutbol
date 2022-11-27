package com.fcpm.pronofutbol.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.fcpm.pronofutbol.views.View;
import com.googlecode.jmapper.annotations.JGlobalMap;

@JGlobalMap
public class RolDTO extends BaseDTO {

	@JsonView(View.Public.class)
	private int id;
	
	@JsonView(View.Public.class)
	private String nombre;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
