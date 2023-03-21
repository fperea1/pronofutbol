package com.fcpm.pronofutbol.service.interfaces;

import com.fcpm.pronofutbol.dtos.ResultTableDTO;
import com.fcpm.pronofutbol.entities.Log;

public interface LogService {
	
	void crear(Log log);

	ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
}
