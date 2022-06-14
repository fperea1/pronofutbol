package com.base.rest.service.interfaces;

import org.springframework.data.domain.Page;

import com.base.rest.entities.BaseEntity;
import com.base.rest.entities.Log;

public interface LogService {
	
	public void save(Log log);

	public Page<BaseEntity> findByFilter(String filtroWeb, boolean exportar);
}
