package com.base.rest.utils.mail;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendSimpleMessage(String from, String to, String cc, String bcc, String asunto, String texto) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to.split(";"));
		if (!StringUtils.isBlank(cc) && !cc.equals("-")) {
			message.setCc(cc.split(";"));
		}
		if (!StringUtils.isBlank(bcc) && !bcc.equals("-")) {
			message.setBcc(bcc.split(";"));
		}
		message.setSubject(asunto);
		message.setText(texto);
		javaMailSender.send(message);
		
	}

}
