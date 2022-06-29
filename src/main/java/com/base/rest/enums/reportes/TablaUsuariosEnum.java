package com.base.rest.enums.reportes;

import java.util.stream.Stream;

public enum TablaUsuariosEnum {
	
	NOMBRE("Nombre", "nombre"),
	USERNAME("Username", "username"),
	EMAIL("Email", "email"),
	FECHA_ALTA("Fecha de Alta", "fechaAlta"),
	FECHA_DESACTIVACION("Fecha de Baja", "fechaDesactivacion"),
	ACTIVO("Activo", "activo"),
	ROLES("Roles", "roles");

	public String titulo;
	
	public String column;

	private TablaUsuariosEnum(String titulo, String column) {
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

	public static Stream<TablaUsuariosEnum> stream() {
        return Stream.of(TablaUsuariosEnum.values()); 
    }

}
