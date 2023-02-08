package com.fcpm.pronofutbol.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.ArbitroDTO;
import com.fcpm.pronofutbol.dtos.BaseDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.entities.Arbitro;
import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.Liga;
import com.fcpm.pronofutbol.entities.Quiniela;
import com.fcpm.pronofutbol.exceptions.ServiceException;
import com.fcpm.pronofutbol.repositories.ArbitroRepository;
import com.fcpm.pronofutbol.service.interfaces.ArbitroService;
import com.fcpm.pronofutbol.utils.Converter;
import com.fcpm.pronofutbol.utils.bd.FiltroTablasView;
import com.fcpm.pronofutbol.utils.bd.FiltrosUtils;
import com.fcpm.pronofutbol.utils.bd.SearchCriteriaColumn;

import jakarta.persistence.criteria.Join;

@Service
@Transactional(readOnly = true)
public class ArbitroServiceImpl extends BaseServiceImpl implements ArbitroService {
	
	@Autowired
	private ArbitroRepository repository;
	
	private Converter<ArbitroDTO, Arbitro> toEntity;
	
	private Converter<Arbitro, ArbitroDTO> toDTO;

	public ArbitroServiceImpl() {
		super();
		toEntity = new Converter<>(ArbitroDTO.class, Arbitro.class);
		toDTO = new Converter<>(Arbitro.class, ArbitroDTO.class);
	}

	@Override
	public void save(ArbitroDTO arbitro) {

		repository.save((Arbitro) toEntity.toEntity(arbitro));
	}

	@Override
	public void update(ArbitroDTO arbitro) {

		repository.save((Arbitro) toEntity.toEntity(arbitro));
	}

	@Override
	public ArbitroDTO getById(Integer id) {
		
		if (!repository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		return (ArbitroDTO) toDTO.toDTO(repository.findById(id).orElse(null));
	}

	@Override
	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar) {
		
		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);
		
		Specification<BaseEntity> spec = getSpecification(filtro);
		
		if (filtro.getFilters() != null && !filtro.getFilters().isEmpty()) {
			List<SearchCriteriaColumn> params = FiltrosUtils.getFiltrosSelect(filtro.getFilters());
			for (SearchCriteriaColumn scc: params) {
				spec = Specification.where(spec).or(hasLigaConNombre(scc.getValue().toString()));
			}
		}
        
		Pageable pageable = getPageable(exportar, filtro);
        
        return toDTO.convertToResultTableDTO(repository.findAll(spec, pageable));
	}
	
	private Specification<BaseEntity> hasLigaConNombre(String nombre) {
	    return (root, query, criteriaBuilder) -> {
	        Join<Liga, Quiniela> liga = root.join("liga");
	        return criteriaBuilder.equal(liga.get("nombre"), nombre);
	    };
	}

	@Override
	public void delete(Integer id) {
		
		repository.deleteById(id);
	}

	@Override
	public List<BaseDTO> findForSelect() {
		
		Sort sort = Sort.by("nombre").ascending();
		
		return toDTO.convertListToSelectDTO(repository.findAll(sort));
	}
}
