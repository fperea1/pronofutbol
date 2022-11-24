package com.fcmp.pronofutbol.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcmp.pronofutbol.constant.Constantes;
import com.fcmp.pronofutbol.dtos.ResultTableDTO;
import com.fcmp.pronofutbol.dtos.RolDTO;
import com.fcmp.pronofutbol.entities.BaseEntity;
import com.fcmp.pronofutbol.entities.Rol;
import com.fcmp.pronofutbol.repositories.RolRepository;
import com.fcmp.pronofutbol.service.interfaces.RolService;
import com.fcmp.pronofutbol.specification.BaseSpecificationsBuilder;
import com.fcmp.pronofutbol.utils.Converter;

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
