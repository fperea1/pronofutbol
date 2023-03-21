package com.fcpm.pronofutbol.enums.reportes;

import java.util.stream.Stream;

import com.fcpm.pronofutbol.constant.Constantes;

public enum TablaPaisEnum {

	NOMBRE(Constantes.NOMBRE, "nombre");

	private String titulo;
	
	private String column;

	private TablaPaisEnum(String titulo, String column) {
		this.titulo = titulo;
		this.column = column;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public String getColumn() {
		return column;
	}

	public static Stream<TablaPaisEnum> stream() {
        return Stream.of(TablaPaisEnum.values()); 
    }
}
