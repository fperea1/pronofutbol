package com.base.rest.enums.reportes;

import java.util.stream.Stream;

public enum TablaLogsEnum {
	
	USUARIO("Usuario", "username"),
	ENTIDAD("Entidad", "entidad"),
	ACCION("Acción", "accion"),
	OBSERVACIONES("Observaciones", "observaciones"),
	FECHA("Fecha", "fecha");

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
	
	//TablaLogsEnum.stream().forEach(titulo -> crearComlumna(titulo));
}
