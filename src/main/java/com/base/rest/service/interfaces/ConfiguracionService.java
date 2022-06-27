package com.base.rest.service.interfaces;

import org.springframework.data.domain.Page;

import com.base.rest.entities.BaseEntity;
import com.base.rest.entities.Configuracion;

public interface ConfiguracionService {

	Configuracion getByNombre(String nombre);

	public Page<BaseEntity> findByFilter(String filtroWeb, boolean exportar);
	
	public void save(Configuracion configuracion);
	
	public void update(Configuracion configuracion);
	
	public Configuracion findById(Integer id);
	
	public void deleteById(Integer id);
}
