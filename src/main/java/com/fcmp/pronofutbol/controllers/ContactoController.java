package com.fcmp.pronofutbol.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fcmp.pronofutbol.constant.Constantes;
import com.fcmp.pronofutbol.dtos.CambioPasswordDTO;
import com.fcmp.pronofutbol.dtos.ContactoDTO;
import com.fcmp.pronofutbol.dtos.UsuarioDTO;
import com.fcmp.pronofutbol.enums.ConfiguracionEnum;
import com.fcmp.pronofutbol.service.interfaces.ConfiguracionService;
import com.fcmp.pronofutbol.service.interfaces.UsuarioService;
import com.fcmp.pronofutbol.utils.mail.EmailServiceImpl;

@RestController
@RequestMapping(Constantes.CONTACTO)
public class ContactoController extends BaseController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EmailServiceImpl emailService;
	
	@Autowired
	private ConfiguracionService configuracionService;
	
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
		UsuarioDTO usuario = usuarioService.findByUsername(getCurrentUserName());
		usuarioService.cambioPasswordUser(usuario.getId(), usuario.getUsername(), 
				cambioPasswordDTO.getOldPassword(), cambioPasswordDTO.getNewPassword(), cambioPasswordDTO.getNewPassword2());
		return responseOperationCorrecta(Constantes.USUARIO, Constantes.CAMBIO_PASS_USUARIO, Constantes.USUARIO + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername());
    }

}
