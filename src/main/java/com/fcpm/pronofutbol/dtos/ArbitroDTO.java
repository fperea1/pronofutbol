package com.fcpm.pronofutbol.dtos;

import com.fcpm.pronofutbol.constant.Constantes;
import com.googlecode.jmapper.annotations.JGlobalMap;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JGlobalMap
public class ArbitroDTO implements BaseDTO {

	private Integer id;
	
	@NotNull(message = Constantes.VALIDATION_NOMBRE_OBLIGATORIO)
	@Size(min = 1, max = 100, message = Constantes.VALIDATION_NOMBRE_ARBITRO_SIZE)
	private String nombre;
	
	@NotNull(message = Constantes.VALIDATION_GANADOS_LOCAL_OBLIGATORIO)
	private Integer ganadosLocal;
	
	@NotNull(message = Constantes.VALIDATION_EMPATADOS_OBLIGATORIO)
	private Integer empatados;
	
	@NotNull(message = Constantes.VALIDATION_GANADOS_VISITANTE_OBLIGATORIO)
	private Integer ganadosVisitante;
	
	@NotNull(message = Constantes.VALIDATION_LIGA_OBLIGATORIO)
	private LigaDTO liga;

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

	public Integer getGanadosLocal() {
		return ganadosLocal;
	}

	public void setGanadosLocal(Integer ganadosLocal) {
		this.ganadosLocal = ganadosLocal;
	}

	public Integer getEmpatados() {
		return empatados;
	}

	public void setEmpatados(Integer empatados) {
		this.empatados = empatados;
	}

	public Integer getGanadosVisitante() {
		return ganadosVisitante;
	}

	public void setGanadosVisitante(Integer ganadosVisitante) {
		this.ganadosVisitante = ganadosVisitante;
	}

	public LigaDTO getLiga() {
		return liga;
	}

	public void setLiga(LigaDTO liga) {
		this.liga = liga;
	}

}
