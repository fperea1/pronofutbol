package com.fcpm.pronofutbol.entities;

import java.util.Set;

import com.googlecode.jmapper.annotations.JMap;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "roles")
public class Rol extends BaseEntity {
	
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
