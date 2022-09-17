package com.base.rest.enums.reportes;

import java.util.stream.Stream;

import com.base.rest.constant.Constantes;

public enum TablaLogsEnum {
	
	USUARIO(Constantes.USUARIO, "username"),
	ENTIDAD(Constantes.ENTIDAD, "entidad"),
	ACCION(Constantes.ACCION, "accion"),
	OBSERVACIONES(Constantes.OBSERVACIONES, "observaciones"),
	FECHA(Constantes.FECHA, "fecha");

	public String titulo;
	
	public String column;

	private TablaLogsEnum(String titulo, String column) {
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

	public static Stream<TablaLogsEnum> stream() {
        return Stream.of(TablaLogsEnum.values()); 
    }
	
}
