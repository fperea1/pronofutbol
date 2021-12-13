package com.base.rest.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.googlecode.jmapper.annotations.JGlobalMap;

@Entity
@Table(name = "logs")
@JGlobalMap
public class Log {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "username", length = 50)
	@Size(min = 5, max = 50, message = "Username debe tener entre 5 y 50 caracteres")
	@NotNull(message = "Username. Campo obligatorio")
	private String username;
	
	@Column(name = "entidad", length = 50)
	@Size(min = 1, max = 50, message = "Entidad debe tener entre 1 y 50 caracteres")
	@NotNull(message = "Entidad. Campo obligatorio")
	private String entidad;
	
	@Column(name = "accion", length = 50)
	@Size(min = 1, max = 50, message = "Acción debe tener entre 1 y 50 caracteres")
	@NotNull(message = "Acción. Campo obligatorio")
	private String accion;
	
	@Column(name = "observaciones", length = 500)
	@Size(min = 1, max = 500, message = "Observaciones debe tener entre 1 y 500 caracteres")
	@NotNull(message = "Observaciones. Campo obligatorio")
	private String observaciones;
	
	@Column(name = "fecha")
	@NotNull(message = "Fecha. Campo obligatorio")
	private Date fecha;

	public Log() {
		super();
	}

	public Log(String username, String entidad, String accion, String observaciones, Date fecha) {
		super();
		this.username = username;
		this.entidad = entidad;
		this.accion = accion;
		this.observaciones = observaciones;
		this.fecha = fecha;
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
