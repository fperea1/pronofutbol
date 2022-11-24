package com.fcmp.pronofutbol.enums.reportes;

import java.util.stream.Stream;

import com.fcmp.pronofutbol.constant.Constantes;

public enum TablaConfiguracionEnum {
	
	NOMBRE(Constantes.NOMBRE, "nombre"),
	VALOR(Constantes.VALOR, "valor");

	public String titulo;
	
	public String column;

	private TablaConfiguracionEnum(String titulo, String column) {
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

	public static Stream<TablaConfiguracionEnum> stream() {
        return Stream.of(TablaConfiguracionEnum.values()); 
    }

}
