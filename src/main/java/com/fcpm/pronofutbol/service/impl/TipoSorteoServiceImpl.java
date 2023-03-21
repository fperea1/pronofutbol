package com.fcpm.pronofutbol.service.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;
import com.fcpm.pronofutbol.dtos.TipoSorteoDTO;
import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.TipoSorteo;
import com.fcpm.pronofutbol.repositories.TipoSorteoRepository;
import com.fcpm.pronofutbol.service.interfaces.TipoSorteoService;
import com.fcpm.pronofutbol.utils.Converter;
import com.fcpm.pronofutbol.utils.bd.FiltroTablasView;
import com.fcpm.pronofutbol.utils.bd.FiltrosUtils;

@Service
@Transactional(readOnly = true)
public class TipoSorteoServiceImpl extends RepositoryServiceImpl<TipoSorteo, Integer> implements TipoSorteoService {

	private Converter<TipoSorteo, TipoSorteoDTO> toDTO;
	
	private Converter<TipoSorteoDTO, TipoSorteo> toEntity;
	
	public TipoSorteoServiceImpl(TipoSorteoRepository repository) {
		super(repository);
		toEntity = new Converter<>(TipoSorteoDTO.class, TipoSorteo.class);
		toDTO = new Converter<>(TipoSorteo.class, TipoSorteoDTO.class);
	}

	@Override
	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar) {
		
		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);
		
		Specification<BaseEntity> spec = getSpecification(filtro);
        
		Pageable pageable = getPageable(exportar, filtro);
        
		return toDTO.convertToResultTableDTO(((TipoSorteoRepository)repository).findAll(spec, pageable));
	}

	@Transactional
	@Override
	public void crear(TipoSorteoDTO tipoSorteo) {
		
		save((TipoSorteo) toEntity.toEntity(tipoSorteo));
	}

	@Override
	public TipoSorteoDTO getById(Integer id) {
		
		return (TipoSorteoDTO) toDTO.toDTO(findById(id));
	}

	@Transactional
	@Override
	public void actualizar(Integer id, TipoSorteoDTO tipoSorteo) {
		
		update(id, (TipoSorteo) toEntity.toEntity(tipoSorteo));
	}
	
	@Override
	public List<SelectDTO> findForSelect() {
		
		Sort sort = Sort.by("nombre").ascending();
		
		return toDTO.convertListToSelectDTO(repository.findAll(sort));
	}

	@Transactional
	@Override
	public void borrar(Integer id) {
		
		deleteById(id);
	}
}
