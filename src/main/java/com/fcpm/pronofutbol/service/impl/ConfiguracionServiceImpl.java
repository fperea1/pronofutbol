package com.fcpm.pronofutbol.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcpm.pronofutbol.dtos.ConfiguracionDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.Configuracion;
import com.fcpm.pronofutbol.repositories.ConfiguracionRepository;
import com.fcpm.pronofutbol.service.interfaces.ConfiguracionService;
import com.fcpm.pronofutbol.utils.Converter;
import com.fcpm.pronofutbol.utils.bd.FiltroTablasView;
import com.fcpm.pronofutbol.utils.bd.FiltrosUtils;

@Service
@Transactional(readOnly = true)
public class ConfiguracionServiceImpl extends RepositoryServiceImpl<Configuracion, Integer> implements ConfiguracionService {
	
	private Converter<ConfiguracionDTO, Configuracion> toEntity;
	
	private Converter<Configuracion, ConfiguracionDTO> toDTO;
	
	public ConfiguracionServiceImpl(ConfiguracionRepository repository) {
		super(repository);
		toEntity = new Converter<>(ConfiguracionDTO.class, Configuracion.class);
		toDTO = new Converter<>(Configuracion.class, ConfiguracionDTO.class);
	}

	@Override
	public ConfiguracionDTO getByNombre(String nombre) {
		
		return (ConfiguracionDTO) toDTO.toDTO(((ConfiguracionRepository)repository).getByNombre(nombre));
	}
	
	@Override
	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar) {
		
		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);
		
		Specification<BaseEntity> spec = getSpecification(filtro);
        
		Pageable pageable = getPageable(exportar, filtro);
        
        return toDTO.convertToResultTableDTO(((ConfiguracionRepository)repository).findAll(spec, pageable));
        
	}

	@Transactional
	@Override
	public void crear(ConfiguracionDTO configuracion) {

		save((Configuracion) toEntity.toEntity(configuracion));
	}

	@Transactional
	@Override
	public void actualizar(Integer id, ConfiguracionDTO configuracion) {

		update(id, (Configuracion) toEntity.toEntity(configuracion));
	}



	@Override
	public ConfiguracionDTO getById(Integer id) {
		
		return (ConfiguracionDTO) toDTO.toDTO(findById(id));
	}

	@Transactional
	@Override
	public void borrar(Integer id) {
		deleteById(id);
	}
}
