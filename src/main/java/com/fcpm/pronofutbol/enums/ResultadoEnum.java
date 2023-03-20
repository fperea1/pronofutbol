package com.fcpm.pronofutbol.enums;

public enum ResultadoEnum {

	UNO("1"), EQUIS("X"), DOS("2");

	private final String codigo;

	ResultadoEnum(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

}
