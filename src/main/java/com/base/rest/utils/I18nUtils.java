package com.base.rest.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public final class I18nUtils {
	
	
    private static MessageSource messageSource;
	
	public static String getMensaje(String mensage) {
		return messageSource.getMessage(mensage, null, LocaleContextHolder.getLocale());
	}
	
	public static void setMessageSource(MessageSource messageSource) {
		I18nUtils.messageSource = messageSource;
	}

}
