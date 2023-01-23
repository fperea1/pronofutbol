package com.fcpm.pronofutbol.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.QuinielaDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;
import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.Liga;
import com.fcpm.pronofutbol.entities.Quiniela;
import com.fcpm.pronofutbol.exceptions.ServiceException;
import com.fcpm.pronofutbol.repositories.QuinielaRepository;
import com.fcpm.pronofutbol.service.interfaces.QuinielaService;
import com.fcpm.pronofutbol.utils.Converter;
import com.fcpm.pronofutbol.utils.bd.FiltroTablasView;
import com.fcpm.pronofutbol.utils.bd.FiltrosUtils;
import com.fcpm.pronofutbol.utils.bd.SearchCriteriaColumn;

import jakarta.persistence.criteria.Join;

@Service
@Transactional(readOnly = true)
public class QuinielaServiceImpl extends BaseServiceImpl implements QuinielaService {
	
	@Autowired
	private QuinielaRepository repository;
	
	private Converter<QuinielaDTO, Quiniela> toEntity;
	
	private Converter<Quiniela, QuinielaDTO> toDTO;

	public QuinielaServiceImpl() {
		super();
		toEntity = new Converter<QuinielaDTO, Quiniela>(QuinielaDTO.class, Quiniela.class);
		toDTO = new Converter<Quiniela, QuinielaDTO>(Quiniela.class, QuinielaDTO.class);
	}

	@Override
	public void save(QuinielaDTO quiniela) {

		repository.save((Quiniela) toEntity.toEntity(quiniela));
	}

	@Override
	public void update(QuinielaDTO quiniela) {

		repository.save((Quiniela) toEntity.toEntity(quiniela));
	}

	@Override
	public QuinielaDTO getByNumero(Integer numero) {
		
		return (QuinielaDTO) toDTO.toDTO(repository.getByNumero(numero));
	}

	@Override
	public QuinielaDTO getById(Integer id) {
		
		if (!repository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		return (QuinielaDTO) toDTO.toDTO(repository.findById(id).orElse(null));
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

	@SuppressWarnings("unchecked")
	@Override
	public List<SelectDTO> findForSelect() {
		
		Sort sort = Sort.by("nombre").ascending();
		
		return (List<SelectDTO>) toDTO.convertListToSelectDTO(repository.findAll(sort));
	}
}
