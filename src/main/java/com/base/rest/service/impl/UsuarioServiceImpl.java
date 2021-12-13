package com.base.rest.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.base.rest.entities.Usuario;
import com.base.rest.exceptions.EntityNoExistsException;
import com.base.rest.exceptions.PasswordLimitException;
import com.base.rest.repositories.UsuarioRepository;
import com.base.rest.service.interfaces.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public List<Usuario> findAll() {
		
		return usuarioRepository.findAll();
	}

	@Override
	public void save(Usuario usuario) {

		validarPassword(usuario.getPassword());
		usuario.setPassword(bcryptEncoder.encode(usuario.getPassword()));
		if (usuario.getId() == null) {
			usuario.setFechaAlta(new Date());
			usuario.setActivo(true);
		}
		usuarioRepository.save(usuario);
	}

	@Override
	public void update(Usuario usuario) {

		usuario.setPassword(usuarioRepository.getPassword(usuario.getId()));
		usuarioRepository.save(usuario);
	}

	@Override
	public Usuario findById(Integer id) {
		
		if (!usuarioRepository.existsById(id)) {
			throw new EntityNoExistsException();
		}
		return usuarioRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteById(Integer id) {
		
		if (!usuarioRepository.existsById(id)) {
			throw new EntityNoExistsException();
		}
		usuarioRepository.deleteById(id);
	}

	@Override
	public void deactivateById(Integer id) {
		
		if (!usuarioRepository.existsById(id)) {
			throw new EntityNoExistsException();
		}
		usuarioRepository.deactivateById(new Date(), id);
	}

	@Override
	public void activateById(Integer id) {
		
		if (!usuarioRepository.existsById(id)) {
			throw new EntityNoExistsException();
		}
		usuarioRepository.activateById(id);
	}
	
	private void validarPassword(String password) {
		
		if (password == null || password.isBlank()
				|| password.length() < 10 || password.length() > 100) {
			throw new PasswordLimitException();
		}
	}

	public void cambioPasswordUser(Integer id, String username, String oldPassword, String newPassword) {
		
		Usuario u = findById(id);
		if (u == null) {
			throw new EntityNoExistsException();
		} 
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, oldPassword));
		validarPassword(newPassword);
		usuarioRepository.changePassword(bcryptEncoder.encode(newPassword), id);
	}

	public void cambioPasswordAdmin(Integer id, String newPassword) {
		
		Usuario u = findById(id);
		if (u == null) {
			throw new EntityNoExistsException();
		}
		validarPassword(newPassword);
		usuarioRepository.changePassword(bcryptEncoder.encode(newPassword), id);
	}

}
