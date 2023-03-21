package com.fcpm.pronofutbol.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.UsuarioDTO;
import com.fcpm.pronofutbol.dtos.UsuarioListadoDTO;
import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.Rol;
import com.fcpm.pronofutbol.entities.Usuario;
import com.fcpm.pronofutbol.exceptions.ServiceException;
import com.fcpm.pronofutbol.repositories.UsuarioRepository;
import com.fcpm.pronofutbol.service.interfaces.UsuarioService;
import com.fcpm.pronofutbol.utils.Converter;
import com.fcpm.pronofutbol.utils.bd.FiltroTablasView;
import com.fcpm.pronofutbol.utils.bd.FiltrosUtils;
import com.fcpm.pronofutbol.utils.bd.SearchCriteriaColumn;

import jakarta.persistence.criteria.Join;

@Service
@Transactional(readOnly = true)
public class UsuarioServiceImpl extends RepositoryServiceImpl<Usuario, Integer> implements UsuarioService {

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	private Converter<UsuarioDTO, Usuario> toEntity;
	
	private Converter<Usuario, UsuarioDTO> toDTO;
	
	private Converter<Usuario, UsuarioListadoDTO> converterListadoDTO;

	public UsuarioServiceImpl(UsuarioRepository repository) {
		super(repository);
		toEntity = new Converter<>(UsuarioDTO.class, Usuario.class);
		toDTO = new Converter<>(Usuario.class, UsuarioDTO.class);
		converterListadoDTO = new Converter<>(Usuario.class, UsuarioListadoDTO.class);
	}

	@Override
	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar) {
		
		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);
		
		Specification<BaseEntity> spec = getSpecification(filtro);
		if (filtro.getFilters() != null && !filtro.getFilters().isEmpty()) {
			List<SearchCriteriaColumn> params = FiltrosUtils.getFiltrosSelect(filtro.getFilters());
			for (SearchCriteriaColumn scc: params) {
				spec = Specification.where(spec).or(hasRolConNombre(scc.getValue().toString()));
			}
		}
        
		Pageable pageable = getPageable(exportar, filtro);
        
		return converterListadoDTO.convertToResultTableDTO(((UsuarioRepository)repository).findAll(spec, pageable));
	}
	
	private Specification<BaseEntity> hasRolConNombre(String nombre) {
	    return (root, query, criteriaBuilder) -> {
	        Join<Rol, Usuario> roles = root.join("roles");
	        return criteriaBuilder.equal(roles.get("nombre"), nombre);
	    };
	}

	@Transactional
	@Override
	public void crear(UsuarioDTO usuario) {

		validarPassword(usuario.getPassword(), null);
		usuario.setPassword(bcryptEncoder.encode(usuario.getPassword()));
		if (usuario.getId() == null) {
			usuario.setFechaAlta(new Date());
			usuario.setActivo(true);
		}
		save((Usuario) toEntity.toEntity(usuario));
	}

	@Transactional
	@Override
	public void actualizar(Integer id, UsuarioDTO usuario) {
		
		update(id, (Usuario) toEntity.toEntity(usuario));
	}

	@Override
	public UsuarioDTO getById(Integer id) {
		
		return (UsuarioDTO) toDTO.toDTO(findById(id));
	}

	@Transactional
	@Override
	public void deactivate(Integer id) {
		
		if (!repository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		((UsuarioRepository)repository).deactivate(new Date(), id);
	}

	@Transactional
	@Override
	public void activate(Integer id) {
		
		if (!repository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		((UsuarioRepository)repository).activate(id);
	}
	
	private void validarPassword(String password, String newPassword2) {
		
		if (newPassword2 != null && !password.equals(newPassword2)) {
			throw new ServiceException(Constantes.EXC_PASSWORDS_DIFERENTES);
		}
		
		if (password == null || password.isBlank()
				|| password.length() < 10 || password.length() > 100) {
			throw new ServiceException(Constantes.EXC_LIMITE_CARACTERES_PASSWORD);
		}
	}

	@Transactional
	@Override
	public void cambioPasswordUser(Integer id, String username, String oldPassword, String newPassword, String newPassword2) {
		
		if (oldPassword == null) {
			throw new ServiceException(Constantes.EXC_PASSWORD_ANTERIOR_OBLIGATORIO);
		}
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username, oldPassword));
		} catch (AuthenticationException e) {
			throw new ServiceException(Constantes.EXC_PASSWORD_ANT_ERRONEA);
		}
		validarPassword(newPassword, newPassword2);
		((UsuarioRepository)repository).changePassword(bcryptEncoder.encode(newPassword), id);
	}

	@Transactional
	@Override
	public void cambioPasswordAdmin(Integer id, String newPassword, String newPassword2) {
		
		UsuarioDTO u = getById(id);
		if (u == null) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		validarPassword(newPassword, newPassword2);
		((UsuarioRepository)repository).changePassword(bcryptEncoder.encode(newPassword), id);
	}

	@Override
	public UsuarioDTO findByUsername(String username) {
		
		return (UsuarioDTO) toDTO.toDTO(((UsuarioRepository)repository).getByUsername(username));
	}

	@Transactional
	@Override
	public void borrar(Integer id) {
		
		deleteById(id);
	}

}
