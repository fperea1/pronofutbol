package com.base.rest.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.rest.constant.Constantes;
import com.base.rest.entities.BaseEntity;
import com.base.rest.entities.Usuario;
import com.base.rest.exceptions.EntityNoExistsException;
import com.base.rest.exceptions.PasswordLimitException;
import com.base.rest.repositories.UsuarioRepository;
import com.base.rest.service.interfaces.UsuarioService;
import com.base.rest.specification.BaseSpecificationsBuilder;
import com.base.rest.utils.bd.FiltroTablasView;
import com.base.rest.utils.bd.FiltrosUtils;
import com.base.rest.utils.bd.SearchCriteriaColumn;

@Service
@Transactional(readOnly = true)
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;


	
	@Override
	public Page<BaseEntity> findByFilter(String filtroWeb, boolean exportar) {
		
		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);
		// en principio se ordenan de último a primero
		Sort sort = Sort.by("id").descending();
		// En caso de tener un orden específico se cambiaría
		if (filtro != null && filtro.getSortField() != null && !filtro.getSortField().isBlank()) {
			sort = (filtro.getSortOrder() > 0) ? Sort.by(filtro.getSortField()).ascending() : Sort.by(filtro.getSortField()).descending();
		}
		Pageable pageable = null;
		if (!exportar) {
			pageable = PageRequest.of((filtro.getFirst() > 0 ? filtro.getFirst() / filtro.getRows() : filtro.getFirst()), filtro.getRows(), sort);
		} else {
			pageable = PageRequest.of(0, Constantes.MAX_ROWS_XLSX, sort);
		}
		
		BaseSpecificationsBuilder builder = new BaseSpecificationsBuilder();
		if (filtro.getFilters() != null && !filtro.getFilters().isEmpty()) {
			List<SearchCriteriaColumn> params = FiltrosUtils.getFiltrosColumns(filtro.getFilters());
			builder.with(params);
		}
        Specification<BaseEntity> spec = builder.build();
        
		return usuarioRepository.findAll(spec, pageable);
	}

	@Transactional
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

	@Transactional
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

	@Transactional
	@Override
	public void deleteById(Integer id) {
		
		if (!usuarioRepository.existsById(id)) {
			throw new EntityNoExistsException();
		}
		usuarioRepository.deleteById(id);
	}

	@Transactional
	@Override
	public void deactivateById(Integer id) {
		
		if (!usuarioRepository.existsById(id)) {
			throw new EntityNoExistsException();
		}
		usuarioRepository.deactivateById(new Date(), id);
	}

	@Transactional
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

	@Transactional
	@Override
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

	@Transactional
	@Override
	public void cambioPasswordAdmin(Integer id, String newPassword) {
		
		Usuario u = findById(id);
		if (u == null) {
			throw new EntityNoExistsException();
		}
		validarPassword(newPassword);
		usuarioRepository.changePassword(bcryptEncoder.encode(newPassword), id);
	}

}
