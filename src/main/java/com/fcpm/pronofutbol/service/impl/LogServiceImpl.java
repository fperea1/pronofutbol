package com.fcpm.pronofutbol.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcpm.pronofutbol.dtos.LogDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.Log;
import com.fcpm.pronofutbol.repositories.LogRepository;
import com.fcpm.pronofutbol.service.interfaces.LogService;
import com.fcpm.pronofutbol.utils.Converter;
import com.fcpm.pronofutbol.utils.bd.FiltroTablasView;
import com.fcpm.pronofutbol.utils.bd.FiltrosUtils;

@Service
@Transactional(readOnly = true)
public class LogServiceImpl extends SpecificationBaseServiceImpl implements LogService {
	
	@Autowired
	private LogRepository repository;
	
	private Converter<Log, LogDTO> toDTO;

	public LogServiceImpl() {
		super();
		toDTO = new Converter<>(Log.class, LogDTO.class);
	}

	@Transactional
	@Override
	public void crear(Log log) {
		
		repository.save(log);
	}

	@Override
	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar) {
		
		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);
		
		Specification<BaseEntity> spec = getSpecification(filtro);
        
		Pageable pageable = getPageable(exportar, filtro);
        
		return toDTO.convertToResultTableDTO(repository.findAll(spec, pageable));
	}

}
