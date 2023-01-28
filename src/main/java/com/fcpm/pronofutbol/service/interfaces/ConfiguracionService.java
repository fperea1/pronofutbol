package com.fcpm.pronofutbol.service.interfaces;

import com.fcpm.pronofutbol.dtos.ConfiguracionDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;

public interface ConfiguracionService {

	ConfiguracionDTO getByNombre(String nombre);

	ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
	
	void save(ConfiguracionDTO dto);
	
	void update(ConfiguracionDTO dto);
	
	ConfiguracionDTO getById(Integer id);
	
	void deleteById(Integer id);
}
