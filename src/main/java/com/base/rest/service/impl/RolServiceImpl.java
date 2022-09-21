package com.base.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.rest.constant.Constantes;
import com.base.rest.dtos.BaseDTO;
import com.base.rest.dtos.RolDTO;
import com.base.rest.entities.BaseEntity;
import com.base.rest.entities.Rol;
import com.base.rest.repositories.RolRepository;
import com.base.rest.service.interfaces.RolService;
import com.base.rest.specification.BaseSpecificationsBuilder;
import com.base.rest.utils.Converter;

@Service
@Transactional(readOnly = true)
public class RolServiceImpl implements RolService {

	@Autowired
	private RolRepository rolRepository;
	
	private Converter<Rol, RolDTO> converterDTO = new Converter<Rol, RolDTO>(Rol.class, RolDTO.class);
	

	@Override
	public List<BaseDTO> findAll() {
		
		Sort sort = Sort.by("id").ascending();
		
		Pageable pageable = PageRequest.of(0, Constantes.MAX_ROWS_XLSX, sort);
		
		BaseSpecificationsBuilder builder = new BaseSpecificationsBuilder();
        Specification<BaseEntity> spec = builder.build();
        
		return (List<BaseDTO>) converterDTO.convertList(rolRepository.findAll(spec, pageable));
	}

}
