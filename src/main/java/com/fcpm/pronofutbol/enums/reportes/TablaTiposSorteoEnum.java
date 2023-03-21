package com.fcpm.pronofutbol.enums.reportes;

import java.util.stream.Stream;

import com.fcpm.pronofutbol.constant.Constantes;

public enum TablaTiposSorteoEnum {

	NOMBRE(Constantes.NOMBRE, "nombre"),
	NUM_DOBLES(Constantes.NUM_DOBLES, "numDobles"),
	NUM_TRIPLES(Constantes.NUM_TRIPLES, "numTriples");

	private String titulo;
	
	private String column;

	private TablaTiposSorteoEnum(String titulo, String column) {
		this.titulo = titulo;
		this.column = column;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public String getColumn() {
		return column;
	}

	public static Stream<TablaTiposSorteoEnum> stream() {
        return Stream.of(TablaTiposSorteoEnum.values()); 
    }
}
