package com.base.rest.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.base.rest.constant.Constantes;

public final class Utilidades {
	
	private Utilidades() {}
	
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constantes.YYYY_MM_DD_T_HH_MM_SS_Z);
	
	public static Date getDateFormat(String value) {
		return Date.from(LocalDateTime.parse(value, formatter).atZone(ZoneId.systemDefault()).toInstant());
	}
}
