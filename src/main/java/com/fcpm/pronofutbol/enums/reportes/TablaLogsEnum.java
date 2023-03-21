package com.fcpm.pronofutbol.enums.reportes;

import java.util.stream.Stream;

import com.fcpm.pronofutbol.constant.Constantes;

public enum TablaLogsEnum {
	
	USUARIO(Constantes.USUARIO, "username"),
	ENTIDAD(Constantes.ENTIDAD, "entidad"),
	ACCION(Constantes.ACCION, "accion"),
	OBSERVACIONES(Constantes.OBSERVACIONES, "observaciones"),
	FECHA(Constantes.FECHA, "fecha");

	private String titulo;
	
	private String column;

	private TablaLogsEnum(String titulo, String column) {
		this.titulo = titulo;
		this.column = column;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public String getColumn() {
		return column;
	}

	public static Stream<TablaLogsEnum> stream() {
        return Stream.of(TablaLogsEnum.values()); 
    }
	
}
