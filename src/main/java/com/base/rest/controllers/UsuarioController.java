package com.base.rest.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.rest.constant.Constantes;
import com.base.rest.dtos.UsuarioDTO;
import com.base.rest.entities.Log;
import com.base.rest.entities.Usuario;
import com.base.rest.service.interfaces.LogService;
import com.base.rest.service.interfaces.UsuarioService;
import com.base.rest.views.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.googlecode.jmapper.JMapper;

@RestController
@RequestMapping(Constantes.USUARIOS)
public class UsuarioController extends BaseController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private LogService logService;
	
	@JsonView(View.Public.class)
	@GetMapping(Constantes.FIND_ALL)
    public ResponseEntity<List<UsuarioDTO>> findAll() {        
        List<Usuario> usuarios = usuarioService.findAll();
		JMapper<UsuarioDTO, Usuario> mapper = new JMapper<>(UsuarioDTO.class, Usuario.class);
		List<UsuarioDTO> result = usuarios.stream().map(temp-> mapper.getDestination(temp))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
	
	@PostMapping(Constantes.SAVE)
    public ResponseEntity<String> save(@RequestBody UsuarioDTO usuario) {
		JMapper<Usuario, UsuarioDTO> mapper = new JMapper<>(Usuario.class, UsuarioDTO.class);
		usuarioService.save(mapper.getDestination(usuario));
		logService.save(new Log(getCurrentUserName(), Constantes.USUARIO, Constantes.ALTA, Constantes.USUARIO + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername(), new Date()));
        return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }
	
	@PutMapping(Constantes.UPDATE)
    public ResponseEntity<String> update(@RequestBody UsuarioDTO usuario) {
		JMapper<Usuario, UsuarioDTO> mapper = new JMapper<>(Usuario.class, UsuarioDTO.class);
		usuarioService.update(mapper.getDestination(usuario));
		logService.save(new Log(getCurrentUserName(), Constantes.USUARIO, Constantes.EDICION, Constantes.USUARIO + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername(), new Date()));
        return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }
	
	@JsonView(View.Public.class)
	@GetMapping(Constantes.FIND)
    public ResponseEntity<UsuarioDTO> findById(@RequestParam Integer id) {
		JMapper<UsuarioDTO, Usuario> mapper = new JMapper<>(UsuarioDTO.class, Usuario.class);
		return new ResponseEntity<>(mapper.getDestination(usuarioService.findById(id)), HttpStatus.OK);
    }
	
	@DeleteMapping(Constantes.DELETE)
    public ResponseEntity<String> deleteById(@RequestParam Integer id) {
		Usuario usuario = usuarioService.findById(id);
		usuarioService.deleteById(id);
		logService.save(new Log(getCurrentUserName(), Constantes.USUARIO, Constantes.BAJA, Constantes.USUARIO + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername(), new Date()));
		return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }
	
	@PutMapping(Constantes.DEACTIVATE)
    public ResponseEntity<String> deactivateById(@RequestParam Integer id) {
		Usuario usuario = usuarioService.findById(id);
		usuarioService.deactivateById(id);
		logService.save(new Log(getCurrentUserName(), Constantes.USUARIO, Constantes.DESACTIVAR, Constantes.USUARIO +Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername(), new Date()));
		return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }
	
	@PutMapping(Constantes.ACTIVATE)
    public ResponseEntity<String> activateById(@RequestParam Integer id) {
		Usuario usuario = usuarioService.findById(id);
		usuarioService.activateById(id);
		logService.save(new Log(getCurrentUserName(), Constantes.USUARIO, Constantes.ACTIVAR, Constantes.USUARIO + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername(), new Date()));
		return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }
	
	@PutMapping(Constantes.CAMBIO_PASSWORD)
	public ResponseEntity<String> cambioPasswordUser(@RequestParam Integer id, @RequestParam String oldPassword, @RequestParam String newPassword) {
		Usuario usuario = usuarioService.findById(id);
		usuarioService.cambioPasswordUser(id, usuario.getUsername(), oldPassword, newPassword);
		logService.save(new Log(getCurrentUserName(), Constantes.USUARIO, Constantes.CAMBIO_PASS_USUARIO, Constantes.USUARIO + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername(), new Date()));
		return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }
	
	@PutMapping(Constantes.CAMBIO_PASSWORD_ADMIN)
	public ResponseEntity<String> cambioPasswordAdmin(@RequestParam Integer id, @RequestParam String newPassword) {
		Usuario usuario = usuarioService.findById(id);
		usuarioService.cambioPasswordAdmin(id, newPassword);
		logService.save(new Log(getCurrentUserName(), Constantes.USUARIO, Constantes.CAMBIO_PASS_ADMIN, Constantes.USUARIO + Constantes.SEPARADOR_DOS_PUNTOS + usuario.getUsername(), new Date()));
		return new ResponseEntity<>(Constantes.OPERACION_CORRECTA, HttpStatus.OK);
    }
}
