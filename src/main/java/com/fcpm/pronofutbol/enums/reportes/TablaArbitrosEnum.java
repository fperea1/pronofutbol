package com.fcpm.pronofutbol.enums.reportes;

import java.util.stream.Stream;

import com.fcpm.pronofutbol.constant.Constantes;

public enum TablaArbitrosEnum {

	NOMBRE(Constantes.NOMBRE, "nombre"),
	GANADOS_LOCAL(Constantes.GANADOS_LOCAL, "ganadosLocal"),
	EMPATADOS(Constantes.EMPATADOS, "empatados"),
	GANADOS_VISITANTE(Constantes.GANADOS_VISITANTE, "ganadosVisitante"),
	LIGA(Constantes.LIGA, "liga");

	private String titulo;
	
	private String column;

	private TablaArbitrosEnum(String titulo, String column) {
		this.titulo = titulo;
		this.column = column;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public String getColumn() {
		return column;
	}

	public static Stream<TablaArbitrosEnum> stream() {
        return Stream.of(TablaArbitrosEnum.values()); 
    }
}
