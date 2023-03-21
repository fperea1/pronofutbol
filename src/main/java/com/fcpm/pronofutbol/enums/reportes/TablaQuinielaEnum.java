package com.fcpm.pronofutbol.enums.reportes;

import java.util.stream.Stream;

import com.fcpm.pronofutbol.constant.Constantes;

public enum TablaQuinielaEnum {

	NUMERO(Constantes.NUMERO, "numero"),
	NOMBRE(Constantes.NOMBRE, "nombre"),
	FECHA(Constantes.FECHA, "fecha"),
	ACTUALIZADA(Constantes.ACTIVO, "actualizada"),
	LIGA(Constantes.LIGA, "liga");

	private String titulo;
	
	private String column;

	private TablaQuinielaEnum(String titulo, String column) {
		this.titulo = titulo;
		this.column = column;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public String getColumn() {
		return column;
	}

	public static Stream<TablaQuinielaEnum> stream() {
        return Stream.of(TablaQuinielaEnum.values()); 
    }
}
