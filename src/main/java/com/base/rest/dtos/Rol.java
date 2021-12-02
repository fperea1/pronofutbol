package com.base.rest.dtos;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.base.rest.views.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.NotNull;

@Entity
@Table(name = "roles")
public class Rol {
	
	@JsonView(View.Public.class)
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JsonView(View.Public.class)
	@Column(name = "nombre", length = 50, unique = true)
	@NotNull
	private String nombre;

    @ManyToMany(mappedBy = "roles")
   // @JsonIgnore
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
