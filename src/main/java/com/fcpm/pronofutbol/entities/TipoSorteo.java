package com.fcpm.pronofutbol.entities;

import com.fcpm.pronofutbol.constant.Constantes;
import com.googlecode.jmapper.annotations.JMap;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tipo_sorteo")
public class TipoSorteo extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JMap
	private Integer id;
	
	@Column(name = "nombre", length = 50, unique = true)
	@NotNull(message = Constantes.VALIDATION_NOMBRE_OBLIGATORIO)
	@Size(min = 1, max = 50, message = Constantes.VALIDATION_NOMBRE_TIPO_SORTEO_SIZE)
	@JMap
	private String nombre;
	
	@Column(name = "num_dobles")
	@NotNull(message = Constantes.VALIDATION_NUMERO_DOBLES_OBLIGATORIO)
	@JMap
	private Integer numDobles;
	
	@Column(name = "num_triples")
	@NotNull(message = Constantes.VALIDATION_NUMERO_TRIPLES_OBLIGATORIO)
	@JMap
	private Integer numTriples;

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

	public Integer getNumDobles() {
		return numDobles;
	}

	public void setNumDobles(Integer numDobles) {
		this.numDobles = numDobles;
	}

	public Integer getNumTriples() {
		return numTriples;
	}

	public void setNumTriples(Integer numTriples) {
		this.numTriples = numTriples;
	}

}
