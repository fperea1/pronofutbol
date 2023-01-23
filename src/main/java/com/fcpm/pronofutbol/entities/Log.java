package com.fcpm.pronofutbol.entities;

import java.util.Date;

import com.fcpm.pronofutbol.constant.Constantes;
import com.googlecode.jmapper.annotations.JGlobalMap;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "logs")
@JGlobalMap
public class Log extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "username", length = 50)
	@Size(min = 5, max = 50, message = Constantes.VALIDATION_USERNAME_LOG_SIZE)
	@NotNull(message = Constantes.VALIDATION_USERNAME_OBLIGATORIO)
	private String username;
	
	@Column(name = "entidad", length = 50)
	@Size(min = 1, max = 50, message = Constantes.VALIDATION_ENTIDAD_LOG_SIZE)
	@NotNull(message = Constantes.VALIDATION_ENTIDAD_OBLIGATORIO)
	private String entidad;
	
	@Column(name = "accion", length = 50)
	@Size(min = 1, max = 50, message = Constantes.VALIDATION_ACCION_LOG_SIZE)
	@NotNull(message = Constantes.VALIDATION_ACCION_OBLIGATORIO)
	private String accion;
	
	@Column(name = "observaciones", length = 500)
	@Size(min = 1, max = 500, message = Constantes.VALIDATION_OBSERVACIONES_LOG_SIZE)
	@NotNull(message = Constantes.VALIDATION_OBSERVACIONES_OBLIGATORIO)
	private String observaciones;
	
	@Column(name = "fecha")
	@NotNull(message = Constantes.VALIDATION_FECHA_OBLIGATORIO)
	private Date fecha;

	public Log() {
		super();
	}

	public Log(String username, String entidad, String accion, String observaciones) {
		super();
		this.username = username;
		this.entidad = entidad;
		this.accion = accion;
		this.observaciones = observaciones;
		this.fecha = new Date();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}
