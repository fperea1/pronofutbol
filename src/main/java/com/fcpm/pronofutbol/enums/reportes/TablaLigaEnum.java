package com.fcpm.pronofutbol.enums.reportes;

import java.util.stream.Stream;

import com.fcpm.pronofutbol.constant.Constantes;

public enum TablaLigaEnum {

	NOMBRE(Constantes.NOMBRE, "nombre"),
	PAIS(Constantes.PAIS, "pais");

	public String titulo;
	
	public String column;

	private TablaLigaEnum(String titulo, String column) {
		this.titulo = titulo;
		this.column = column;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public static Stream<TablaLigaEnum> stream() {
        return Stream.of(TablaLigaEnum.values()); 
    }
}
