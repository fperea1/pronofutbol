package com.base.rest.controllers;

import java.util.Date;
import java.util.List;

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

import com.base.rest.dtos.Log;
import com.base.rest.dtos.Usuario;
import com.base.rest.service.interfaces.LogService;
import com.base.rest.service.interfaces.UsuarioService;
import com.base.rest.views.View;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends BaseController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private LogService logService;
	
	@JsonView(View.Public.class)
	@GetMapping("/findAll")
	//@PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<Usuario>> findAll() {
        return new ResponseEntity<List<Usuario>>(usuarioService.findAll(), HttpStatus.OK);
    }
	
	@PostMapping("/save")
	//@PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> save(@RequestBody Usuario usuario) {
		usuarioService.save(usuario);
		logService.save(new Log(getCurrentUserName(), "Usuario", "Alta", "Usuario: " + usuario.getUsername(), new Date()));
        return new ResponseEntity<String>("Operación correcta", HttpStatus.OK);
    }
	
	@PutMapping("/update")
	//@PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> update(@RequestBody Usuario usuario) {
		usuarioService.update(usuario);
		logService.save(new Log(getCurrentUserName(), "Usuario", "Edición", "Usuario: " + usuario.getUsername(), new Date()));
        return new ResponseEntity<String>("Operación correcta", HttpStatus.OK);
    }
	
	@JsonView(View.Public.class)
	@GetMapping("/find")
	//@PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Usuario> findById(@RequestParam Integer id) {
		return new ResponseEntity<Usuario>(usuarioService.findById(id), HttpStatus.OK);
    }
	
	@DeleteMapping("/delete")
	//@PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> deleteById(@RequestParam Integer id) {
		Usuario usuario = usuarioService.findById(id);
		usuarioService.deleteById(id);
		logService.save(new Log(getCurrentUserName(), "Usuario", "Baja", "Usuario: " + usuario.getUsername(), new Date()));
		return new ResponseEntity<String>("Operación correcta", HttpStatus.OK);
    }
	
	@PutMapping("/deactivate")
	//@PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> deactivateById(@RequestParam Integer id) {
		Usuario usuario = usuarioService.findById(id);
		usuarioService.deactivateById(id);
		logService.save(new Log(getCurrentUserName(), "Usuario", "Desactivar", "Usuario: " + usuario.getUsername(), new Date()));
		return new ResponseEntity<String>("Operación correcta", HttpStatus.OK);
    }
	
	@PutMapping("/activate")
	//@PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> activateById(@RequestParam Integer id) {
		Usuario usuario = usuarioService.findById(id);
		usuarioService.activateById(id);
		logService.save(new Log(getCurrentUserName(), "Usuario", "Activar", "Usuario: " + usuario.getUsername(), new Date()));
		return new ResponseEntity<String>("Operación correcta", HttpStatus.OK);
    }
	
	@PutMapping("/cambioPassword")
	//@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<String> cambioPasswordUser(@RequestParam Integer id, @RequestParam String oldPassword, @RequestParam String newPassword) {
		Usuario usuario = usuarioService.findById(id);
		usuarioService.cambioPasswordUser(id, oldPassword, newPassword);
		logService.save(new Log(getCurrentUserName(), "Usuario", "Cambiar Password Usuario", "Usuario: " + usuario.getUsername(), new Date()));
		return new ResponseEntity<String>("Operación correcta", HttpStatus.OK);
    }
	
	@PutMapping("/cambioPasswordAdmin")
	public ResponseEntity<String> cambioPasswordAdmin(@RequestParam Integer id, @RequestParam String newPassword) {
		Usuario usuario = usuarioService.findById(id);
		usuarioService.cambioPasswordAdmin(id, newPassword);
		logService.save(new Log(getCurrentUserName(), "Usuario", "Cambiar Password Administrador", "Usuario: " + usuario.getUsername(), new Date()));
		return new ResponseEntity<String>("Operación correcta", HttpStatus.OK);
    }
}
