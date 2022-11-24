package com.fcmp.pronofutbol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fcmp.pronofutbol.entities.BaseEntity;
import com.fcmp.pronofutbol.entities.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer>, JpaSpecificationExecutor<BaseEntity>{
}
