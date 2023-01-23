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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "ligas")
@JGlobalMap
public class Liga extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nombre", length = 100, unique = true)
	@NotNull(message = Constantes.VALIDATION_NOMBRE_OBLIGATORIO)
	@Size(min = 1, max = 100, message = Constantes.VALIDATION_NOMBRE_LIGA_SIZE)
	private String nombre;
	
	@ManyToOne
	@JoinColumn(name="id_pais", nullable=false)
	@NotNull(message = Constantes.VALIDATION_PAIS_OBLIGATORIO)
	private Pais pais;

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

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

}
