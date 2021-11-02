package com.base.rest.service.interfaces;

import java.util.List;

import com.base.rest.dtos.Configuracion;

public interface ConfiguracionService {

	Configuracion getByNombre(String nombre);
	
	public List<Configuracion> findAll();
	
	public void save(Configuracion configuracion);
	
	public void update(Configuracion configuracion);
	
	public Configuracion findById(Integer id);
	
	public void deleteById(Integer id);
}
