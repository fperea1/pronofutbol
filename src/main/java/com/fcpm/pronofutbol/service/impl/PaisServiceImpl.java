package com.fcpm.pronofutbol.service.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcpm.pronofutbol.dtos.PaisDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;
import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.Pais;
import com.fcpm.pronofutbol.repositories.PaisRepository;
import com.fcpm.pronofutbol.service.interfaces.PaisService;
import com.fcpm.pronofutbol.utils.Converter;
import com.fcpm.pronofutbol.utils.bd.FiltroTablasView;
import com.fcpm.pronofutbol.utils.bd.FiltrosUtils;

@Service
@Transactional(readOnly = true)
public class PaisServiceImpl extends RepositoryServiceImpl<Pais, Integer> implements PaisService {
	
	private Converter<Pais, PaisDTO> toDTO;
	
	private Converter<PaisDTO, Pais> toEntity;
	
	public PaisServiceImpl(PaisRepository repository) {
		super(repository);
		toEntity = new Converter<>(PaisDTO.class, Pais.class);
		toDTO = new Converter<>(Pais.class, PaisDTO.class);
	}
	
	@Override
	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar) {
		
		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);
		
		Specification<BaseEntity> spec = getSpecification(filtro);
        
		Pageable pageable = getPageable(exportar, filtro);
        
		return toDTO.convertToResultTableDTO(((PaisRepository)repository).findAll(spec, pageable));
	}

	@Transactional
	@Override
	public void crear(PaisDTO pais) {
		
		save((Pais) toEntity.toEntity(pais));
	}

	@Override
	public PaisDTO getById(Integer id) {
		
		return (PaisDTO) toDTO.toDTO(findById(id));
	}

	@Transactional
	@Override
	public void actualizar(Integer id, PaisDTO pais) {
		
		update(id, (Pais) toEntity.toEntity(pais));
	}

	@Transactional
	@Override
	public void borrar(Integer id) {
		
		deleteById(id);
	}

	@Override
	public List<SelectDTO> findForSelect() {
		
		Sort sort = Sort.by("nombre").ascending();
		
		return toDTO.convertListToSelectDTO(repository.findAll(sort));
	}

}
