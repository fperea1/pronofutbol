package com.base.rest.service.interfaces;

import org.springframework.data.domain.Page;

import com.base.rest.entities.BaseEntity;

public interface RolService {

	public Page<BaseEntity> findAll();

}
