package com.fcpm.pronofutbol.entities;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.enums.ResultadoEnum;
import com.googlecode.jmapper.annotations.JGlobalMap;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "equipos")
@JGlobalMap
public class Equipo extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "nombre", unique = true)
	@NotNull(message = Constantes.VALIDATION_NOMBRE_OBLIGATORIO)
	@Size(min = 1, max = 100, message = Constantes.VALIDATION_NOMBRE_EQUIPO_SIZE)
	private String nombre;

	@Column(name = "puntos")
	@NotNull(message = Constantes.VALIDATION_PUNTOS_OBLIGATORIO)
	@Min(value = 0)
	private Integer puntos;

	@Column(name = "ganados_local")
	@NotNull(message = Constantes.VALIDATION_GANADOS_LOCAL_OBLIGATORIO)
	@Min(value = 0)
	private Integer ganadosLocal;

	@Column(name = "empatados_local")
	@NotNull(message = Constantes.VALIDATION_EMPATADOS_LOCAL_OBLIGATORIO)
	@Min(value = 0)
	private Integer empatadosLocal;

	@Column(name = "perdidos_local")
	@NotNull(message = Constantes.VALIDATION_PERDIDOS_LOCAL_OBLIGATORIO)
	@Min(value = 0)
	private Integer perdidosLocal;

	@Column(name = "ganados_visitante")
	@NotNull(message = Constantes.VALIDATION_GANADOS_VISITANTE_OBLIGATORIO)
	@Min(value = 0)
	private Integer ganadosVisitante;

	@Column(name = "empatados_visitante")
	@NotNull(message = Constantes.VALIDATION_EMPATADOS_VISITANTE_OBLIGATORIO)
	@Min(value = 0)
	private Integer empatadosVisitante;

	@Column(name = "perdidos_visitante")
	@NotNull(message = Constantes.VALIDATION_PERDIDOS_VISITANTE_OBLIGATORIO)
	@Min(value = 0)
	private Integer perdidosVisitante;

	@Column(name = "result_ultimo_partido")
	private ResultadoEnum resUltimoPartido;

	@Column(name = "result_penultimo_partido")
	private ResultadoEnum resPenultimoPartido;

	@ManyToOne
	@JoinColumn(name = "id_liga", nullable = false)
	@NotNull(message = Constantes.VALIDATION_LIGA_OBLIGATORIO)
	private Liga liga;

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

	public Liga getLiga() {
		return liga;
	}

	public void setLiga(Liga liga) {
		this.liga = liga;
	}

}
