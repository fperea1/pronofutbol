package com.fcpm.pronofutbol.utils;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fcpm.pronofutbol.dtos.BaseDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.entities.BaseEntity;
import com.googlecode.jmapper.JMapper;

public class Converter <O, D> {
	
	private JMapper<D, O> mapper;

	public Converter(Class<O> origen, Class<D> destino) {
		super();
		mapper = new JMapper<>(destino, origen);
	}

	// Para mapeo entre clases.
	@SuppressWarnings("unchecked")
	public ResultTableDTO convertToResultTableDTO(Page<BaseEntity> page) {
		return new ResultTableDTO((List<BaseDTO>) page.getContent().stream()
				.map(temp-> mapper.getDestination((O) temp)).toList(), page.getTotalElements());
    }
	
	@SuppressWarnings("unchecked")
	public List<BaseDTO> convertListToSelectDTO(List<? extends BaseEntity> lista) {
		return (List<BaseDTO>) lista.stream().map(temp-> mapper.getDestination((O) temp)).toList();
    }
	
	@SuppressWarnings("unchecked")
	public List<BaseDTO> convertToListDTO(List<? extends BaseEntity> lista) {
		return (List<BaseDTO>) lista.stream().map(temp-> mapper.getDestination((O) temp)).toList();
    }
	
	@SuppressWarnings("unchecked")
	public BaseDTO toDTO(BaseEntity entity) {
		return (BaseDTO) mapper.getDestination((O) entity);
	}
	

	@SuppressWarnings("unchecked")
	public BaseEntity toEntity(BaseDTO dto) {
		return (BaseEntity) mapper.getDestination((O) dto);
	}
	

}
