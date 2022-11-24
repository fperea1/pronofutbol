package com.fcmp.pronofutbol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fcmp.pronofutbol.entities.BaseEntity;
import com.fcmp.pronofutbol.entities.Configuracion;

public interface ConfiguracionRepository extends JpaRepository<Configuracion, Integer>, JpaSpecificationExecutor<BaseEntity> {
	
	Configuracion getByNombre(String nombre);

}
