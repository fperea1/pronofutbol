package com.fcpm.pronofutbol.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public final class I18nUtils {
	
	@Autowired
    private static MessageSource messageSource;
	
	public static String getMensaje(String message) {
		return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
	}
	
	public static void setMessageSource(MessageSource messageSource) {
		I18nUtils.messageSource = messageSource;
	}

}
