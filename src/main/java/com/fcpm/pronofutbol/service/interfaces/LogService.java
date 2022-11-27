package com.fcpm.pronofutbol.service.interfaces;

import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.entities.Log;

public interface LogService {
	
	public void save(Log log);

	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
}
