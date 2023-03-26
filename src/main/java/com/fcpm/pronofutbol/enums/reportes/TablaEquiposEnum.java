package com.fcpm.pronofutbol.enums.reportes;

import java.util.stream.Stream;

import com.fcpm.pronofutbol.constant.Constantes;

public enum TablaEquiposEnum {

	NOMBRE(Constantes.NOMBRE, "nombre"),
	PUNTOS(Constantes.NOMBRE, "nombre"),
	GANADOS_LOCAL(Constantes.GANADOS_LOCAL, "ganadosLocal"),
	EMPATADOS_LOCAL(Constantes.EMPATADOS_LOCAL, "empatadosLocal"),
	PERDIDOS_LOCAL(Constantes.PERDIDOS_LOCAL, "perdidosLocal"),
	GANADOS_VISITANTE(Constantes.GANADOS_VISITANTE, "ganadosVisitante"),
	EMPATADOS_VISITANTE(Constantes.EMPATADOS_VISITANTE, "empatadosVisitante"),
	PERDIDOS_VISITANTE(Constantes.PERDIDOS_VISITANTE, "perdidosVisitante"),
	RES_ULT_PARTIDO(Constantes.RES_ULT_PARTIDO, "resUltimoPartido"),
	RES_PENUL_PARTIDO(Constantes.RES_PENUL_PARTIDO, "resPenultimoPartido"),
	LIGA(Constantes.LIGA, "liga");

	private String titulo;
	
	private String column;

	private TablaEquiposEnum(String titulo, String column) {
		this.titulo = titulo;
		this.column = column;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public String getColumn() {
		return column;
	}

	public static Stream<TablaEquiposEnum> stream() {
        return Stream.of(TablaEquiposEnum.values()); 
    }
}
