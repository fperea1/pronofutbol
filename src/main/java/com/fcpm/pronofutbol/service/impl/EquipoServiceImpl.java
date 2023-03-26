package com.fcpm.pronofutbol.service.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcpm.pronofutbol.dtos.EquipoDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;
import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.Equipo;
import com.fcpm.pronofutbol.entities.Liga;
import com.fcpm.pronofutbol.repositories.EquipoRepository;
import com.fcpm.pronofutbol.service.interfaces.EquipoService;
import com.fcpm.pronofutbol.utils.Converter;
import com.fcpm.pronofutbol.utils.bd.FiltroTablasView;
import com.fcpm.pronofutbol.utils.bd.FiltrosUtils;
import com.fcpm.pronofutbol.utils.bd.SearchCriteriaColumn;

import jakarta.persistence.criteria.Join;

@Service
@Transactional(readOnly = true)
public class EquipoServiceImpl extends RepositoryServiceImpl<Equipo, Integer> implements EquipoService {

	private Converter<EquipoDTO, Equipo> toEntity;

	private Converter<Equipo, EquipoDTO> toDTO;

	public EquipoServiceImpl(EquipoRepository repository) {
		super(repository);
		toEntity = new Converter<>(EquipoDTO.class, Equipo.class);
		toDTO = new Converter<>(Equipo.class, EquipoDTO.class);
	}

	@Override
	public void crear(EquipoDTO equipo) {

		save((Equipo) toEntity.toEntity(equipo));
	}

	@Override
	public void actualizar(Integer id, EquipoDTO equipo) {

		update(id, (Equipo) toEntity.toEntity(equipo));
	}

	@Override
	public EquipoDTO getById(Integer id) {
		
		return (EquipoDTO) toDTO.toDTO(findById(id));
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

		return toDTO.convertToResultTableDTO(((EquipoRepository)repository).findAll(spec, pageable));
	}

	private Specification<BaseEntity> hasLigaConNombre(String nombre) {
		return (root, query, criteriaBuilder) -> {
			Join<Liga, Equipo> liga = root.join("liga");
			return criteriaBuilder.equal(liga.get("nombre"), nombre);
		};
	}

	@Override
	public List<SelectDTO> findForSelect() {

		Sort sort = Sort.by("nombre").ascending();

		return toDTO.convertListToSelectDTO(repository.findAll(sort));
	}

}
