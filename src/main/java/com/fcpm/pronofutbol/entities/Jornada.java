package com.fcpm.pronofutbol.entities;

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
@Table(name = "jornadas")
@JGlobalMap
public class Jornada extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nombre", length = 50, unique = true)
	@NotNull(message = Constantes.VALIDATION_NOMBRE_OBLIGATORIO)
	@Size(min = 1, max = 50, message = Constantes.VALIDATION_NOMBRE_JORNADA_SIZE)
	private String nombre;

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
}
