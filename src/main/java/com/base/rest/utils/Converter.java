package com.base.rest.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.base.rest.dtos.BaseDTO;
import com.base.rest.entities.BaseEntity;
import com.googlecode.jmapper.JMapper;

public class Converter <O, D>{

	// Para mapeo entre clases.
	@SuppressWarnings("unchecked")
	public List<BaseDTO> convertList(Page<BaseEntity> page, Class<O> o, Class<D> d) {
    	JMapper<D, O> mapper = new JMapper<D,O>(d, o);
		return (List<BaseDTO>) page.getContent().stream().map(temp-> mapper.getDestination((O) temp))
                .collect(Collectors.toList());
    }

}
