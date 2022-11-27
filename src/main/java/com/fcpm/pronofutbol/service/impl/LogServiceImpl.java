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
public class LogServiceImpl extends BaseServiceImpl implements LogService {
	
	@Autowired
	private LogRepository logRepository;
	
	private Converter<Log, LogDTO> converterDTO;

	public LogServiceImpl() {
		super();
		converterDTO = new Converter<Log, LogDTO>(Log.class, LogDTO.class);
	}

	@Transactional
	@Override
	public void save(Log log) {
		
		logRepository.save(log);
	}

	@Override
	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar) {
		
		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);
		
		Specification<BaseEntity> spec = getSpecification(filtro);
        
		Pageable pageable = getPageable(exportar, filtro);
        
		return converterDTO.convertList(logRepository.findAll(spec, pageable));
	}

}
