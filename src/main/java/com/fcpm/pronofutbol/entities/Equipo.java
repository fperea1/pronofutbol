package com.fcpm.pronofutbol.entities;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.LigaDTO;
import com.fcpm.pronofutbol.enums.ResultadoEnum;
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
@Table(name = "equipos")
@JGlobalMap
public class Equipo extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "nombre", unique = true)
	@NotNull(message = Constantes.VALIDATION_NOMBRE_OBLIGATORIO)
	@Size(min = 1, max = 100, message = Constantes.VALIDATION_NOMBRE_EQUIPO_SIZE)
	private String nombre;

	@Column(name = "puntos")
	@NotNull(message = Constantes.VALIDATION_PUNTOS_OBLIGATORIO)
	@Min(value = 0)
	private Integer puntos;

	@Column(name = "ganados_local")
	@NotNull(message = Constantes.VALIDATION_GANADOS_LOCAL_OBLIGATORIO)
	@Min(value = 0)
	private Integer ganadosLocal;

	@Column(name = "empatados_local")
	@NotNull(message = Constantes.VALIDATION_EMPATADOS_LOCAL_OBLIGATORIO)
	@Min(value = 0)
	private Integer empatadosLocal;

	@Column(name = "perdidos_local")
	@NotNull(message = Constantes.VALIDATION_PERDIDOS_LOCAL_OBLIGATORIO)
	@Min(value = 0)
	private Integer perdidosLocal;

	@Column(name = "ganados_visitante")
	@NotNull(message = Constantes.VALIDATION_GANADOS_VISITANTE_OBLIGATORIO)
	@Min(value = 0)
	private Integer ganadosVisitante;

	@Column(name = "empatados_visitante")
	@NotNull(message = Constantes.VALIDATION_EMPATADOS_VISITANTE_OBLIGATORIO)
	@Min(value = 0)
	private Integer empatadosVisitante;

	@Column(name = "perdidos_visitante")
	@NotNull(message = Constantes.VALIDATION_PERDIDOS_VISITANTE_OBLIGATORIO)
	@Min(value = 0)
	private Integer perdidosVisitante;

	@Column(name = "result_ultimo_partido")
	private ResultadoEnum resUltimoPartido;

	@Column(name = "result_penultimo_partido")
	private ResultadoEnum resPenultimoPartido;

	@ManyToOne
	@JoinColumn(name = "id_liga", nullable = false)
	@NotNull(message = Constantes.VALIDATION_LIGA_OBLIGATORIO)
	private LigaDTO liga;

}
