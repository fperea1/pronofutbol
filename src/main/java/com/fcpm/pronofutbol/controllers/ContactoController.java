package com.fcpm.pronofutbol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.CambioPasswordDTO;
import com.fcpm.pronofutbol.dtos.ContactoDTO;
import com.fcpm.pronofutbol.dtos.UsuarioDTO;
import com.fcpm.pronofutbol.enums.ConfiguracionEnum;
import com.fcpm.pronofutbol.service.interfaces.ConfiguracionService;
import com.fcpm.pronofutbol.service.interfaces.UsuarioService;
import com.fcpm.pronofutbol.utils.mail.EmailServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping(Constantes.CONTACTO)
public class ContactoController extends BaseController {

	@Autowired
	private UsuarioService service;
	
	@Autowired
	private EmailServiceImpl emailService;
	
	@Autowired
	private ConfiguracionService configuracionService;
	
	@PostMapping(Constantes.ENVIO_CONSULTA)
    public ResponseEntity<String> envioConsulta(@Valid @RequestBody ContactoDTO contacto) {
		
		String from = configuracionService.getById(ConfiguracionEnum.MAIL_FROM.getCodigo()).getValor(); 
		String to = configuracionService.getById(ConfiguracionEnum.MAIL_ADMINISTRACION.getCodigo()).getValor();
		String cc = configuracionService.getById(ConfiguracionEnum.MAIL_ADMINISTRACION_CC.getCodigo()).getValor();
		String bcc = configuracionService.getById(ConfiguracionEnum.MAIL_ADMINISTRACION_BCC.getCodigo()).getValor();
		
		emailService.sendSimpleMessage(from, to, cc, bcc, contacto.getAsunto(), contacto.getConsulta());
        
        return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }
	
	@PutMapping(Constantes.CAMBIO_PASSWORD)
	public ResponseEntity<String> cambioPasswordUser(@Valid @RequestBody CambioPasswordDTO cambioPasswordDTO) {
		UsuarioDTO usuario = service.findByUsername(getCurrentUserName());
		service.cambioPasswordUser(usuario.getId(), usuario.getUsername(), 
				cambioPasswordDTO.getOldPassword(), cambioPasswordDTO.getNewPassword(), cambioPasswordDTO.getNewPassword2());
		return responseOperationCorrecta(Constantes.USUARIO, Constantes.CAMBIO_PASS_USUARIO, Constantes.USUARIO + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername());
    }

}
