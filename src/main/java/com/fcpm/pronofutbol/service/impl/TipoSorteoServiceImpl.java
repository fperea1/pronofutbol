package com.fcpm.pronofutbol.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.TipoSorteoDTO;
import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.TipoSorteo;
import com.fcpm.pronofutbol.exceptions.ServiceException;
import com.fcpm.pronofutbol.repositories.TipoSorteoRepository;
import com.fcpm.pronofutbol.service.interfaces.TipoSorteoService;
import com.fcpm.pronofutbol.utils.Converter;
import com.fcpm.pronofutbol.utils.bd.FiltroTablasView;
import com.fcpm.pronofutbol.utils.bd.FiltrosUtils;

@Service
@Transactional(readOnly = true)
public class TipoSorteoServiceImpl extends BaseServiceImpl implements TipoSorteoService {

	@Autowired
	private TipoSorteoRepository repository;
	
	private Converter<TipoSorteo, TipoSorteoDTO> toDTO;
	
	private Converter<TipoSorteoDTO, TipoSorteo> toEntity;
	
	public TipoSorteoServiceImpl() {
		super();
		toEntity = new Converter<TipoSorteoDTO, TipoSorteo>(TipoSorteoDTO.class, TipoSorteo.class);
		toDTO = new Converter<TipoSorteo, TipoSorteoDTO>(TipoSorteo.class, TipoSorteoDTO.class);
	}

	@Override
	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar) {
		
		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);
		
		Specification<BaseEntity> spec = getSpecification(filtro);
        
		Pageable pageable = getPageable(exportar, filtro);
        
		return toDTO.convertToResultTableDTO(repository.findAll(spec, pageable));
	}

	@Override
	public void save(TipoSorteoDTO tipoSorteo) {
		
		repository.save((TipoSorteo) toEntity.toEntity(tipoSorteo));
	}

	@Override
	public TipoSorteoDTO getById(Integer id) {
		
		if (!repository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		return (TipoSorteoDTO) toDTO.toDTO(repository.findById(id).orElse(null));
	}

	@Override
	public void update(TipoSorteoDTO tipoSorteo) {
		
		repository.save((TipoSorteo) toEntity.toEntity(tipoSorteo));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoSorteoDTO> findAll() {
		
		return (List<TipoSorteoDTO>) toDTO.convertToListDTO(repository.findAll());
	}
	
}
