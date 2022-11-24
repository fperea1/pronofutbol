package com.fcmp.pronofutbol.service.interfaces;

import com.fcmp.pronofutbol.dtos.ConfiguracionDTO;
import com.fcmp.pronofutbol.dtos.ResultTableDTO;

public interface ConfiguracionService {

	ConfiguracionDTO getByNombre(String nombre);

	public ResultTableDTO findByFilter(String filtroWeb, boolean exportar);
	
	public void save(ConfiguracionDTO configuracion);
	
	public void update(ConfiguracionDTO configuracion);
	
	public ConfiguracionDTO findById(Integer id);
	
	public void deleteById(Integer id);
}
