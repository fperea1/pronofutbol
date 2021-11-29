package com.base.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.rest.dtos.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer>{
}
