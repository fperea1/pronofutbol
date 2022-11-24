package com.fcmp.pronofutbol.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcmp.pronofutbol.dtos.LogDTO;
import com.fcmp.pronofutbol.dtos.ResultTableDTO;
import com.fcmp.pronofutbol.entities.BaseEntity;
import com.fcmp.pronofutbol.entities.Log;
import com.fcmp.pronofutbol.repositories.LogRepository;
import com.fcmp.pronofutbol.service.interfaces.LogService;
import com.fcmp.pronofutbol.utils.Converter;
import com.fcmp.pronofutbol.utils.bd.FiltroTablasView;
import com.fcmp.pronofutbol.utils.bd.FiltrosUtils;

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
