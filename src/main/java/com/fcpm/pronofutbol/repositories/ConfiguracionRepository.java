package com.fcpm.pronofutbol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.Configuracion;

public interface ConfiguracionRepository extends JpaRepository<Configuracion, Integer>, JpaSpecificationExecutor<BaseEntity> {
	
	Configuracion getByNombre(String nombre);

}
