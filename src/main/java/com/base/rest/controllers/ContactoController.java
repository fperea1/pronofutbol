package com.base.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.rest.ConfiguracionEnum;
import com.base.rest.constant.Constantes;
import com.base.rest.service.interfaces.ConfiguracionService;
import com.base.rest.utils.EmailServiceImpl;

@RestController
@RequestMapping(Constantes.CONTACTO)
public class ContactoController {
	
	@Autowired
	private EmailServiceImpl emailService;
	
	@Autowired
	private ConfiguracionService configuracionService;
	
	@PostMapping(Constantes.ENVIO_CONSULTA)
    public ResponseEntity<String> envioConsulta(@RequestParam String asunto, @RequestParam String consulta) {
		
		String from = configuracionService.findById(ConfiguracionEnum.MAIL_FROM.getCodigo()).getValor(); 
		String to = configuracionService.findById(ConfiguracionEnum.MAIL_ADMINISTRACION.getCodigo()).getValor();
		String cc = configuracionService.findById(ConfiguracionEnum.MAIL_ADMINISTRACION_CC.getCodigo()).getValor();
		String bcc = configuracionService.findById(ConfiguracionEnum.MAIL_ADMINISTRACION_BCC.getCodigo()).getValor();
		
		emailService.sendSimpleMessage(from, to, cc, bcc, asunto,  consulta);
        
        return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }

}
