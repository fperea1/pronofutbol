package com.base.rest.dtos;

import java.util.Date;
import java.util.Set;

import com.base.rest.views.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.googlecode.jmapper.annotations.JGlobalMap;

@JGlobalMap
public class UsuarioDTO {

	@JsonView(View.Public.class)
	private Integer id;
	
	@JsonView(View.Public.class)
	private String nombre;
	
	@JsonView(View.Public.class)
	private String username;
	
	private String password;
	
	@JsonView(View.Public.class)
	private String email;
	
	@JsonView(View.Public.class)
	private Date fechaAlta;
	
	@JsonView(View.Public.class)
	private Date fechaDesactivacion;
	
	@JsonView(View.Public.class)
	private Boolean activo;
	
	@JsonView(View.Public.class)
	private Set<RolDTO> roles;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaDesactivacion() {
		return fechaDesactivacion;
	}

	public void setFechaDesactivacion(Date fechaDesactivacion) {
		this.fechaDesactivacion = fechaDesactivacion;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Set<RolDTO> getRoles() {
		return roles;
	}

	public void setRoles(Set<RolDTO> roles) {
		this.roles = roles;
	}
	
}