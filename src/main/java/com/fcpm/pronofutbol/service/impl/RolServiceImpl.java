package com.fcpm.pronofutbol.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.RolDTO;
import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.Rol;
import com.fcpm.pronofutbol.repositories.RolRepository;
import com.fcpm.pronofutbol.service.interfaces.RolService;
import com.fcpm.pronofutbol.specification.BaseSpecificationsBuilder;
import com.fcpm.pronofutbol.utils.Converter;

@Service
@Transactional(readOnly = true)
public class RolServiceImpl implements RolService {

	@Autowired
	private RolRepository rolRepository;
	
	private Converter<Rol, RolDTO> converterDTO;
	
	public RolServiceImpl() {
		super();
		converterDTO = new Converter<Rol, RolDTO>(Rol.class, RolDTO.class);
	}

	@Override
	public ResultTableDTO findAll() {
		
		Sort sort = Sort.by("id").ascending();
		
		Pageable pageable = PageRequest.of(0, Constantes.MAX_ROWS_XLSX, sort);
		
		BaseSpecificationsBuilder builder = new BaseSpecificationsBuilder();
        Specification<BaseEntity> spec = builder.build();
        
		return converterDTO.convertList(rolRepository.findAll(spec, pageable));
	}

}
