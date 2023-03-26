package com.fcpm.pronofutbol.service.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcpm.pronofutbol.dtos.ArbitroDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;
import com.fcpm.pronofutbol.entities.Arbitro;
import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.Liga;
import com.fcpm.pronofutbol.repositories.ArbitroRepository;
import com.fcpm.pronofutbol.service.interfaces.ArbitroService;
import com.fcpm.pronofutbol.utils.Converter;
import com.fcpm.pronofutbol.utils.bd.FiltroTablasView;
import com.fcpm.pronofutbol.utils.bd.FiltrosUtils;
import com.fcpm.pronofutbol.utils.bd.SearchCriteriaColumn;

import jakarta.persistence.criteria.Join;

@Service
@Transactional(readOnly = true)
public class ArbitroServiceImpl extends RepositoryServiceImpl<Arbitro, Integer> implements ArbitroService {

	private Converter<ArbitroDTO, Arbitro> toEntity;

	private Converter<Arbitro, ArbitroDTO> toDTO;

	public ArbitroServiceImpl(ArbitroRepository repository) {
		super(repository);
		toEntity = new Converter<>(ArbitroDTO.class, Arbitro.class);
		toDTO = new Converter<>(Arbitro.class, ArbitroDTO.class);
	}

	@Transactional
	@Override
	public void crear(ArbitroDTO arbitro) {

		save((Arbitro) toEntity.toEntity(arbitro));
	}

	@Transactional
	@Override
	public void actualizar(Integer id, ArbitroDTO arbitro) {

		update(id, (Arbitro) toEntity.toEntity(arbitro));
	}

	@Override
	public ArbitroDTO getById(Integer id) {

		return (ArbitroDTO) toDTO.toDTO(findById(id));
	}

	@Transactional
	@Override
	public void borrar(Integer id) {
		deleteById(id);
	}
	
	@Override
	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar) {

		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);

		Specification<BaseEntity> spec = getSpecification(filtro);

		if ((filtro.getFilters() != null) && !filtro.getFilters().isEmpty()) {
			List<SearchCriteriaColumn> params = FiltrosUtils.getFiltrosSelect(filtro.getFilters());
			for (SearchCriteriaColumn scc : params) {
				spec = Specification.where(spec).or(hasLigaConNombre(scc.getValue().toString()));
			}
		}

		Pageable pageable = getPageable(exportar, filtro);
        
        return toDTO.convertToResultTableDTO(((ArbitroRepository)repository).findAll(spec, pageable));
	}

	private Specification<BaseEntity> hasLigaConNombre(String nombre) {
		return (root, query, criteriaBuilder) -> {
			Join<Liga, Arbitro> liga = root.join("liga");
			return criteriaBuilder.equal(liga.get("nombre"), nombre);
		};
	}

	@Override
	public List<SelectDTO> findForSelect() {
		
		Sort sort = Sort.by("nombre").ascending();

		return toDTO.convertListToSelectDTO(repository.findAll(sort));
	}
}
