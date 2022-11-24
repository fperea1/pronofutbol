package com.fcmp.pronofutbol.enums.reportes;

import java.util.stream.Stream;

import com.fcmp.pronofutbol.constant.Constantes;

public enum TablaUsuariosEnum {
	
	NOMBRE(Constantes.NOMBRE, "nombre"),
	USERNAME(Constantes.USERNAME, "username"),
	EMAIL(Constantes.EMAIL, "email"),
	FECHA_ALTA(Constantes.FECHA_ALTA, "fechaAlta"),
	FECHA_DESACTIVACION(Constantes.FECHA_BAJA, "fechaDesactivacion"),
	ACTIVO(Constantes.ACTIVO, "activo"),
	ROLES(Constantes.T_ROLES, "roles");

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
