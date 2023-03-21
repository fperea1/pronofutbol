package com.fcpm.pronofutbol.enums.reportes;

import java.util.stream.Stream;

import com.fcpm.pronofutbol.constant.Constantes;

public enum TablaConfiguracionEnum {
	
	NOMBRE(Constantes.NOMBRE, "nombre"),
	VALOR(Constantes.VALOR, "valor");

	private String titulo;
	
	private String column;

	private TablaConfiguracionEnum(String titulo, String column) {
		this.titulo = titulo;
		this.column = column;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public String getColumn() {
		return column;
	}

	public static Stream<TablaConfiguracionEnum> stream() {
        return Stream.of(TablaConfiguracionEnum.values()); 
    }

}
