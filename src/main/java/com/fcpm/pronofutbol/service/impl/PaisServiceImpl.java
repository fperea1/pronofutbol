package com.fcpm.pronofutbol.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcpm.pronofutbol.constant.Constantes;
import com.fcpm.pronofutbol.dtos.PaisDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.dtos.SelectDTO;
import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.Pais;
import com.fcpm.pronofutbol.exceptions.ServiceException;
import com.fcpm.pronofutbol.repositories.PaisRepository;
import com.fcpm.pronofutbol.service.interfaces.PaisService;
import com.fcpm.pronofutbol.utils.Converter;
import com.fcpm.pronofutbol.utils.bd.FiltroTablasView;
import com.fcpm.pronofutbol.utils.bd.FiltrosUtils;

@Service
@Transactional(readOnly = true)
public class PaisServiceImpl extends BaseServiceImpl implements PaisService {
	
	@Autowired
	private PaisRepository repository;
	
	private Converter<Pais, PaisDTO> toDTO;
	
	private Converter<PaisDTO, Pais> toEntity;
	
	public PaisServiceImpl() {
		super();
		toEntity = new Converter<PaisDTO, Pais>(PaisDTO.class, Pais.class);
		toDTO = new Converter<Pais, PaisDTO>(Pais.class, PaisDTO.class);
	}
	
	@Override
	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar) {
		
		FiltroTablasView filtro = FiltrosUtils.getFiltroByString(filtroWeb);
		
		Specification<BaseEntity> spec = getSpecification(filtro);
        
		Pageable pageable = getPageable(exportar, filtro);
        
		return toDTO.convertToResultTableDTO(repository.findAll(spec, pageable));
	}

	@Override
	public void save(PaisDTO pais) {
		
		repository.save((Pais) toEntity.toEntity(pais));
	}

	@Override
	public PaisDTO getById(Integer id) {
		
		if (!repository.existsById(id)) {
			throw new ServiceException(Constantes.EXC_NO_EXISTE_ENTIDAD);
		}
		return (PaisDTO) toDTO.toDTO(repository.findById(id).orElse(null));
	}

	@Override
	public void update(PaisDTO pais) {
		
		repository.save((Pais) toEntity.toEntity(pais));
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
