package com.base.rest.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.base.rest.dtos.Usuario;
import com.base.rest.exceptions.EntityNoExistsException;
import com.base.rest.exceptions.PasswordException;
import com.base.rest.repositories.UsuarioRepository;
import com.base.rest.service.interfaces.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

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

		//validarPassword(usuario.getPassword());
		//usuario.setPassword(bcryptEncoder.encode(usuario.getPassword()));
		usuarioRepository.save(usuario);
	}

	@Override
	public Usuario findById(Integer id) {
		
		if (!usuarioRepository.existsById(id)) {
			throw new EntityNoExistsException();
		}
		return usuarioRepository.findById(id).get();
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
			throw new PasswordException();
		}
	}

	public void cambioPasswordUser(Integer id, String oldPassword, String newPassword) {
		
		Usuario u = findById(id);
		if (!u.getPassword().equals(bcryptEncoder.encode(oldPassword))) {
			throw new EntityNoExistsException();
		}
		validarPassword(newPassword);
		usuarioRepository.changePassword(bcryptEncoder.encode(newPassword), id);
	}

	public void cambioPasswordAdmin(Integer id, String newPassword) {
		
		@SuppressWarnings("unused")
		Usuario u = findById(id);
		validarPassword(newPassword);
		usuarioRepository.changePassword(bcryptEncoder.encode(newPassword), id);
	}

}
