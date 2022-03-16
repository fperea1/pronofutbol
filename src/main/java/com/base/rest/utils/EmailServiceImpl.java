package com.base.rest.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl {
	
	@Autowired
	private JavaMailSender emailSender;
	
	public void sendSimpleMessage(String from, String to, String cc, String bcc, String asunto, String texto) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		if (!StringUtils.isBlank(cc)) {
			message.setCc(cc);
		}
		if (!StringUtils.isBlank(bcc)) {
			message.setBcc(bcc);
		}
		message.setSubject(asunto);
		message.setText(texto);
		emailSender.send(message);
		
	}

}
