package com.fcmp.pronofutbol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fcmp.pronofutbol.entities.BaseEntity;
import com.fcmp.pronofutbol.entities.Log;

public interface LogRepository extends JpaRepository<Log, Integer>, JpaSpecificationExecutor<BaseEntity> {

}
