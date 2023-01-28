package com.fcpm.pronofutbol.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.JornadaDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;
import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.Jornada;
import com.fcpm.pronofutbol.exceptions.ServiceException;
import com.fcpm.pronofutbol.repositories.JornadaRepository;
import com.fcpm.pronofutbol.service.interfaces.JornadaService;
import com.fcpm.pronofutbol.utils.Converter;
import com.fcpm.pronofutbol.utils.bd.FiltroTablasView;
import com.fcpm.pronofutbol.utils.bd.FiltrosUtils;

@Service
@Transactional(readOnly = true)
public class JornadaServiceImpl extends BaseServiceImpl implements JornadaService {

	@Autowired
	private JornadaRepository repository;
	
	private Converter<Jornada, JornadaDTO> toDTO;
	
	private Converter<JornadaDTO, Jornada> toEntity;
	
	public JornadaServiceImpl() {
		super();
		toEntity = new Converter<JornadaDTO, Jornada>(JornadaDTO.class, Jornada.class);
		toDTO = new Converter<Jornada, JornadaDTO>(Jornada.class, JornadaDTO.class);
	}
	
	@Override
	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar) {
		
		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);
		
		Specification<BaseEntity> spec = getSpecification(filtro);
        
		Pageable pageable = getPageable(exportar, filtro);
        
		return toDTO.convertToResultTableDTO(repository.findAll(spec, pageable));
	}

	@Override
	public void save(JornadaDTO jornada) {
		
		repository.save((Jornada) toEntity.toEntity(jornada));
	}

	@Override
	public JornadaDTO getById(Integer id) {
		
		if (!repository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		return (JornadaDTO) toDTO.toDTO(repository.findById(id).orElse(null));
	}

	@Override
	public void update(JornadaDTO jornada) {
		
		repository.save((Jornada) toEntity.toEntity(jornada));
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
