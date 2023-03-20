package com.fcpm.pronofutbol.dtos;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.enums.ResultadoEnum;
import com.googlecode.jmapper.annotations.JGlobalMap;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JGlobalMap
public class EquipoDTO implements BaseDTO {

	private Integer id;

	@NotNull(message = Constantes.VALIDATION_NOMBRE_OBLIGATORIO)
	@Size(min = 1, max = 100, message = Constantes.VALIDATION_NOMBRE_EQUIPO_SIZE)
	private String nombre;

	@NotNull(message = Constantes.VALIDATION_PUNTOS_OBLIGATORIO)
	@Min(value = 0)
	private Integer puntos;

	@NotNull(message = Constantes.VALIDATION_GANADOS_LOCAL_OBLIGATORIO)
	@Min(value = 0)
	private Integer ganadosLocal;

	@NotNull(message = Constantes.VALIDATION_EMPATADOS_LOCAL_OBLIGATORIO)
	@Min(value = 0)
	private Integer empatadosLocal;

	@NotNull(message = Constantes.VALIDATION_PERDIDOS_LOCAL_OBLIGATORIO)
	@Min(value = 0)
	private Integer perdidosLocal;

	@NotNull(message = Constantes.VALIDATION_GANADOS_VISITANTE_OBLIGATORIO)
	@Min(value = 0)
	private Integer ganadosVisitante;

	@NotNull(message = Constantes.VALIDATION_EMPATADOS_VISITANTE_OBLIGATORIO)
	@Min(value = 0)
	private Integer empatadosVisitante;

	@NotNull(message = Constantes.VALIDATION_PERDIDOS_VISITANTE_OBLIGATORIO)
	@Min(value = 0)
	private Integer perdidosVisitante;

	private ResultadoEnum resUltimoPartido;

	private ResultadoEnum resPenultimoPartido;

	@NotNull(message = Constantes.VALIDATION_LIGA_OBLIGATORIO)
	private LigaDTO liga;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getPuntos() {
		return puntos;
	}

	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
	}

	public Integer getGanadosLocal() {
		return ganadosLocal;
	}

	public void setGanadosLocal(Integer ganadosLocal) {
		this.ganadosLocal = ganadosLocal;
	}

	public Integer getEmpatadosLocal() {
		return empatadosLocal;
	}

	public void setEmpatadosLocal(Integer empatadosLocal) {
		this.empatadosLocal = empatadosLocal;
	}

	public Integer getPerdidosLocal() {
		return perdidosLocal;
	}

	public void setPerdidosLocal(Integer perdidosLocal) {
		this.perdidosLocal = perdidosLocal;
	}

	public Integer getGanadosVisitante() {
		return ganadosVisitante;
	}

	public void setGanadosVisitante(Integer ganadosVisitante) {
		this.ganadosVisitante = ganadosVisitante;
	}

	public Integer getEmpatadosVisitante() {
		return empatadosVisitante;
	}

	public void setEmpatadosVisitante(Integer empatadosVisitante) {
		this.empatadosVisitante = empatadosVisitante;
	}

	public Integer getPerdidosVisitante() {
		return perdidosVisitante;
	}

	public void setPerdidosVisitante(Integer perdidosVisitante) {
		this.perdidosVisitante = perdidosVisitante;
	}

	public ResultadoEnum getResUltimoPartido() {
		return resUltimoPartido;
	}

	public void setResUltimoPartido(ResultadoEnum resUltimoPartido) {
		this.resUltimoPartido = resUltimoPartido;
	}

	public ResultadoEnum getResPenultimoPartido() {
		return resPenultimoPartido;
	}

	public void setResPenultimoPartido(ResultadoEnum resPenultimoPartido) {
		this.resPenultimoPartido = resPenultimoPartido;
	}

	public LigaDTO getLiga() {
		return liga;
	}

	public void setLiga(LigaDTO liga) {
		this.liga = liga;
	}

}
