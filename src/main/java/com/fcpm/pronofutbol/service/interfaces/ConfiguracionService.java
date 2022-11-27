package com.fcpm.pronofutbol.service.interfaces;

import com.fcpm.pronofutbol.dtos.ConfiguracionDTO;
import com.fcpm.pronofutbol.dtos.ResultTableDTO;

public interface ConfiguracionService {

	ConfiguracionDTO getByNombre(String nombre);

	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
	
	public void save(ConfiguracionDTO configuracion);
	
	public void update(ConfiguracionDTO configuracion);
	
	public ConfiguracionDTO findById(Integer id);
	
	public void deleteById(Integer id);
}
