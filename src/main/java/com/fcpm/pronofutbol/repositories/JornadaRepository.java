package com.fcpm.pronofutbol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.Jornada;

public interface JornadaRepository extends JpaRepository<Jornada, Integer>, JpaSpecificationExecutor<BaseEntity> {

}
