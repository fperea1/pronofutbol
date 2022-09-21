package com.base.rest.service.interfaces;

import com.base.rest.dtos.ResultTableDTO;
import com.base.rest.entities.Log;

public interface LogService {
	
	public void save(Log log);

	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
}
