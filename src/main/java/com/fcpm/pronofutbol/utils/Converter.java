package com.fcpm.pronofutbol.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.fcpm.pronofutbol.dtos.BaseDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.entities.BaseEntity;
import com.googlecode.jmapper.JMapper;

public class Converter <O, D> {
	
	private JMapper<D, O> mapper;

	public Converter(Class<O> origen, Class<D> destino) {
		super();
		Class<O> o = origen;
		Class<D> d = destino;
		mapper = new JMapper<D,O>(d, o);
	}

	// Para mapeo entre clases.
	@SuppressWarnings("unchecked")
	public ResultTableDTO convertToResultTableDTO(Page<BaseEntity> page) {
		return new ResultTableDTO((List<BaseDTO>) page.getContent().stream().map(temp-> mapper.getDestination((O) temp))
                .collect(Collectors.toList()), page.getTotalElements());
    }
	
	@SuppressWarnings("unchecked")
	public List<? extends BaseDTO> convertListToSelectDTO(List<? extends BaseEntity> lista) {
		return (List<BaseDTO>) lista.stream().map(temp-> mapper.getDestination((O) temp))
                .collect(Collectors.toList());
    }
	
	@SuppressWarnings("unchecked")
	public List<? extends BaseDTO> convertToListDTO(List<? extends BaseEntity> lista) {
		return (List<BaseDTO>) lista.stream().map(temp-> mapper.getDestination((O) temp))
                .collect(Collectors.toList());
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
