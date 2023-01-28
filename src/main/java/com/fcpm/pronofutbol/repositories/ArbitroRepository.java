package com.fcpm.pronofutbol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fcpm.pronofutbol.entities.Arbitro;
import com.fcpm.pronofutbol.entities.BaseEntity;

public interface ArbitroRepository extends JpaRepository<Arbitro, Integer>, JpaSpecificationExecutor<BaseEntity> {

}
