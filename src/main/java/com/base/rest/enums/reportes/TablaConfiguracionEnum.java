package com.base.rest.enums.reportes;

import java.util.stream.Stream;

public enum TablaConfiguracionEnum {
	
	NOMBRE("Nombre", "nombre"),
	VALOR("Valor", "valor");

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
