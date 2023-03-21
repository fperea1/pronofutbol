package com.fcpm.pronofutbol.entities;

import java.util.Date;

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
@Table(name = "quinielas")
@JGlobalMap
public class Quiniela extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "numero", unique = true)
	@NotNull(message = Constantes.VALIDATION_NUMERO_OBLIGATORIO)
	@Min(value = 1)
	private Integer numero;
	
	@Column(name = "nombre", unique = true)
	@NotNull(message = Constantes.VALIDATION_NOMBRE_OBLIGATORIO)
	@Size(min = 1, max = 50, message = Constantes.VALIDATION_NOMBRE_QUINIELA_SIZE)
	private String nombre;
	
	@Column(name = "fecha", unique = true)
	@NotNull(message = Constantes.VALIDATION_FECHA_OBLIGATORIO)
	private Date fecha;
	
	@Column(name = "actualizada")
	@NotNull(message = Constantes.VALIDATION_ACTUALIZADA_OBLIGATORIO)
	private Boolean actualizada;
	
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

	public Liga getLiga() {
		return liga;
	}

	public void setLiga(Liga liga) {
		this.liga = liga;
	}

}
