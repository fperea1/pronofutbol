package com.base.rest.controllers;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.rest.constant.Constantes;
import com.base.rest.dtos.CambioPasswordDTO;
import com.base.rest.dtos.ContactoDTO;
import com.base.rest.entities.Log;
import com.base.rest.entities.Usuario;
import com.base.rest.enums.ConfiguracionEnum;
import com.base.rest.service.interfaces.ConfiguracionService;
import com.base.rest.service.interfaces.LogService;
import com.base.rest.service.interfaces.UsuarioService;
import com.base.rest.utils.mail.EmailServiceImpl;

@RestController
@RequestMapping(Constantes.CONTACTO)
public class ContactoController extends BaseController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EmailServiceImpl emailService;
	
	@Autowired
	private ConfiguracionService configuracionService;
	
	@Autowired
	private LogService logService;
	
	@PostMapping(Constantes.ENVIO_CONSULTA)
    public ResponseEntity<String> envioConsulta(@Valid @RequestBody ContactoDTO contacto) {
		
		String from = configuracionService.findById(ConfiguracionEnum.MAIL_FROM.getCodigo()).getValor(); 
		String to = configuracionService.findById(ConfiguracionEnum.MAIL_ADMINISTRACION.getCodigo()).getValor();
		String cc = configuracionService.findById(ConfiguracionEnum.MAIL_ADMINISTRACION_CC.getCodigo()).getValor();
		String bcc = configuracionService.findById(ConfiguracionEnum.MAIL_ADMINISTRACION_BCC.getCodigo()).getValor();
		
		emailService.sendSimpleMessage(from, to, cc, bcc, contacto.getAsunto(), contacto.getConsulta());
        
        return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }
	
	@PutMapping(Constantes.CAMBIO_PASSWORD)
	public ResponseEntity<String> cambioPasswordUser(@RequestBody CambioPasswordDTO cambioPasswordDTO) {
		Usuario usuario = usuarioService.findByUsername(getCurrentUserName());
		usuarioService.cambioPasswordUser(usuario.getId(), usuario.getUsername(), 
				cambioPasswordDTO.getOldPassword(), cambioPasswordDTO.getNewPassword(), cambioPasswordDTO.getNewPassword2());
		logService.save(new Log(getCurrentUserName(), Constantes.USUARIO, Constantes.CAMBIO_PASS_USUARIO, Constantes.USUARIO + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername(), new Date()));
		return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }

}
