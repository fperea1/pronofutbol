package com.base.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.rest.dtos.Configuracion;

public interface ConfiguracionRepository extends JpaRepository<Configuracion, Integer>{
	
	Configuracion getByNombre(String nombre);

}
