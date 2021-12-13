package com.base.rest.dtos;

import com.googlecode.jmapper.annotations.JGlobalMap;

@JGlobalMap
public class ConfiguracionDTO {

	private Integer id;
	
	private String nombre;
	
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
