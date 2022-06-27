package com.base.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.base.rest.entities.BaseEntity;
import com.base.rest.entities.Configuracion;

public interface ConfiguracionRepository extends JpaRepository<Configuracion, Integer>, JpaSpecificationExecutor<BaseEntity> {
	
	Configuracion getByNombre(String nombre);

}
