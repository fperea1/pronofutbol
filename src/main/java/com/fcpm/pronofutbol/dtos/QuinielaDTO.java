package com.fcpm.pronofutbol.dtos;

import java.util.Date;

import com.fcpm.pronofutbol.constant.Constantes;
import com.googlecode.jmapper.annotations.JGlobalMap;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JGlobalMap
public class QuinielaDTO implements BaseDTO {

	private Integer id;
	
	@NotNull(message = Constantes.VALIDATION_NUMERO_OBLIGATORIO)
	private Integer numero;
	
	@NotNull(message = Constantes.VALIDATION_NOMBRE_OBLIGATORIO)
	@Size(min = 1, max = 50, message = Constantes.VALIDATION_NOMBRE_QUINIELA_SIZE)
	private String nombre;
	
	@NotNull(message = Constantes.VALIDATION_FECHA_OBLIGATORIO)
	private Date fecha;

	@NotNull(message = Constantes.VALIDATION_ACTUALIZADA_OBLIGATORIO)
	private Boolean actualizada;
	
	@NotNull(message = Constantes.VALIDATION_LIGA_OBLIGATORIO)
	private LigaDTO liga;

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Boolean getActualizada() {
		return actualizada;
	}

	public void setActualizada(Boolean actualizada) {
		this.actualizada = actualizada;
	}

	public LigaDTO getLiga() {
		return liga;
	}

	public void setLiga(LigaDTO liga) {
		this.liga = liga;
	}

}
