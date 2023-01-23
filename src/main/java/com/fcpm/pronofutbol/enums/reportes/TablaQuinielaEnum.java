package com.fcpm.pronofutbol.enums.reportes;

import java.util.stream.Stream;

import com.fcpm.pronofutbol.constant.Constantes;

public enum TablaQuinielaEnum {

	NUMERO(Constantes.NUMERO, "numero"),
	NOMBRE(Constantes.NOMBRE, "nombre"),
	FECHA(Constantes.FECHA, "fecha"),
	ACTUALIZADA(Constantes.ACTIVO, "actualizada"),
	LIGA(Constantes.LIGA, "liga");

	public String titulo;
	
	public String column;

	private TablaQuinielaEnum(String titulo, String column) {
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

	public static Stream<TablaQuinielaEnum> stream() {
        return Stream.of(TablaQuinielaEnum.values()); 
    }
}
