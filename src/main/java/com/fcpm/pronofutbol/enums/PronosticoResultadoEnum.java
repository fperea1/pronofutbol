package com.fcpm.pronofutbol.enums;

public enum PronosticoResultadoEnum {

	UNO("1"), EQUIS("X"), DOS("2"), UNO_EQUIS("1X"), UNO_DOS("12"), EQUIS_DOS("X2"), TRIPLE("1X2");

	private final String codigo;

	PronosticoResultadoEnum(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}
}
