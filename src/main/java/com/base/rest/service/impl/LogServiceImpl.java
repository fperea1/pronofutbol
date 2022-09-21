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
import com.base.rest.dtos.LogDTO;
import com.base.rest.dtos.ResultTableDTO;
import com.base.rest.entities.BaseEntity;
import com.base.rest.entities.Log;
import com.base.rest.repositories.LogRepository;
import com.base.rest.service.interfaces.LogService;
import com.base.rest.specification.BaseSpecificationsBuilder;
import com.base.rest.utils.Converter;
import com.base.rest.utils.bd.FiltroTablasView;
import com.base.rest.utils.bd.FiltrosUtils;
import com.base.rest.utils.bd.SearchCriteriaColumn;

@Service
@Transactional(readOnly = true)
public class LogServiceImpl implements LogService {
	
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
        
		return converterDTO.convertList(logRepository.findAll(spec, pageable));
	}

}
