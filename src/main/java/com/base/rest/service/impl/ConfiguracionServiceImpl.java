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
import com.base.rest.dtos.ConfiguracionDTO;
import com.base.rest.entities.BaseEntity;
import com.base.rest.entities.Configuracion;
import com.base.rest.exceptions.ServiceException;
import com.base.rest.repositories.ConfiguracionRepository;
import com.base.rest.service.interfaces.ConfiguracionService;
import com.base.rest.specification.BaseSpecificationsBuilder;
import com.base.rest.utils.Converter;
import com.base.rest.utils.bd.FiltroTablasView;
import com.base.rest.utils.bd.FiltrosUtils;
import com.base.rest.utils.bd.SearchCriteriaColumn;

@Service
@Transactional(readOnly = true)
public class ConfiguracionServiceImpl implements ConfiguracionService {

	@Autowired
	private ConfiguracionRepository configuracionRepository;
	
	private Converter<ConfiguracionDTO, Configuracion> converterEntity = new Converter<ConfiguracionDTO, Configuracion>();
	
	private Converter<Configuracion, ConfiguracionDTO> converterDTO = new Converter<Configuracion, ConfiguracionDTO>();

	@Override
	public ConfiguracionDTO getByNombre(String nombre) {
		
		return (ConfiguracionDTO) converterDTO.toDTO(configuracionRepository
				.getByNombre(nombre), Configuracion.class, ConfiguracionDTO.class);
	}
	
	@Override
	public List<BaseDTO> findByFilter(String filtroWeb, boolean exportar) {
		
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
        
        return (List<BaseDTO>) converterDTO.convertList(configuracionRepository
        		.findAll(spec, pageable), Configuracion.class, ConfiguracionDTO.class);
        
	}

	@Transactional
	@Override
	public void save(ConfiguracionDTO configuracion) {

		configuracionRepository.save((Configuracion) converterEntity.toEntity(configuracion, ConfiguracionDTO.class, Configuracion.class));
	}

	@Transactional
	@Override
	public void update(ConfiguracionDTO configuracion) {

		configuracionRepository.save((Configuracion) converterEntity.toEntity(configuracion, ConfiguracionDTO.class, Configuracion.class));
	}

	@Override
	public ConfiguracionDTO findById(Integer id) {
		
		if (!configuracionRepository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		return (ConfiguracionDTO) converterDTO.toDTO(configuracionRepository
				.findById(id).orElse(null), Configuracion.class, ConfiguracionDTO.class);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) {
		
		if (!configuracionRepository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		configuracionRepository.deleteById(id);
	}
}
