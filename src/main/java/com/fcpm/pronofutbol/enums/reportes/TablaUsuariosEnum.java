package com.fcpm.pronofutbol.enums.reportes;

import java.util.stream.Stream;

import com.fcpm.pronofutbol.constant.Constantes;

public enum TablaUsuariosEnum {
	
	NOMBRE(Constantes.NOMBRE, "nombre"),
	USERNAME(Constantes.USERNAME, "username"),
	EMAIL(Constantes.EMAIL, "email"),
	FECHA_ALTA(Constantes.FECHA_ALTA, "fechaAlta"),
	FECHA_DESACTIVACION(Constantes.FECHA_BAJA, "fechaDesactivacion"),
	ACTIVO(Constantes.ACTIVO, "activo"),
	ROLES(Constantes.T_ROLES, "roles");

	private String titulo;
	
	private String column;

	private TablaUsuariosEnum(String titulo, String column) {
		this.titulo = titulo;
		this.column = column;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public String getColumn() {
		return column;
	}

	public static Stream<TablaUsuariosEnum> stream() {
        return Stream.of(TablaUsuariosEnum.values()); 
    }

}
