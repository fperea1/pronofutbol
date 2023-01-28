package com.fcpm.pronofutbol.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcpm.pronofutbol.dtos.SelectDTO;
import com.fcpm.pronofutbol.entities.Rol;
import com.fcpm.pronofutbol.repositories.RolRepository;
import com.fcpm.pronofutbol.service.interfaces.RolService;
import com.fcpm.pronofutbol.utils.Converter;

@Service
@Transactional(readOnly = true)
public class RolServiceImpl implements RolService {

	@Autowired
	private RolRepository repository;
	
	private Converter<Rol, SelectDTO> toDTO;
	
	public RolServiceImpl() {
		super();
		toDTO = new Converter<Rol, SelectDTO>(Rol.class, SelectDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SelectDTO> findForSelect() {
		
		Sort sort = Sort.by("nombre").ascending();
		
		return (List<SelectDTO>) toDTO.convertListToSelectDTO(repository.findAll(sort));
	}

}