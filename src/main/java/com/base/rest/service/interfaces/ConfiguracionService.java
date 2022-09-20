package com.base.rest.service.interfaces;

import java.util.List;

import com.base.rest.dtos.BaseDTO;
import com.base.rest.dtos.ConfiguracionDTO;

public interface ConfiguracionService {

	ConfiguracionDTO getByNombre(String nombre);

	public List<BaseDTO> findByFilter(String filtroWeb, boolean exportar);
	
	public void save(ConfiguracionDTO configuracion);
	
	public void update(ConfiguracionDTO configuracion);
	
	public ConfiguracionDTO findById(Integer id);
	
	public void deleteById(Integer id);
}
