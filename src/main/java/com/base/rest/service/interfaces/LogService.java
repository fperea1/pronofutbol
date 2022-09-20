package com.base.rest.service.interfaces;

import java.util.List;

import com.base.rest.dtos.BaseDTO;
import com.base.rest.entities.Log;

public interface LogService {
	
	public void save(Log log);

	public List<BaseDTO> findByFilter(String filtroWeb, boolean exportar);
}
