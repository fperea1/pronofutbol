package com.fcpm.pronofutbol.service.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcpm.pronofutbol.dtos.JornadaDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;
import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.Jornada;
import com.fcpm.pronofutbol.repositories.JornadaRepository;
import com.fcpm.pronofutbol.service.interfaces.JornadaService;
import com.fcpm.pronofutbol.utils.Converter;
import com.fcpm.pronofutbol.utils.bd.FiltroTablasView;
import com.fcpm.pronofutbol.utils.bd.FiltrosUtils;

@Service
@Transactional(readOnly = true)
public class JornadaServiceImpl extends RepositoryServiceImpl<Jornada, Integer>  implements JornadaService {
	
	private Converter<Jornada, JornadaDTO> toDTO;
	
	private Converter<JornadaDTO, Jornada> toEntity;
	
	public JornadaServiceImpl(JornadaRepository repository) {
		super(repository);
		toEntity = new Converter<>(JornadaDTO.class, Jornada.class);
		toDTO = new Converter<>(Jornada.class, JornadaDTO.class);
	}
	
	@Override
	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar) {
		
		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);
		
		Specification<BaseEntity> spec = getSpecification(filtro);
        
		Pageable pageable = getPageable(exportar, filtro);
        
		return toDTO.convertToResultTableDTO(((JornadaRepository)repository).findAll(spec, pageable));
	}

	@Transactional
	@Override
	public void crear(JornadaDTO jornada) {
		
		save((Jornada) toEntity.toEntity(jornada));
	}

	@Override
	public JornadaDTO getById(Integer id) {
		
		return (JornadaDTO) toDTO.toDTO(findById(id));
	}

	@Transactional
	@Override
	public void actualizar(Integer id, JornadaDTO jornada) {
		
		update(id, (Jornada) toEntity.toEntity(jornada));
	}

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
