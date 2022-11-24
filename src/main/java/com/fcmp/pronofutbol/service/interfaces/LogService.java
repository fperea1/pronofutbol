package com.fcmp.pronofutbol.service.interfaces;

import com.fcmp.pronofutbol.dtos.ResultTableDTO;
import com.fcmp.pronofutbol.entities.Log;

public interface LogService {
	
	public void save(Log log);

	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
}
