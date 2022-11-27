package com.fcpm.pronofutbol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fcpm.pronofutbol.entities.BaseEntity;
import com.fcpm.pronofutbol.entities.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer>, JpaSpecificationExecutor<BaseEntity>{
}
