package com.fcpm.pronofutbol.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.ConfiguracionDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.Configuracion;
import com.fcpm.pronofutbol.exceptions.ServiceException;
import com.fcpm.pronofutbol.repositories.ConfiguracionRepository;
import com.fcpm.pronofutbol.service.interfaces.ConfiguracionService;
import com.fcpm.pronofutbol.utils.Converter;
import com.fcpm.pronofutbol.utils.bd.FiltroTablasView;
import com.fcpm.pronofutbol.utils.bd.FiltrosUtils;

@Service
@Transactional(readOnly = true)
public class ConfiguracionServiceImpl extends BaseServiceImpl implements ConfiguracionService {

	@Autowired
	private ConfiguracionRepository repository;
	
	private Converter<ConfiguracionDTO, Configuracion> toEntity;
	
	private Converter<Configuracion, ConfiguracionDTO> toDTO;

	public ConfiguracionServiceImpl() {
		super();
		toEntity = new Converter<>(ConfiguracionDTO.class, Configuracion.class);
		toDTO = new Converter<>(Configuracion.class, ConfiguracionDTO.class);
	}

	@Override
	public ConfiguracionDTO getByNombre(String nombre) {
		
		return (ConfiguracionDTO) toDTO.toDTO(repository.getByNombre(nombre));
	}
	
	@Override
	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar) {
		
		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);
		
		Specification<BaseEntity> spec = getSpecification(filtro);
        
		Pageable pageable = getPageable(exportar, filtro);
        
        return toDTO.convertToResultTableDTO(repository.findAll(spec, pageable));
        
	}

	@Transactional
	@Override
	public void save(ConfiguracionDTO configuracion) {

		repository.save((Configuracion) toEntity.toEntity(configuracion));
	}

	@Transactional
	@Override
	public void update(ConfiguracionDTO configuracion) {

		repository.save((Configuracion) toEntity.toEntity(configuracion));
	}

	@Override
	public ConfiguracionDTO getById(Integer id) {
		
		if (!repository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		return (ConfiguracionDTO) toDTO.toDTO(repository.findById(id).orElse(null));
	}

	@Transactional
	@Override
	public void deleteById(Integer id) {
		
		if (!repository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		repository.deleteById(id);
	}
}
