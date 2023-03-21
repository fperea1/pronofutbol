package com.fcpm.pronofutbol.enums.reportes;

import java.util.stream.Stream;

import com.fcpm.pronofutbol.constant.Constantes;

public enum TablaLigaEnum {

	NOMBRE(Constantes.NOMBRE, "nombre"),
	PAIS(Constantes.PAIS, "pais");

	private String titulo;
	
	private String column;

	private TablaLigaEnum(String titulo, String column) {
		this.titulo = titulo;
		this.column = column;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public String getColumn() {
		return column;
	}

	public static Stream<TablaLigaEnum> stream() {
        return Stream.of(TablaLigaEnum.values()); 
    }
}
