package com.fcpm.pronofutbol.service.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcpm.pronofutbol.dtos.LigaDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;
import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.Liga;
import com.fcpm.pronofutbol.entities.Quiniela;
import com.fcpm.pronofutbol.repositories.LigaRepository;
import com.fcpm.pronofutbol.service.interfaces.LigaService;
import com.fcpm.pronofutbol.utils.Converter;
import com.fcpm.pronofutbol.utils.bd.FiltroTablasView;
import com.fcpm.pronofutbol.utils.bd.FiltrosUtils;
import com.fcpm.pronofutbol.utils.bd.SearchCriteriaColumn;

import jakarta.persistence.criteria.Join;

@Service
@Transactional(readOnly = true)
public class LigaServiceImpl extends RepositoryServiceImpl<Liga, Integer> implements LigaService {
	
	private Converter<Liga, LigaDTO> toDTO;
	
	private Converter<LigaDTO, Liga> toEntity;
	
	public LigaServiceImpl(LigaRepository repository) {
		super(repository);
		toEntity = new Converter<>(LigaDTO.class, Liga.class);
		toDTO = new Converter<>(Liga.class, LigaDTO.class);
	}
	
	@Override
	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar) {
		
		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);
		
		Specification<BaseEntity> spec = getSpecification(filtro);
		
		if (filtro.getFilters() != null && !filtro.getFilters().isEmpty()) {
			List<SearchCriteriaColumn> params = FiltrosUtils.getFiltrosSelect(filtro.getFilters());
			for (SearchCriteriaColumn scc: params) {
				spec = Specification.where(spec).or(hasPaisConNombre(scc.getValue().toString()));
			}
		}
        
		Pageable pageable = getPageable(exportar, filtro);
        
		return toDTO.convertToResultTableDTO(((LigaRepository)repository).findAll(spec, pageable));
	}
	
	private Specification<BaseEntity> hasPaisConNombre(String nombre) {
	    return (root, query, criteriaBuilder) -> {
	        Join<Liga, Quiniela> liga = root.join("pais");
	        return criteriaBuilder.equal(liga.get("nombre"), nombre);
	    };
	}

	@Transactional
	@Override
	public void crear(LigaDTO liga) {
		
		save((Liga) toEntity.toEntity(liga));
	}

	@Override
	public LigaDTO getById(Integer id) {
		
		return (LigaDTO) toDTO.toDTO(findById(id));
	}

	@Transactional
	@Override
	public void actualizar(Integer id, LigaDTO liga) {
		
		update(id, (Liga) toEntity.toEntity(liga));
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
