package com.fcpm.pronofutbol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.Quiniela;

public interface QuinielaRepository extends JpaRepository<Quiniela, Integer>, JpaSpecificationExecutor<BaseEntity> {

	Quiniela getByNumero(Integer numero);

}
