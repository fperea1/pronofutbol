package com.base.rest.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.googlecode.jmapper.annotations.JMap;

@Entity
@Table(name = "roles")
public class Rol {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JMap
	private int id;
	
	@Column(name = "nombre", length = 50, unique = true)
	@NotBlank
	@JMap
	private String nombre;

    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> users;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Usuario> getUsers() {
		return users;
	}

	public void setUsers(Set<Usuario> users) {
		this.users = users;
	}

}
