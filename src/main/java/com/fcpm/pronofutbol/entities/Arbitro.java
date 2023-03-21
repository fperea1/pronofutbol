package com.fcpm.pronofutbol.entities;

import com.fcpm.pronofutbol.constant.Constantes;
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
@Table(name = "arbitros")
@JGlobalMap
public class Arbitro extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "nombre", unique = true)
	@NotNull(message = Constantes.VALIDATION_NOMBRE_OBLIGATORIO)
	@Size(min = 1, max = 50, message = Constantes.VALIDATION_NOMBRE_ARBITRO_SIZE)
	private String nombre;
	
	@Column(name = "ganados_local", nullable=false)
	@NotNull(message = Constantes.VALIDATION_GANADOS_LOCAL_OBLIGATORIO)
	@Min(value = 0)
	private Integer ganadosLocal;
	
	@Column(name = "empatados", nullable=false)
	@NotNull(message = Constantes.VALIDATION_EMPATADOS_OBLIGATORIO)
	@Min(value = 0)
	private Integer empatados;
	
	@Column(name = "ganados_visitante", nullable=false)
	@NotNull(message = Constantes.VALIDATION_GANADOS_VISITANTE_OBLIGATORIO)
	@Min(value = 0)
	private Integer ganadosVisitante;
	
	@ManyToOne
	@JoinColumn(name="id_liga", nullable=false)
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

	public Liga getLiga() {
		return liga;
	}

	public void setLiga(Liga liga) {
		this.liga = liga;
	}

}
