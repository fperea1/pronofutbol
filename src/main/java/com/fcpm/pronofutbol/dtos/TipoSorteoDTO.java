package com.fcpm.pronofutbol.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.views.View;
import com.googlecode.jmapper.annotations.JGlobalMap;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JGlobalMap
public class TipoSorteoDTO extends BaseDTO {

	@JsonView(View.Public.class)
	private Integer id;
	
	@JsonView(View.Public.class)
	@NotNull(message = Constantes.VALIDATION_NOMBRE_OBLIGATORIO)
	@Size(min = 1, max = 50, message = Constantes.VALIDATION_NOMBRE_TIPO_SORTEO_SIZE)
	private String nombre;
	
	@JsonView(View.Public.class)
	@NotNull(message = Constantes.VALIDATION_NUMERO_DOBLES_OBLIGATORIO)
	private Integer numDobles;
	
	@JsonView(View.Public.class)
	@NotNull(message = Constantes.VALIDATION_NUMERO_TRIPLES_OBLIGATORIO)
	private Integer numTriples;

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

	public Integer getNumDobles() {
		return numDobles;
	}

	public void setNumDobles(Integer numDobles) {
		this.numDobles = numDobles;
	}

	public Integer getNumTriples() {
		return numTriples;
	}

	public void setNumTriples(Integer numTriples) {
		this.numTriples = numTriples;
	}

}
