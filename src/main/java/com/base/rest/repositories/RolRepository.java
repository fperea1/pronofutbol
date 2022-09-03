package com.base.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.base.rest.entities.BaseEntity;
import com.base.rest.entities.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer>, JpaSpecificationExecutor<BaseEntity>{
}
