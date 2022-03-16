package com.base.rest;

public enum ConfiguracionEnum {
	
	MAIL_FROM(1, "mailFrom"),
	MAIL_ADMINISTRACION(2, "mailAdministracion"),
	MAIL_ADMINISTRACION_CC(3, "mailAdministracionCC"),
	MAIL_ADMINISTRACION_BCC(4, "mailAdministracionBCC");
	
	public Integer codigo;
	
	public String nombre;

	private ConfiguracionEnum(Integer codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
