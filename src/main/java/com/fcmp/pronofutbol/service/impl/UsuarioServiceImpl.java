package com.fcmp.pronofutbol.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Join;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcmp.pronofutbol.constant.Constantes;
import com.fcmp.pronofutbol.dtos.ResultTableDTO;
import com.fcmp.pronofutbol.dtos.UsuarioDTO;
import com.fcmp.pronofutbol.dtos.UsuarioListadoDTO;
import com.fcmp.pronofutbol.entities.BaseEntity;
import com.fcmp.pronofutbol.entities.Rol;
import com.fcmp.pronofutbol.entities.Usuario;
import com.fcmp.pronofutbol.exceptions.ServiceException;
import com.fcmp.pronofutbol.repositories.UsuarioRepository;
import com.fcmp.pronofutbol.service.interfaces.UsuarioService;
import com.fcmp.pronofutbol.utils.Converter;
import com.fcmp.pronofutbol.utils.bd.FiltroTablasView;
import com.fcmp.pronofutbol.utils.bd.FiltrosUtils;
import com.fcmp.pronofutbol.utils.bd.SearchCriteriaColumn;

@Service
@Transactional(readOnly = true)
public class UsuarioServiceImpl extends BaseServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	private Converter<UsuarioDTO, Usuario> converterEntity;
	
	private Converter<Usuario, UsuarioDTO> converterDTO;
	
	private Converter<Usuario, UsuarioListadoDTO> converterListadoDTO;

	public UsuarioServiceImpl() {
		super();
		converterEntity = new Converter<UsuarioDTO, Usuario>(UsuarioDTO.class, Usuario.class);
		converterDTO = new Converter<Usuario, UsuarioDTO>(Usuario.class, UsuarioDTO.class);
		converterListadoDTO = new Converter<Usuario, UsuarioListadoDTO>(Usuario.class, UsuarioListadoDTO.class);
	}

	@Override
	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar) {
		
		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);
		
		Specification<BaseEntity> spec = getSpecification(filtro);
		if (filtro.getFilters() != null && !filtro.getFilters().isEmpty()) {
			List<SearchCriteriaColumn> params = FiltrosUtils.getFiltrosSelect(filtro.getFilters());
			for (SearchCriteriaColumn scc: params) {
				spec = Specification.where(spec).and(hasRolConNombre(scc.getValue().toString()));
			}
		}
        
		Pageable pageable = getPageable(exportar, filtro);
        
		return converterListadoDTO.convertList(usuarioRepository.findAll(spec, pageable));
	}
	
	private Specification<BaseEntity> hasRolConNombre(String nombre) {
	    return (root, query, criteriaBuilder) -> {
	        Join<Rol, Usuario> roles = root.join("roles");
	        return criteriaBuilder.equal(roles.get("nombre"), nombre);
	    };
	}

	@Transactional
	@Override
	public void save(UsuarioDTO usuario) {

		validarPassword(usuario.getPassword(), null);
		usuario.setPassword(bcryptEncoder.encode(usuario.getPassword()));
		if (usuario.getId() == null) {
			usuario.setFechaAlta(new Date());
			usuario.setActivo(true);
		}
		usuarioRepository.save((Usuario) converterEntity.toEntity(usuario));
	}

	@Transactional
	@Override
	public void update(UsuarioDTO usuario) {

		usuario.setPassword(usuarioRepository.getPassword(usuario.getId()));
		usuarioRepository.save((Usuario) converterEntity.toEntity(usuario));
	}

	@Override
	public UsuarioDTO findById(Integer id) {
		
		if (!usuarioRepository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		return (UsuarioDTO) converterDTO.toDTO(usuarioRepository.findById(id).orElse(null));
	}

	@Transactional
	@Override
	public void deleteById(Integer id) {
		
		if (!usuarioRepository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		usuarioRepository.deleteById(id);
	}

	@Transactional
	@Override
	public void deactivateById(Integer id) {
		
		if (!usuarioRepository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		usuarioRepository.deactivateById(new Date(), id);
	}

	@Transactional
	@Override
	public void activateById(Integer id) {
		
		if (!usuarioRepository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		usuarioRepository.activateById(id);
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
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username, oldPassword));
		} catch (AuthenticationException e) {
			throw new ServiceException(Constantes.EXC_PASSWORD_ANT_ERRONEA);
		}
		validarPassword(newPassword, newPassword2);
		usuarioRepository.changePassword(bcryptEncoder.encode(newPassword), id);
	}

	@Transactional
	@Override
	public void cambioPasswordAdmin(Integer id, String newPassword, String newPassword2) {
		
		UsuarioDTO u = findById(id);
		if (u == null) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		validarPassword(newPassword, newPassword2);
		usuarioRepository.changePassword(bcryptEncoder.encode(newPassword), id);
	}

	@Override
	public UsuarioDTO findByUsername(String username) {
		
		return (UsuarioDTO) converterDTO.toDTO(usuarioRepository.getByUsername(username));
	}

}
